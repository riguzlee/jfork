package com.riguz.jfinal;

import com.jfinal.core.JFinal;

public class Jetty {
    public static void main(String[] args) {
        // 如果用Jetty启动 需要jetty-server-8.1.8.jar
        // 否则用Tomcat启动时，需要删除该包避免冲突
        JFinal.start("src/main/app", 8888, "/", 5);
    }
}
