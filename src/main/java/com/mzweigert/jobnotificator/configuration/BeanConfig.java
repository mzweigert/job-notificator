/*
 * Copyright (c) 2019. BEST S.A. and/or its affiliates. All rights reserved.
 */
package com.mzweigert.jobnotificator.configuration;

import com.mzweigert.crawler.service.crawler.CrawlerService;
import com.mzweigert.crawler.service.crawler.CrawlerServiceImpl;
import com.mzweigert.jobnotificator.util.MailContentGenerator;
import com.mzweigert.jobnotificator.util.MailContentGeneratorPoland;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class BeanConfig {

	@Value("${email.host}")
	private String HOST;
	@Value("${email.port}")
	private int PORT;
	@Value("${email.username}")
	private String USERNAME;
	@Value("${email.password}")
	private String PASSWORD;

	@Bean(name = "javaMailSender")
	public JavaMailSender getJavaMailSender() {
		JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();

		javaMailSender.setHost(HOST);
		javaMailSender.setPort(PORT);
		javaMailSender.setUsername(USERNAME);
		javaMailSender.setPassword(PASSWORD);

		Properties properties = new Properties();

		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.socketFactory.port", PORT);
		properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		properties.put("mail.smtp.ssl.trust", "smtp.gmail.com");
		properties.put("mail.debug", "true");

		javaMailSender.setJavaMailProperties(properties);

		return javaMailSender;
	}

	@Bean
	public CrawlerService getCrawlerService(){
		return new CrawlerServiceImpl();
	}

	@Bean
	public MailContentGenerator getMailTextGenerator() {
		String language = LocaleContextHolder.getLocale().getLanguage();
		switch (language) {
			case "PL":
				default:
				return new MailContentGeneratorPoland();
		}
	}
}
