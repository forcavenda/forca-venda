package br.com.logistica.forcavenda.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.mongodb.MongoClient;

@Configuration
@EnableMongoAuditing
@PropertySource("classpath:application.properties")
@ComponentScan(basePackages = "br.com.logistica.forcavenda.models")
public class MongoConfig extends AbstractMongoConfiguration {

  @Value("${spring.application.name}")
  private String proAppName;

  @Value("${spring.data.mongodb.authentication-database}")
  private String authenticationDatabase;

  @Value("${spring.data.mongodb.host}")
  private String mongoHost;

  @Value("${spring.data.mongodb.port}")
  private Integer mongoPort;

  @Value("${spring.data.mongodb.database}")
  private String mongoDB;

  @Value("${spring.data.mongodb.username}")
  private String mongoUser;

  @Value("${spring.data.mongodb.password}")
  private String mongoPwd;

  @Bean
  @Override
  public MongoClient mongoClient() {
    return new MongoClient(mongoHost);
    /*
     * return new MongoClient(new ServerAddress(mongoHost, mongoPort), MongoCredential
     * .createCredential(mongoUser, authenticationDatabase, mongoPwd.toCharArray()),
     * new MongoClientOptions.Builder().build());
     */
  }

  @Override
  protected String getDatabaseName() {
    return mongoDB;
  }

  @Bean
  @Override
  @Qualifier("mongoTemplate")
  public MongoTemplate mongoTemplate() throws Exception {
    return new MongoTemplate(mongoClient(), getDatabaseName());
  }
}
