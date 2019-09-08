package com.mzweigert.jobnotifier.repository;

import com.mzweigert.jobnotifier.model.Receiver;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface ReceiverRepository extends CrudRepository<Receiver, Long> {

	@Query("select distinct r from Receiver r " +
			"left join fetch r.sentJobs sj " +
			"left join fetch r.subscribedSourcePages ssp " +
			"where r.active = 1")
	List<Receiver> findAllActive();
}
