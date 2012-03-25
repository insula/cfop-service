package br.com.insula.cfopservice.configuration.root.mongo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;

import br.com.insula.cfopservice.configuration.root.MongoConfig;

import com.mongodb.MongoURI;

@Configuration
@Profile("producao")
public class HerokuMongoConfig implements MongoConfig {

	private static final String MONGO_URI = "MONGOLAB_URI";

	@Autowired
	private Environment env;

	@Override
	@Bean
	public MongoDbFactory mongoDbFactory() throws Exception {
		return new SimpleMongoDbFactory(new MongoURI(env.getProperty(MONGO_URI)));
	}

}