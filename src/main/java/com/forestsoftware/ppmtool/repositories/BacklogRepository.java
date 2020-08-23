package com.forestsoftware.ppmtool.repositories;

import com.forestsoftware.ppmtool.domain.Backlog;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BacklogRepository extends CrudRepository<Backlog, Long> {

    public Backlog findByProjectIdentifier(String s);
}
