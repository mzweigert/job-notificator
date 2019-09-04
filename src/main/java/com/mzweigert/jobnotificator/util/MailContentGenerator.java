/*
 * Copyright (c) 2019. BEST S.A. and/or its affiliates. All rights reserved.
 */
package com.mzweigert.jobnotificator.util;

import com.mzweigert.jobnotificator.model.Job;

import java.util.Collection;

public interface MailContentGenerator {
	String generateSubject(int jobsSize);
	String generateContent(Collection<Job> jobs);
}
