package edu.epam.mentoring.task10.config;

import edu.epam.mentoring.task10.model.Employee;
import edu.epam.mentoring.task10.model.Project;
import edu.epam.mentoring.task10.model.Unit;
import org.h2.jdbcx.JdbcDataSource;
import org.hibernate.dialect.H2Dialect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.hibernate3.HibernateTransactionManager;
import org.springframework.orm.hibernate3.annotation.AnnotationSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * Created by eugen on 17.09.2016.
 */
@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "edu.epam.mentoring.task10")
@EnableTransactionManagement
public class ApplicationConfig {
    @Autowired
    private DataSource dataSource;

    @Bean
    public DataSource getDataSource() {
//        return new EmbeddedDatabaseBuilder()
//                .setType(EmbeddedDatabaseType.H2)
//                .addScript("script/setup.sql")
//                .addScript("script/populate.sql")
//                .build();
        JdbcDataSource ds = new JdbcDataSource();
        ds.setURL("jdbc:h2:~/task10database");
//        ds.setUser("sa");
//        ds.setPassword("sa");
        return ds;
    }

    @Bean
    public AnnotationSessionFactoryBean sessionFactoryBean() {
        Properties props = new Properties();
        props.put("hibernate.dialect", H2Dialect.class.getName());
//        props.put("hibernate.format_sql", "true");
//        props.put("hibernate.show_sql", "true");
//        props.put("hibernate.hbm2ddl.auto", "create");
        props.put("hibernate.connection.url", "jdbc:h2:~/task10database");

        AnnotationSessionFactoryBean bean = new AnnotationSessionFactoryBean();
        bean.setAnnotatedClasses(Employee.class, Project.class, Unit.class);
        bean.setHibernateProperties(props);
        bean.setDataSource(dataSource);
        bean.setSchemaUpdate(true);
        return bean;
    }

    @Bean
    public HibernateTransactionManager transactionManager() {
        return new HibernateTransactionManager(sessionFactoryBean().getObject());
    }
}
