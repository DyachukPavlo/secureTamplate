package com.securetemplate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

@ComponentScan("com.securetemplate")
@SpringBootApplication(exclude = HibernateJpaAutoConfiguration.class)
//@EnableJpaRepositories(basePackages="com.securetemplate.repository")
public class SecureTemplateApplication {

	public static void main(String[] args) throws UnsupportedEncodingException {
		String path = SecureTemplateApplication.class.getProtectionDomain().getCodeSource().getLocation().getPath();
		String jarPath = URLDecoder.decode(path, "UTF-8");
		File jarFile = new File(jarPath);
		System.setProperty("jarPath", jarFile.getParent());


		System.getProperties().put("server.port", 8095);
		SpringApplication.run(SecureTemplateApplication.class, args);
	}
/*
  @Bean
	LocalContainerEntityManagerFactoryBean entityManagerFactory(){
		LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
		entityManagerFactoryBean.setPackagesToScan("com.securetemplate.repository");
		return entityManagerFactoryBean;
  }*/

}


