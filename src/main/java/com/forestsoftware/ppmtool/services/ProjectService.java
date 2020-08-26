package com.forestsoftware.ppmtool.services;

import com.forestsoftware.ppmtool.domain.Backlog;
import com.forestsoftware.ppmtool.domain.Project;
import com.forestsoftware.ppmtool.domain.User;
import com.forestsoftware.ppmtool.exceptions.ProjectIdException;
import com.forestsoftware.ppmtool.exceptions.ProjectNotFoundException;
import com.forestsoftware.ppmtool.repositories.BacklogRepository;
import com.forestsoftware.ppmtool.repositories.ProjectRepository;
import com.forestsoftware.ppmtool.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private BacklogRepository backlogRepository;

    @Autowired
    private UserRepository userRepository;

    public Project saveOrUpdateProject(Project project, String username){

        if(project.getId() != null){
            Project existingProject = projectRepository.findByProjectIdentifier(project.getProjectIdentifier());
            if(existingProject != null && (!existingProject.getProjectLeader().equals(username))){
                throw new ProjectNotFoundException("Project not found in your account");
            }else if(existingProject == null){
                throw new ProjectNotFoundException("Project with ID '"+project.getProjectIdentifier()+" can not be updated because it doesn't exist");
            }
        }
        try {
            User user = userRepository.findByUsername(username);
            project.setUser(user);
            project.setProjectLeader(username);
            project.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
            if(project.getId() == null){
                Backlog backlog = new Backlog();
                project.setBacklog(backlog);
                backlog.setProject(project);
                backlog.setProjectIdentifier(project.getProjectIdentifier().toUpperCase().toUpperCase());
            }
            else {
                project.setBacklog(backlogRepository.findByProjectIdentifier(project.getProjectIdentifier().toUpperCase().toUpperCase()));
            }
            return projectRepository.save(project);
        } catch (Exception e){
            throw new ProjectIdException("Project ID '" + project.getProjectIdentifier().toUpperCase()+"' already exists");
        }

    }
    public Project findProjectByIdentifier(String projectId, String username){
        Project project =  projectRepository.findByProjectIdentifier(projectId.toUpperCase());
        if(project == null){
            throw new ProjectIdException("Project ID '" + projectId+"' does not exists");
        }
        if(!project.getProjectLeader().equals(username)){
            throw new ProjectNotFoundException("Project not found in your account");
        }
        return project;
    }

    public Iterable<Project>findAllProjects(String username){
        return projectRepository.findAllByProjectLeader(username);
    }
    public void deleteProjectById(String id,String username){

        projectRepository.delete(findProjectByIdentifier( id, username));
    }
}
