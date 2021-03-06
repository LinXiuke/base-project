package com.zero.common.base.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.MimeMappings;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/js/**").addResourceLocations("classpath:/js/");
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

}

//public class WebConfig implements ServletContextInitializer, EmbeddedServletContainerCustomizer {
//
//	private final Logger log = LoggerFactory.getLogger(WebConfig.class);
//
//	private final Environment env;
//
//	private final ApplicationProperties applicationProperties;
//
//	public WebConfig(Environment env, ApplicationProperties applicationProperties) {
//		this.env = env;
//		this.applicationProperties = applicationProperties;
//	}
//
//	@Override
//	public void onStartup(ServletContext servletContext) throws ServletException {
//		if (env.getActiveProfiles().length != 0) {
//			log.info("profiles: {}", (Object[]) env.getActiveProfiles());
//		}
//	}
//
//
//	@Override
//	public void customize(ConfigurableEmbeddedServletContainer container) {
//		MimeMappings mappings = new MimeMappings(MimeMappings.DEFAULT);
//		mappings.add("html", "text/html;charset=utf-8");
//		mappings.add("json", "text/html;charset=utf-8");
//		container.setMimeMappings(mappings);
//	}
//
//}
