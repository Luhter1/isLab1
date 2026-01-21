package org.itmo.isLab1.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class DatabaseConfig {

    @Value("${spring.datasource.url}")
    private String url;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    @Value("${spring.datasource.driver-class-name}")
    private String driverClassName;

    @Value("${druid.initial-size}")
    private int initialSize;

    @Value("${druid.min-idle}")
    private int minIdle;

    @Value("${druid.max-active}")
    private int maxActive;

    @Value("${druid.max-wait}")
    private long maxWait;

    @Value("${druid.time-between-eviction-runs-millis}")
    private long timeBetweenEvictionRunsMillis;

    @Value("${druid.min-evictable-idle-time-millis}")
    private long minEvictableIdleTimeMillis;

    @Value("${druid.max-evictable-idle-time-millis}")
    private long maxEvictableIdleTimeMillis;

    @Value("${druid.validation-query}")
    private String validationQuery;

    @Value("${druid.test-while-idle}")
    private boolean testWhileIdle;

    @Value("${druid.test-on-borrow}")
    private boolean testOnBorrow;

    @Value("${druid.test-on-return}")
    private boolean testOnReturn;

    @Value("${druid.pool-prepared-statements}")
    private boolean poolPreparedStatements;

    @Value("${druid.max-pool-prepared-statement-per-connection-size}")
    private int maxPoolPreparedStatementPerConnectionSize;

    @Bean
    public DataSource dataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        dataSource.setDriverClassName(driverClassName);

        // Connection pool settings
        dataSource.setInitialSize(initialSize);
        dataSource.setMinIdle(minIdle);
        dataSource.setMaxActive(maxActive);
        dataSource.setMaxWait(maxWait);

        // Eviction settings
        dataSource.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
        dataSource.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
        dataSource.setMaxEvictableIdleTimeMillis(maxEvictableIdleTimeMillis);

        // Validation settings
        dataSource.setValidationQuery(validationQuery);
        dataSource.setTestWhileIdle(testWhileIdle);
        dataSource.setTestOnBorrow(testOnBorrow);
        dataSource.setTestOnReturn(testOnReturn);

        // Prepared statement pool settings
        dataSource.setPoolPreparedStatements(poolPreparedStatements);
        dataSource.setMaxPoolPreparedStatementPerConnectionSize(maxPoolPreparedStatementPerConnectionSize);

        return dataSource;
    }
}
