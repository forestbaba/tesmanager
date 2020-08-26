package com.forestsoftware.ppmtool.web;

import com.forestsoftware.ppmtool.domain.Project;
import com.forestsoftware.ppmtool.services.ErrorValidationService;
import com.forestsoftware.ppmtool.services.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@RestController
@RequestMapping("/api/project")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private ErrorValidationService errorValidationService;

    @PostMapping("")
    public ResponseEntity<?> createNewProject(@Valid @RequestBody Project project, BindingResult bindingResult, Principal principal){

        ResponseEntity<?>errorMap = errorValidationService.errorHandler(bindingResult);
        if(errorMap != null) return errorMap;
        Project project1 = projectService.saveOrUpdateProject(project, principal.getName());
        return new ResponseEntity<Project>(project, HttpStatus.CREATED);
    }
    @GetMapping("/{projectId}")
    public ResponseEntity<?> getProjectById(@PathVariable String projectId, Principal principal){
        Project project = projectService.findProjectByIdentifier(projectId.toUpperCase(), principal.getName());

        return new ResponseEntity<Project>(project, HttpStatus.OK);
    }

    @GetMapping("/all")
    public Iterable<Project>getAllProjects(Principal principal){
        return projectService.findAllProjects(principal.getName());
    }

    @DeleteMapping("/{projectId}")
    public ResponseEntity<?>deleteProject(@PathVariable String projectId,Principal principal){
        projectService.deleteProjectById(projectId, principal.getName());
        return new ResponseEntity<String>("Project with ID: '" + projectId + "'was deleted successfully", HttpStatus.OK);
    }
}
