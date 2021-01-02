package com.wordpress.boxofcubes.webappwithuseraccounts.data;

import com.wordpress.boxofcubes.webappwithuseraccounts.models.Dataset;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DatasetRepository extends CrudRepository<Dataset, Integer>{
}
