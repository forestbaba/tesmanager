package com.forestsoftware.ppmtool.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@RestController
public class CustomResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    public final ResponseEntity<Object>handleProjectException(ProjectIdException e, WebRequest webRequest){
        ProjectExceptionResponse projectExceptionResponse = new ProjectExceptionResponse(e.getMessage());
        return new ResponseEntity<>(projectExceptionResponse, HttpStatus.BAD_REQUEST);
    }

    public final ResponseEntity<Object>handleProjectNotFOundException(ProjectNotFoundException e, WebRequest webRequest){
        ProjectNotFoundExceptionResponse projectExceptionResponse = new ProjectNotFoundExceptionResponse(e.getMessage());
        return new ResponseEntity<>(projectExceptionResponse, HttpStatus.BAD_REQUEST);
    }
 public final ResponseEntity<Object>handleUsernameAlreadyExist(UsernameAlreadyExistException e, WebRequest webRequest){
        UsernameAlreadyExistResponse exceptionResponse = new UsernameAlreadyExistResponse(e.getMessage());
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

}
