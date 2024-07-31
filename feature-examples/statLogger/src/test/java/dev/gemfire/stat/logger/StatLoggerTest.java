// Copyright 2023-2024 Broadcom. All rights reserved.

package dev.gemfire.stat.logger;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import org.apache.geode.Statistics;
import org.apache.geode.StatisticsType;
import org.apache.geode.cache.Cache;
import org.apache.geode.cache.execute.FunctionContext;
import org.apache.geode.cache.execute.ResultSender;
import org.apache.geode.distributed.DistributedSystem;

public class StatLoggerTest {
  private FunctionContext functionContext;
  private StatLogger statLogger;

  @Before
  public void setUp() {
    statLogger = new StatLogger();
    functionContext = mock(FunctionContext.class);
  }

  @Ignore
  @Test
  public void testStatLogger() {
    ResultSender resultSender = mock(ResultSender.class);
    when(functionContext.getResultSender()).thenReturn(resultSender);
    Cache cache = mock(Cache.class);
    when(functionContext.getCache()).thenReturn(cache);
    DistributedSystem distributedSystem = mock(DistributedSystem.class);
    when(cache.getDistributedSystem()).thenReturn(distributedSystem);
    StatisticsType statisticsType = mock(StatisticsType.class);
    when(distributedSystem.findType(any())).thenReturn(statisticsType);
    Statistics[] statistics = new Statistics[1];
    when(distributedSystem.findStatisticsByType(any())).thenReturn(statistics);
    statLogger.execute(functionContext);


  }


}
