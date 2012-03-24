package br.com.insula.cfopservice.configuration.root.mongo;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.data.authentication.UserCredentials;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;

import br.com.insula.cfopservice.configuration.root.MongoConfig;

import com.mongodb.Mongo;

@Configuration
@Profile("producao")
public class HerokuMongoConfig implements MongoConfig {

	private static final String MONGO_URI = "MONGOLAB_URI";

	@Autowired
	private Environment env;

	@Bean
	public Mongo mongo() throws Exception {
		URI mongoUri = new URI(env.getProperty(MONGO_URI));
		return new Mongo(mongoUri.getHost(), mongoUri.getPort());
	}

	@Override
	@Bean
	public MongoDbFactory mongoDbFactory() throws Exception {
		URI mongoUri = new URI(env.getProperty(MONGO_URI));
		String[] userInfo = mongoUri.getUserInfo().split(":");
		UserCredentials credentials = new UserCredentials(userInfo[0], userInfo[1]);
		return new SimpleMongoDbFactory(mongo(), mongoUri.getPath().substring(1), credentials);
	}

}