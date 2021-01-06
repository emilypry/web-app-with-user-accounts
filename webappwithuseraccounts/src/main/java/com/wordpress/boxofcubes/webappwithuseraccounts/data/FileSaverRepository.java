package com.wordpress.boxofcubes.webappwithuseraccounts.data;

import com.wordpress.boxofcubes.webappwithuseraccounts.models.FileSaver;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileSaverRepository extends CrudRepository<FileSaver, Integer>{

    public FileSaver findById(int id);
}
