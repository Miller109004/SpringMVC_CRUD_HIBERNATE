package web.config;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;


import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


@Configuration
@EnableJpaRepositories("web.repository")
@EnableTransactionManagement
@PropertySource("classpath:db.properties")
@ComponentScan("web")
@RequiredArgsConstructor
public class DBConfig {

        @Resource
        private final Environment env;

        @Bean
        public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
            LocalContainerEntityManagerFactoryBean entityManager = new LocalContainerEntityManagerFactoryBean();
            entityManager.setDataSource(dataSource());
            entityManager.setPackagesToScan(env.getRequiredProperty("db.entity.package"));
            entityManager.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
            entityManager.setJpaProperties(getHibernateProperties());
            return entityManager;
        }

        @Bean
        public PlatformTransactionManager transactionManager() {
            JpaTransactionManager jpaTransactionManager = new JpaTransactionManager();
            jpaTransactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
            return jpaTransactionManager;
        }

        public Properties getHibernateProperties() {
            try {
                Properties properties = new Properties();
                InputStream is = getClass().getClassLoader().getResourceAsStream("db.properties");
                properties.load(is);
                return properties;
            } catch (IOException e) {
                throw new IllegalArgumentException("Не найденно 'db.properties' ", e);
            }
        }

        @Bean
        public DataSource dataSource() {
            BasicDataSource basicDataSource = new BasicDataSource();
            basicDataSource.setDriverClassName(env.getRequiredProperty("db.driver"));
            basicDataSource.setUrl(env.getRequiredProperty("db.url"));
            basicDataSource.setUsername(env.getRequiredProperty("db.username"));
            basicDataSource.setPassword(env.getRequiredProperty("db.password"));
            return basicDataSource;

        }
    }
