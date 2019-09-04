/*
 * Copyright (c) 2019. BEST S.A. and/or its affiliates. All rights reserved.
 */
package com.mzweigert.jobnotificator.service;

import com.mzweigert.jobnotificator.model.Job;
import com.mzweigert.jobnotificator.util.MailContentGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class MailService {

	@Value("${email.username}")
	private String notificatorMail;

	private final MailContentGenerator contentGenerator;
	private final JavaMailSender javaMailSender;

	@Autowired
	public MailService(MailContentGenerator contentGenerator, JavaMailSender javaMailSender) {
		this.contentGenerator = contentGenerator;
		this.javaMailSender = javaMailSender;
	}

	public boolean sendJobs(Set<Job> jobsToSend, String receiverMail) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom(notificatorMail);
		message.setTo(receiverMail);
		message.setSubject(contentGenerator.generateSubject(jobsToSend.size()));
		message.setText(contentGenerator.generateContent(jobsToSend));
		try {
			javaMailSender.send(message);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}
