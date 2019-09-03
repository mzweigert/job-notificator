/*
 * Copyright (c) 2019. BEST S.A. and/or its affiliates. All rights reserved.
 */
package com.mzweigert.jobnotificator.service;

import com.mzweigert.crawler.service.crawler.CrawlerArgs;
import com.mzweigert.crawler.service.crawler.CrawlerService;
import com.mzweigert.jobnotificator.model.Job;
import com.mzweigert.jobnotificator.model.SourcePage;
import com.mzweigert.jobnotificator.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class JobService {

	private final JobRepository repository;
	private final CrawlerService crawlerService;

	@Autowired
	public JobService(JobRepository repository, CrawlerService crawlerService) {
		this.repository = repository;
		this.crawlerService = crawlerService;
	}

	public Set<Job> findFromSourcePage(SourcePage sourcePage) {
		CrawlerArgs args = CrawlerArgs.initBuilder()
				.withStartUrl(sourcePage.getUrl())
				.withSelectors(Collections.singletonList(sourcePage.getAdditionalSelector()))
				.build();

		return crawlerService.crawl(args)
				.stream()
				.map(pageLink -> findOrCreateJob(pageLink.getUrl(), sourcePage))
				.collect(Collectors.toSet());
	}

	private Job findOrCreateJob(String url, SourcePage sourcePage) {
		return repository
				.findByUrlAndSourcePageId(url, sourcePage.getId())
				.orElseGet(() -> {
					Job job = new Job();
					job.setUrl(url);
					job.setSourcePage(sourcePage);
					return repository.save(job);
				});
	}
}
