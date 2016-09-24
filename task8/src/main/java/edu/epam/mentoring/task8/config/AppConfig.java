package edu.epam.mentoring.task8.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

/**
 * Created by eugen on 17.09.2016.
 */
@Configuration
@ComponentScan(basePackages = "edu.epam.mentoring.task8")
public class AppConfig { }
