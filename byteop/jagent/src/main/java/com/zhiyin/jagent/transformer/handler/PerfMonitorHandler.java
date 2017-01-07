package com.zhiyin.jagent.transformer.handler;

import com.zhiyin.jagent.ClazzUtil;
import javassist.*;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

@Slf4j
public class PerfMonitorHandler extends SubTypeInstrumentationHandler {
    public static String LogbackLogger = "ch.qos.logback.classic.Logger";
    public static String methodName= "buildLoggingEventAndAppend";
    public PerfMonitorHandler() {
        super( LogbackLogger);
    }

    protected boolean transform(CtClass cc, ClassPool pool) throws NotFoundException, CannotCompileException, IOException {

        try {

//            if (ClazzUtil.classForbidModify(cc)) {
//                return false;
//            }

            System.out.println(cc.getName());
            log.info("process class {}",cc.getName());
            CtBehavior[] methods = cc.getDeclaredBehaviors();
            for (int i = 0; i < methods.length; i++) {
                if (ClazzUtil.methodCouldModify(methods[i])) {
//                    System.out.println("modify" + methods[i].getName());
                    doMethod2(methods[i]);
                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            log.error("transformer",ex);
        } finally {
            if (cc != null) {
                cc.detach();
            }
        }

        return true;
    }

    private void doMethod2(CtBehavior method) throws CannotCompileException {
        method.addLocalVariable("elapsedTime", CtClass.longType);

        method.insertBefore("elapsedTime = System.currentTimeMillis();");
        String methodInfo = method.getDeclaringClass().getName() + "." + method.getName();
//        method.addLocalVariable("methodInfo",CtClass.charType);
        method.insertAfter("{elapsedTime = System.currentTimeMillis() - elapsedTime;"
                + "System.out.println(\"instrumentation result, " + methodInfo + " executed in ms: \" + elapsedTime);}");
    }



}
