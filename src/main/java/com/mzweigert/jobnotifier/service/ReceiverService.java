package com.mzweigert.jobnotifier.service;

import com.mzweigert.jobnotifier.model.Receiver;
import com.mzweigert.jobnotifier.repository.ReceiverRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReceiverService {

	private final ReceiverRepository repository;

	@Autowired
	public ReceiverService(ReceiverRepository repository) {
		this.repository = repository;
	}

	public Receiver merge(Receiver receiver) {
		return repository.save(receiver);
	}

	public Iterable<Receiver> findAll() {
		return repository.findAll();
	}

	public Optional<Receiver> findById(Long id) {
		return repository.findById(id);
	}

	public void deleteById(Long id) {
		repository.deleteById(id);
	}

	public List<Receiver> findAllActive() {
		return repository.findAllActive();
	}
}
