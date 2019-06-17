package com.miaosha.demo.config;

import org.apache.catalina.connector.Connector;
import org.apache.coyote.http11.Http11NioProtocol;
import org.springframework.boot.web.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.ConfigurableWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.stereotype.Component;

/**
 * 描述：定制化tomcat
 * @author wangyu
 * @date 2019/6/17
 */

//当Spring容器内没有TomcatEmbeddedServletContainerFactory这个bean时，会把bean加载进Spring容器
@Component
public class WebServerConfiguration implements WebServerFactoryCustomizer<ConfigurableWebServerFactory> {
    @Override
    public void customize(ConfigurableWebServerFactory configurableWebServerFactory) {

        //使用对应工厂类提供给我们的接口定制化我们的tomcat conactor
        ((TomcatServletWebServerFactory)configurableWebServerFactory).addConnectorCustomizers(new TomcatConnectorCustomizer() {
            @Override
            public void customize(Connector connector) {
                Http11NioProtocol protocol = (Http11NioProtocol)connector.getProtocolHandler();

                //定制化 keepAliveTimeOut,30秒无请求断开连接
                protocol.setKeepAliveTimeout(30000);
                //当客户端发送超过一万个请求，自动断开keepAlive请求
                protocol.setMaxKeepAliveRequests(10000);

            }
        });

    }
}
