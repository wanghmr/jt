package com.jt;

import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;

/**
 * @author wanghui
 * @date 2021/1/14
 * Description:配置解决IDEA正常启动项目但不能访问页面问题
 */
@Configuration
public class CommonConfiguration {
    @Bean
    public TomcatServletWebServerFactory servletContainer() {

        TomcatServletWebServerFactory factory = new TomcatServletWebServerFactory();

        factory.setDocumentRoot(new File("D:\\workspace\\sts\\jt\\jt\\jt-manage\\src\\main\\webapp")); //项目的webapp包目录

        return factory;
    }
}
