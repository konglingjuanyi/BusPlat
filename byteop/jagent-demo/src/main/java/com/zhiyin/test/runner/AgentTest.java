package com.zhiyin.test.runner;

import org.junit.Test;

public class AgentTest {
 
    @Test
    public void shouldInstantiateSleepingInstance() throws InterruptedException {
        Sleeping sleeping = new Sleeping();
        sleeping.randomSleep();
    }
}