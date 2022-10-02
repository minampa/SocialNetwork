package org.example;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class Config {
    @Bean
    DataSource getDataSource(){
        return DataSourceBuilder.create()
                .username("root")
                .password("mypassword")
                .url("jdbc:mysql://localhost:3306/socialnetwork?useSSL=false&createDatabaseIfNotExist=true")
                .build();
    }
}
