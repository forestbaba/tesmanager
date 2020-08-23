package com.forestsoftware.ppmtool.services;

import com.forestsoftware.ppmtool.domain.Backlog;
import com.forestsoftware.ppmtool.domain.Project;
import com.forestsoftware.ppmtool.exceptions.ProjectIdException;
import com.forestsoftware.ppmtool.repositories.BacklogRepository;
import com.forestsoftware.ppmtool.repositories.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private BacklogRepository backlogRepository;

    public Project saveOrUpdateProject(Project project){
        try {
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
    public Project findProjectByIdentifier(String projectId){
        Project project =  projectRepository.findByProjectIdentifier(projectId.toUpperCase());
        if(project == null){
            throw new ProjectIdException("Project ID '" + projectId+"' does not exists");
        }
        return project;
    }

    public Iterable<Project>findAllProjects(){
        return projectRepository.findAll();
    }
    public void deleteProjectById(String id){
        Project project = projectRepository.findByProjectIdentifier(id);
        if(project == null){
            throw new ProjectIdException("Project id '" +id.toUpperCase()+"' does not exist");
        }
        projectRepository.delete(project);
    }
}
