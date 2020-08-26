package com.forestsoftware.ppmtool.repositories;

import com.forestsoftware.ppmtool.domain.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    User findByUsername(String s);
    User getById(Long id);
}
