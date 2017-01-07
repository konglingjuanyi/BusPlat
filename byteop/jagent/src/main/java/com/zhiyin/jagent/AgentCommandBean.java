package com.zhiyin.jagent;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import lombok.Data;
import org.kohsuke.args4j.Option;

import java.util.Arrays;
import java.util.List;

/**
 * Created by wangqinghui on 2017/1/5.
 */
@Data
public class AgentCommandBean {

    @Option(name="-excludePackage",usage="Sets a file if that is present")
    private String excludePackage;

    public List<String> getExcludePackages(){

        if(Strings.isNullOrEmpty(excludePackage)){
            return Lists.newArrayList();
        }
        return Arrays.asList( excludePackage.trim().split(","));

    }

    public static AgentCommandBean getDefault(){
        AgentCommandBean agentCommandBean = new AgentCommandBean();
        agentCommandBean.setExcludePackage("");

        return agentCommandBean;
    }


}
