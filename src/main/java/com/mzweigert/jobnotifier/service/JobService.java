package com.mzweigert.jobnotifier.service;

import com.mzweigert.crawler.model.link.PageLink;
import com.mzweigert.crawler.service.crawler.CrawlerArgs;
import com.mzweigert.crawler.service.crawler.CrawlerService;
import com.mzweigert.jobnotifier.model.Job;
import com.mzweigert.jobnotifier.model.SourcePage;
import com.mzweigert.jobnotifier.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class JobService {

	private static final Logger logger = Logger.getAnonymousLogger();

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

		try {
			Collection<PageLink> results = crawlerService.crawl(args);
			return results.stream()
					.filter(pageLink -> !pageLink.getUrl().equals(sourcePage.getUrl()))
					.map(pageLink -> findOrCreateJob(pageLink.getUrl(), sourcePage))
					.collect(Collectors.toSet());
		} catch (Exception e) {
			logger.log(Level.SEVERE, e.getMessage());
		}
		return new HashSet<>();
	}

	private synchronized Job findOrCreateJob(String url, SourcePage sourcePage) {
		logger.log(Level.INFO, "findByUrlAndSourcePageId: url - " + url );

		return repository
				.findByUrlAndSourcePageId(url, sourcePage.getId())
				.orElseGet(() -> createJob(url, sourcePage));
	}

	private Job createJob(String url, SourcePage sourcePage) {
		Job job = new Job();
		job.setUrl(url);
		job.setSourcePage(sourcePage);
		logger.log(Level.INFO, "Save job:" + job.getUrl());
		return repository.save(job);
	}
}
