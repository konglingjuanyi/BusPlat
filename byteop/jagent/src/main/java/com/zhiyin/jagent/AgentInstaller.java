package com.zhiyin.jagent;

import com.alibaba.ttl.threadpool.agent.TtlTransformer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.google.common.base.Strings;
import com.zhiyin.jagent.agent.example.SleepingClassFileTransformer;
import com.zhiyin.jagent.transformer.TelemetryTransformer;
import com.zhiyin.jagent.transformer.handler.config.TelemetryConfiguration;
import javassist.CannotCompileException;
import lombok.extern.slf4j.Slf4j;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;

import java.io.InputStream;
import java.lang.instrument.Instrumentation;
import java.util.logging.Logger;

@Slf4j
public class AgentInstaller {

//	Logger log = Logger.getLogger()

    public static String prefix = "jagent info, ";

	public static void premain(String agentArguments,
			Instrumentation instrumentation) {

        AgentCommandBean commandBean = parseParam(agentArguments);
		System.out.println("process was attached by premain");
		doIns(instrumentation,commandBean);
	}

	public static void agentmain(String args, Instrumentation instrumentation) throws CannotCompileException {

//		if (args == null || args.isEmpty()) {
//			System.err.println(AgentConfig.AGENT_NAME_NOT_PROVIDED);
//		}

		log.info("process was attached by agentmain:" + AgentInstaller.class.getName());
        AgentCommandBean commandBean = parseParam(args);

		doIns(instrumentation, commandBean);
	}

	public static void doIns(Instrumentation instrumentation, AgentCommandBean commandBean){

        AgentConfig.addExcludePackage( commandBean.getExcludePackages());
		handlerTrans(instrumentation);

		instrumentation.addTransformer(new TtlTransformer());
        System.out.println(prefix + "add " + TtlTransformer.class.getName());
//        instrumentation.addTransformer(new ClassPathTransformer());
//		instrumentation.addTransformer(new SleepingClassFileTransformer());

//		new AopMethodAgent().createAgent(null).installOn(instrumentation);

//		Class[] classes = instrumentation.getAllLoadedClasses();
//		for (Class cls : classes) {
//			boolean modify = ClazzUtil.classCouldModify(cls.getName());
//			if(modify){
//				System.out.println(ClassPathUtil.getClassAbsPath(cls));
//			}
//		}
	}



	private static void handlerTrans(Instrumentation instrumentation ) {

		try {
            InputStream schemaIS = AgentInstaller.class.getClassLoader().getResourceAsStream("META-INF/agent/config-default.yml");
            ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
            TelemetryConfiguration config = mapper.readValue(schemaIS, TelemetryConfiguration.class);
            if(config == null){
                return ;
            }
            final TelemetryTransformer transformer = new TelemetryTransformer();
            transformer.setHandlers( config.getHandlers() );

            instrumentation.addTransformer(transformer);
		}catch (Exception e){
			System.out.println("handler error.");
            System.out.println(e);
        }

	}


    public static AgentCommandBean parseParam(String argStr){
        AgentCommandBean bean = new AgentCommandBean();
        CmdLineParser parser = new CmdLineParser(bean);

        if(Strings.isNullOrEmpty(argStr)){
            return AgentCommandBean.getDefault();
        }
        String[] args = argStr.split("\\s+?");

        try {
            parser.parseArgument(args);
            return bean;
        } catch (CmdLineException e) {
            // handling of wrong arguments
            System.err.println(e.getMessage());
            parser.printUsage(System.err);
        }
        return bean;
    }

}
