package com.sekulicd.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.sekulicd.dbconfig.DatabaseConfig;

//@EnableAutoConfiguration
//@Configuration
@ComponentScan(basePackages="com.sekulicd")
@EnableWebMvc
@Import(DatabaseConfig.class)
@SpringBootApplication
public class RunApplication extends SpringBootServletInitializer 
{
	@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(RunApplication.class);
    }
	
	public static void main(String[] args)
	{
		SpringApplication.run(RunApplication.class, args);
	}
	
}
