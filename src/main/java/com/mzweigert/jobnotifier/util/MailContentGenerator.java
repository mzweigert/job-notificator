package com.mzweigert.jobnotifier.util;

import com.mzweigert.jobnotifier.model.Job;

import java.util.Collection;

public interface MailContentGenerator {
	String generateSubject(int jobsSize);
	String generateContent(Collection<Job> jobs);
}
