package com.forestsoftware.ppmtool.repositories;

import com.forestsoftware.ppmtool.domain.ProjectTask;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectTaskRepository extends CrudRepository<ProjectTask, Long> {

    List<ProjectTask> findByProjectIdentfierOrderByPriority(String s);

   ProjectTask findByProjectSequence (String pt_id);
}
