package com.wordpress.boxofcubes.webappwithuseraccounts.data;

import com.wordpress.boxofcubes.webappwithuseraccounts.models.User;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Integer>{

    public User findByUsername(String username);
}
