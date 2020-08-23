package com.forestsoftware.ppmtool.services;

import com.forestsoftware.ppmtool.domain.Backlog;
import com.forestsoftware.ppmtool.domain.ProjectTask;
import com.forestsoftware.ppmtool.repositories.BacklogRepository;
import com.forestsoftware.ppmtool.repositories.ProjectTaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectTaskService {

    @Autowired
    private BacklogRepository backlogRepository;

    @Autowired
    private ProjectTaskRepository projectTaskRepository;

    public ProjectTask addProjectTask(String projectIdentifier, ProjectTask projectTask){
        Backlog backlog = backlogRepository.findByProjectIdentifier(projectIdentifier);
        projectTask.setBacklog(backlog);
        Integer backlogSequence = backlog.getPTSequence();
        backlogSequence++;
        System.out.println("========="+backlogSequence);

        backlog.setPTSequence(backlogSequence);

        projectTask.setProjectSequence(projectIdentifier+"-"+backlogSequence);
        projectTask.setProjectIdentfier(projectIdentifier);

        if( projectTask.getPriority() == null || projectTask.getPriority() ==0 ){
            projectTask.setPriority(3);
        }
        if(projectTask.getStatus() == null || projectTask.getStatus().equals("") ){
            projectTask.setStatus("TO_DO");
        }

        return projectTaskRepository.save(projectTask);
    }

     public Iterable<ProjectTask> findBacklogById(String backlog_id) {
        return projectTaskRepository.findByProjectIdentfierOrderByPriority(backlog_id);

    }
}
