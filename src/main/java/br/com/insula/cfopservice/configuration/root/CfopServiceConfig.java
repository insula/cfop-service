package br.com.insula.cfopservice.configuration.root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Controller;

import br.com.insula.cfopservice.app.Cfop;

@Configuration
@ComponentScan(basePackageClasses = { Cfop.class, CfopServiceConfig.class }, excludeFilters = @Filter(Controller.class))
public class CfopServiceConfig {

	@Autowired
	private MongoConfig mongoConfig;

	@Bean
	public MongoTemplate mongoTemplate() throws Exception {
		return new MongoTemplate(mongoConfig.mongoDbFactory());
	}

}