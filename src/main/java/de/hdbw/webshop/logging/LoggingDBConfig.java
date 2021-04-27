package de.hdbw.webshop.logging;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.*;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import javax.sql.DataSource;

@Configuration
@EnableMongoRepositories(basePackages = { "de.hdbw.webshop.logging.repository" })
public class LoggingDBConfig {

    @Bean(name = "loggingDataSource")
    @ConfigurationProperties(prefix = "app.logging.datasource")
    public DataSource dataSource() {
        return DataSourceBuilder.create().build();
    }

//    @Bean(name = "loggingEntityManagerFactory")
//    public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean(EntityManagerFactoryBuilder builder, @Qualifier("loggingDataSource") DataSource dataSource) {
//        return builder.dataSource(dataSource).packages("de.hdbw.webshop.logging").build();
//    }
//
//    @Bean(name = "loggingTransactionManager")
//    public PlatformTransactionManager transactionManager(
//            @Qualifier("loggingEntityManagerFactory") EntityManagerFactory
//                    entityManagerFactory
//    ) {
//        return new JpaTransactionManager(entityManagerFactory);
//    }
}
