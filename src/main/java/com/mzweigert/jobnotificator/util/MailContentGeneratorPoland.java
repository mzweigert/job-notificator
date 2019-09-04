/*
 * Copyright (c) 2019. BEST S.A. and/or its affiliates. All rights reserved.
 */
package com.mzweigert.jobnotificator.util;

import com.mzweigert.jobnotificator.model.Job;

import java.util.Collection;
import java.util.stream.Collectors;

public class MailContentGeneratorPoland implements MailContentGenerator {

	@Override
	public String generateSubject(int jobsSize) {
		return "Znaleziono " + (jobsSize > 1? "nowe oferty" : "nową ofertę") + " pracy.";
	}

	@Override
	public String generateContent(Collection<Job> jobs) {
		return jobs.stream()
				.map(Job::getUrl)
				.collect(Collectors.joining(", \n"));
	}
}
