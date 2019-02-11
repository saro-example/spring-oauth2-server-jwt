package me.saro.example.oauth2.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
@EnableJpaRepositories(basePackages = "me.saro.example.oauth2.*", entityManagerFactoryRef = "entityManagerFactory", transactionManagerRef = "transactionManager")
class DBConfiguration {

    @Autowired DataSource dataSource;

    
    @Configuration
    @ConfigurationProperties(prefix = "db.oauth")
    static class HikariDataSourceConfig extends HikariConfig {
        @Bean
        DataSource dataSource() {
            return new HikariDataSource(this);
        }
    }

    // 팩토리 매니저
    @Bean("entityManagerFactory")
    LocalContainerEntityManagerFactoryBean entityManagerFactory(EntityManagerFactoryBuilder builder) {
        return builder.dataSource(dataSource).packages("me.saro.example.oauth2.*").build();
    }

    // 트랜잭션 매니저
    @Bean("transactionManager")
    PlatformTransactionManager transactionManager(EntityManagerFactoryBuilder builder) {
        return new JpaTransactionManager(entityManagerFactory(builder).getObject());
    }
}
