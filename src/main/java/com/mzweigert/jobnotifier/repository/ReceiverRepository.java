package com.mzweigert.jobnotifier.repository;

import com.mzweigert.jobnotifier.model.Job;
import com.mzweigert.jobnotifier.model.Receiver;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;
import java.util.Set;

@RepositoryRestResource
public interface ReceiverRepository extends CrudRepository<Receiver, Long> {

	@Query("select distinct r from Receiver r " +
			"left join fetch r.sentJobs sj " +
			"left join fetch r.subscribedSourcePages ssp " +
			"where r.active = true")
	List<Receiver> findAllActive();


	@Query("select sj from Receiver r " +
			"left join r.sentJobs sj " +
			"where r.id = :id")
	Set<Job> findSentJobsByReceiverId(@Param("id") Long id);
}
