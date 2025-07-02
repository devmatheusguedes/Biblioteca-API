package io.github.devmatheusguedes.libraryapi.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class DataBaseConfiguration {
    @Value("${spring.datasource.url}")
    String url;
    @Value("${spring.datasource.username}")
    String user;
    @Value("${spring.datasource.password}")
    String passWord;
    @Value("${spring.datasource.driver-class-name}")
    String driver;

    @Bean
    public DataSource hikariDataSource(){
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(url);
        config.setUsername(user);
        config.setDriverClassName(driver);
        config.setPassword(passWord);

        config.setMinimumIdle(1); // minimo de conexões liberadas
        config.setMaximumPoolSize(10); // maximo de conexões liberadas
        config.setPoolName("library-db-pool");
        config.setMaxLifetime(600000); // tempo de conexão em milissegundos
        config.setConnectionTimeout(600000);// tempo maximo para tentar estabelecer um conexão
        config.setConnectionTestQuery("SELECT 1");

        return new HikariDataSource(config);

    }
}
