package br.com.insula.cfopservice.configuration.root;

import org.springframework.data.mongodb.MongoDbFactory;

public interface MongoConfig {

	public MongoDbFactory mongoDbFactory() throws Exception;

}