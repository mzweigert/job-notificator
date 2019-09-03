/*
 * Copyright (c) 2019. BEST S.A. and/or its affiliates. All rights reserved.
 */
package com.mzweigert.jobnotificator.configuration;

import com.mzweigert.crawler.service.crawler.CrawlerService;
import com.mzweigert.crawler.service.crawler.CrawlerServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {

	@Bean
	public CrawlerService getCrawlerServiceBean(){
		return new CrawlerServiceImpl();
	}
}
