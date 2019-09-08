package com.mzweigert.jobnotifier.service;

import com.mzweigert.jobnotifier.model.Job;
import com.mzweigert.jobnotifier.util.MailContentGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class MailService {

	@Value("${email.username}")
	private String notifierMail;

	private final MailContentGenerator contentGenerator;
	private final JavaMailSender javaMailSender;

	@Autowired
	public MailService(MailContentGenerator contentGenerator, JavaMailSender javaMailSender) {
		this.contentGenerator = contentGenerator;
		this.javaMailSender = javaMailSender;
	}

	public boolean sendJobs(Set<Job> jobsToSend, String receiverMail) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom(notifierMail);
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
