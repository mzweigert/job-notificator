package com.mzweigert.jobnotificator.repository;

import com.mzweigert.jobnotificator.model.SourcePage;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface SourcePageRepository extends CrudRepository<SourcePage, Long> {

}
