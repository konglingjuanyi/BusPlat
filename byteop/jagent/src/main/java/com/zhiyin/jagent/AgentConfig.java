package com.zhiyin.jagent;

import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by wangqinghui on 2016/12/8.
 */
public class AgentConfig {

    public static final String HANDLER_CONFI_PATH_DEFAULT = "META-INF/agent/config-default.yml";

    public static final String AGENT_NAME_NOT_PROVIDED = "Agent name not provided.";

    public static final String AGENT_DEFAULT_NAME = "";

    public static List<String> NotModifyMethodNames = Arrays.asList("toString", "finalize", "getClass","equals","wait","main","<init>");

    public static List<String> ExcludePackages = Arrays.asList("java.lang","com.zhiyin.jagent");

    private static final String ERR_UNKOWN_AGENT = "Agent with provided name does not exist.";

    public static void addExcludePackage(String pack){
        ArrayList<String> tmp = Lists.newArrayList(AgentConfig.ExcludePackages);
        tmp.add(pack);
        AgentConfig.ExcludePackages = tmp;
    }
    public static void addExcludePackage(List<String> pack){
        ArrayList<String> tmp = Lists.newArrayList(AgentConfig.ExcludePackages);
        tmp.addAll(pack);
        AgentConfig.ExcludePackages = tmp;

    }


}
