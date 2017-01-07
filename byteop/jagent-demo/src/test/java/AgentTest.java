 
import com.google.common.collect.Maps;

import com.zhiyin.test.runner.Sleeping;
import org.junit.Test;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class AgentTest {
 
    @Test
    public void shouldInstantiateSleepingInstance() throws InterruptedException {

        Sleeping sleeping = new Sleeping();
        sleeping.randomSleep();

//        System.out.println();
//
//        LoggerFactory.getLogger(AgentTest.class).info("sssss");
//
//        LoggerFactory.getLogger(AgentTest.class).info("ccc");
//
//        LoggerFactory.getLogger("trace").info("trr");

    }
}