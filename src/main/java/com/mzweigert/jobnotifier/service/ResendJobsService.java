package com.mzweigert.jobnotifier.service;

import com.mzweigert.jobnotifier.model.Job;
import com.mzweigert.jobnotifier.model.Receiver;
import com.mzweigert.jobnotifier.model.SourcePage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

@Service
public class ResendJobsService {

	private final ReceiverService receiverService;
	private final JobService jobService;
	private final MailService mailService;
	private final ExecutorService executorService;
	private final Map<SourcePage, Set<Job>> cache;

	@Autowired
	public ResendJobsService(ReceiverService receiverService, JobService jobService, MailService mailService) {
		this.receiverService = receiverService;
		this.jobService = jobService;
		this.mailService = mailService;
		this.executorService = Executors.newWorkStealingPool();
		this.cache = new ConcurrentHashMap<>();
	}

	public void resend() {
		Set<Receiver> activeReceivers = receiverService.findAllActive();
		System.out.println("Found " + activeReceivers.size() + " receivers.");

		activeReceivers
				.stream()
				.map(activeReceiver -> (Runnable) () -> resendJobsToReceiver(activeReceiver))
				.forEach(executorService::submit);
	}

	private void resendJobsToReceiver(Receiver activeReceiver) {
		Set<Job> foundJobs = getFromCache(activeReceiver);

		Set<Job> sentJobs = activeReceiver.getSentJobs();

		Set<Job> toSend = foundJobs.stream()
				.filter(foundJob -> !sentJobs.contains(foundJob))
				.collect(Collectors.toSet());

		if(toSend.isEmpty()) {
			return;
		}

		System.out.println("new jobs for " + activeReceiver.getMail());
		boolean mailSent = mailService.sendJobs(toSend, activeReceiver.getMail());
		if(mailSent) {
			activeReceiver.addSentJobs(toSend);
			receiverService.merge(activeReceiver);
		}
	}

	private Set<Job> getFromCache(Receiver activeReceiver) {
		return activeReceiver.getSubscribedSourcePages()
				.stream()
				.filter(SourcePage::isActive)
				.map(sourcePage -> cache.computeIfAbsent(sourcePage, jobService::findFromSourcePage))
				.flatMap(Set::stream)
				.collect(Collectors.toSet());
	}

}
