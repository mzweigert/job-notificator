package com.mzweigert.jobnotificator.service;

import com.mzweigert.jobnotificator.model.Receiver;
import com.mzweigert.jobnotificator.repository.ReceiverRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ReceiverService {

	private final ReceiverRepository repository;

	@Autowired
	public ReceiverService(ReceiverRepository repository) {
		this.repository = repository;
	}

	public Receiver merge(Receiver receiver) {
		if (receiver.getId() == null) {
			return repository.save(receiver);
		} else {
			Optional<Receiver> receiverOptional = repository.findById(receiver.getId());
			if (!receiverOptional.isPresent()) {
				return null;
			}
			Receiver foundEntity = receiverOptional.get();
			foundEntity.setMail(receiver.getMail());
			foundEntity.setSubscribedSourcePages(receiver.getSubscribedSourcePages());
			repository.save(foundEntity);
			return foundEntity;
		}
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
}
