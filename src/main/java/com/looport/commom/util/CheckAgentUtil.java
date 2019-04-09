package com.looport.commom.util;

public class CheckAgentUtil {

    public static boolean checkMicroMessageAgent(String agent) {// 网页agent检查
        return agent.contains("MicroMessenger");
    }

    public static boolean checkMobileAgent(String agent) {//手机agent检查
        boolean flag = false;
        String[] keywords = { "Android", "iPhone", "iPod", "iPad",
                "Windows Phone", "MQQBrowser" };

        for (String item : keywords) {
            if (agent.contains(item)) {
                flag = true;
                break;
            }
        }

        return flag;
    }

    public static boolean checkWebAgent(String agent) {// 网页agent检查
        return agent.contains("Web");
    }
}
