package com.zhiyin.test.runner;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Sleeping {
     
    public void randomSleep() throws InterruptedException {
        long randomSleepDuration = (long) (500 + Math.random() * 700);
        log.info("sleep for {}ms",randomSleepDuration);
        Thread.sleep(randomSleepDuration);
        hello();
    }

    public void hello(){
        log.info("hello running!");
    }
}