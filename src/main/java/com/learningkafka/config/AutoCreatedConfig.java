package com.learningkafka.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
@Profile("local")
public class AutoCreatedConfig {

	@Bean
	public NewTopic newTopic() {
		return TopicBuilder.name("library-event").partitions(3).replicas(3).build();
	}
	
	 @Bean
	    public ServletWebServerFactory servletWebServerFactory() {
	        return new TomcatServletWebServerFactory();
	    }
}
