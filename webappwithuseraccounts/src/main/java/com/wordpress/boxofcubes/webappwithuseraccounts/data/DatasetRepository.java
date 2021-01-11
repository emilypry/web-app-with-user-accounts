package com.wordpress.boxofcubes.webappwithuseraccounts.data;

import java.util.List;

import com.wordpress.boxofcubes.webappwithuseraccounts.models.Dataset;
import com.wordpress.boxofcubes.webappwithuseraccounts.models.User;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DatasetRepository extends CrudRepository<Dataset, Integer>{
    public List<Dataset> findByOwner(User owner);
}
