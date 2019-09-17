package com.quasarconsultoria.jwtspringsec;

import org.springframework.boot.web.server.MimeMappings;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MimeMappingsConfigurer implements WebServerFactoryCustomizer<ConfigurableServletWebServerFactory> {

    @Override
    public void customize(ConfigurableServletWebServerFactory factory) {
        MimeMappings mappings = new MimeMappings(MimeMappings.DEFAULT);
        mappings.add("html", "text/html; charset=utf-8");
        mappings.add("js", "application/javascript; charset=utf-8");
        mappings.add("css", "text/css; charset=utf-8");
        factory.setMimeMappings(mappings);
    }

}
