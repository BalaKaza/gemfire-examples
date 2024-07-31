// Copyright 2023-2024 Broadcom. All rights reserved.

package dev.gemfire.stat.logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CopyOnWriteArrayList;

import org.apache.logging.log4j.Logger;

import org.apache.geode.StatisticDescriptor;
import org.apache.geode.Statistics;
import org.apache.geode.StatisticsType;
import org.apache.geode.cache.execute.Function;
import org.apache.geode.cache.execute.FunctionContext;
import org.apache.geode.distributed.DistributedSystem;
import org.apache.geode.logging.internal.log4j.api.LogService;

public class StatLogger implements Function {
  public static final String ID = "StatLogger";
  private static final Logger logger = LogService.getLogger();
  private List<StatsHolder> listOfStats = new CopyOnWriteArrayList<>();
  private Timer timer;
  private long timerInterval;

  @Override
  public boolean isHA() {
    return false;
  }

  @Override
  public boolean hasResult() {
    return true;
  }

  @Override
  public String getId() {
    return ID;
  }

  @Override
  public void execute(FunctionContext functionContext) {
    ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
    Properties properties = new Properties();
    try {
      final InputStream stream = contextClassLoader.getResourceAsStream("statLogger.properties");
      properties.load(stream);
    } catch (IOException e) {
      logger.error("Could not load statLogger.properties", e);
    }
    String logFrequency = properties.get("logFrequency").toString();
    String statistics = properties.getProperty("statistics");
    List<String> statisticsList = Arrays.asList(statistics.split(",", 0));

    if (!statisticsList.isEmpty() && !logFrequency.isEmpty()) {
      timerInterval = Long.parseLong(logFrequency);
      for (String statistic : statisticsList) {
        String[] statsNames = statistic.split("[.]", 0);
        addStats(functionContext.getCache().getDistributedSystem(), statsNames[0], statsNames[1]);
      }
      cancelTimer();
      ensureTimerRunning();
      functionContext.getResultSender()
          .lastResult("Logging Metric count " + listOfStats.size() + " with timer interval set to "
              + timerInterval + " ms");
    } else if (logFrequency.equalsIgnoreCase("0")) {
      cancelTimer();
      listOfStats.clear();
      functionContext.getResultSender().lastResult("Log Frequency set to 0. Stopping StatLogger.");
    } else {
      cancelTimer();
      listOfStats.clear();
      functionContext.getResultSender().lastResult("No stats to log. Stopping StatLogger.");
    }
  }

  private void cancelTimer() {
    if (timer != null) {
      timer.cancel();
      timer = null;
    }
  }

  private void ensureTimerRunning() {
    if (timer == null && !listOfStats.isEmpty()) {
      timer = new Timer("StatLogger", true);
      timer.scheduleAtFixedRate(new TimerTask() {
        @Override
        public void run() {
          for (StatsHolder curr : listOfStats) {
            logger.info(curr.toString());
          }
        }
      }, 0, timerInterval);
    }
  }

  private void addStats(DistributedSystem ds, String groupName, String statName) {
    StatisticsType type = ds.findType(groupName);
    for (Statistics currStatistics : ds.findStatisticsByType(type)) {
      if (currStatistics.getTextId().compareToIgnoreCase(groupName) == 0) {
        for (StatisticDescriptor currDescriptor : type.getStatistics()) {
          if (currDescriptor.getName().compareToIgnoreCase(statName) == 0) {
            listOfStats
                .add(new StatsHolder(groupName + "." + statName, currStatistics, currDescriptor));
          }
        }
      }
    }
  }
}
