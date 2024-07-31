// Copyright 2023-2024 Broadcom. All rights reserved.

package dev.gemfire.stat.logger;

import static org.mockito.Mockito.mock;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import org.apache.geode.cache.execute.FunctionContext;

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
    statLogger.execute(functionContext);


  }


}
