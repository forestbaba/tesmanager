package com.forestsoftware.ppmtool.services;

import com.forestsoftware.ppmtool.domain.Backlog;
import com.forestsoftware.ppmtool.domain.ProjectTask;
import com.forestsoftware.ppmtool.exceptions.ProjectNotFoundException;
import com.forestsoftware.ppmtool.repositories.BacklogRepository;
import com.forestsoftware.ppmtool.repositories.ProjectRepository;
import com.forestsoftware.ppmtool.repositories.ProjectTaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectTaskService {

    @Autowired
    private BacklogRepository backlogRepository;

    @Autowired
    private ProjectTaskRepository projectTaskRepository;
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private ProjectService projectService;

    public ProjectTask addProjectTask(String projectIdentifier, ProjectTask projectTask, String username) {
//        try {
            Backlog backlog = projectService.findProjectByIdentifier(projectIdentifier, username).getBacklog(); //backlogRepository.findByProjectIdentifier(projectIdentifier);
            projectTask.setBacklog(backlog);
            Integer backlogSequence = backlog.getPTSequence();
            backlogSequence++;
            System.out.println("=========" + backlogSequence);

            backlog.setPTSequence(backlogSequence);

            projectTask.setProjectSequence(projectIdentifier + "-" + backlogSequence);
            projectTask.setProjectIdentfier(projectIdentifier);

            if (projectTask.getPriority() == 0 || projectTask.getPriority() == null) {
                projectTask.setPriority(3);
            }
            if (projectTask.getStatus() == null || projectTask.getStatus().equals("")) {
                projectTask.setStatus("TO_DO");
            }

            return projectTaskRepository.save(projectTask);
//        } catch (Exception e) {
//            throw new ProjectNotFoundException("Project not found");
//        }
    }

    public Iterable<ProjectTask> findBacklogById(String backlog_id, String username) {
//        Project project = projectRepository.findByProjectIdentifier(backlog_id);
//        if (project == null) {
//            throw new ProjectNotFoundException("Project with ID '" + backlog_id + "' Does not exist");
//        }
        projectService.findProjectByIdentifier(backlog_id, username);
        return projectTaskRepository.findByProjectIdentfierOrderByPriority(backlog_id);

    }

    public ProjectTask findPTByProjectSequence(String backlog_id, String pt_id, String username) {

//        Backlog backlog = backlogRepository.findByProjectIdentifier(backlog_id);
//        if (backlog == null) {
//            throw new ProjectNotFoundException("Project with ID '" + backlog_id + "' does not exist");
//        }
        projectService.findProjectByIdentifier(backlog_id, username);
        ProjectTask projectTask = projectTaskRepository.findByProjectSequence(pt_id);
        if (projectTask == null) {
            throw new ProjectNotFoundException("Project task '" + pt_id + "' not found");
        }
        if (!projectTask.getProjectIdentfier().equals(backlog_id)) {
            throw new ProjectNotFoundException("Project task '" + pt_id + "' does not exist in this project: " + backlog_id);
        }
        return projectTask;
    }
    public ProjectTask updateByProjectSequence(ProjectTask updatedTask, String backlog_id, String pt_id,String username)
    {
        ProjectTask projectTask =findPTByProjectSequence(backlog_id, pt_id, username);
        projectTask = updatedTask;
        return projectTaskRepository.save(projectTask);
    }

    public void deletePTByProjectSequence(String backlog_id, String pt_id, String username){
        ProjectTask projectTask = findPTByProjectSequence(backlog_id, pt_id, username);
        projectTaskRepository.delete(projectTask);
    }
}
