package com.mzweigert.jobnotificator.repository;

import com.mzweigert.jobnotificator.model.Receiver;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface ReceiverRepository extends CrudRepository<Receiver, Long> {

	@Query("select r from Receiver r where active = 1")
	List<Receiver> findAllActive();
}
