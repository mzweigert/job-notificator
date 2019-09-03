package com.mzweigert.jobnotificator.service;

import com.mzweigert.jobnotificator.model.SourcePage;
import com.mzweigert.jobnotificator.repository.SourcePageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SourcePageService {

	private final SourcePageRepository repository;

	@Autowired
	public SourcePageService(SourcePageRepository repository) {
		this.repository = repository;
	}

	public SourcePage merge(SourcePage sourcePage) {
		if (sourcePage.getId() == null) {
			return repository.save(sourcePage);
		} else {
			Optional<SourcePage> page = repository.findById(sourcePage.getId());
			if (!page.isPresent()) {
				return null;
			}
			SourcePage foundEntity = page.get();
			foundEntity.setUrl(sourcePage.getUrl());
			foundEntity.setAdditionalSelector(sourcePage.getAdditionalSelector());
			repository.save(foundEntity);
			return foundEntity;
		}
	}

	public Iterable<SourcePage> findAll() {
		return repository.findAll();
	}

	public Optional<SourcePage> findById(Long id) {
		return repository.findById(id);
	}

	public void deleteById(Long id) {
		repository.deleteById(id);
	}
}
