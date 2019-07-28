package com.p2c.p2c.controllers;

import com.p2c.p2c.exception.ResourceNotFoundException;
import com.p2c.p2c.model.Child;
import com.p2c.p2c.model.Parent;
import com.p2c.p2c.model.viewmodel.ParentChild;
import com.p2c.p2c.repository.ChildRepository;
import com.p2c.p2c.service.ParentService;
import com.p2c.p2c.validations.Validation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping( "/api/user" )
public class UserController
{

    private final ChildRepository childRepository;
    private final Validation validationService;
    private final ParentService parentService;

    public UserController(ChildRepository childRepository,
        Validation validation, ParentService parentService)
    {
        this.childRepository = childRepository;
        this.validationService = validation;
        this.parentService = parentService;
    }

    @GetMapping( "parent/{username}/child" )
    public ResponseEntity<?> getAllChildrenByParent(@PathVariable( "username" ) @NotNull String username,
        HttpServletRequest request)
    {

        validationService.isLoggedUserValid(username, request);
        Optional<List<Child>> childByParentUsername = this.childRepository.findChildByParentUsername(username);
        if (childByParentUsername.isPresent()) {
            return new ResponseEntity<>(childByParentUsername.get(), HttpStatus.OK);
        }
        throw new ResourceNotFoundException("No child found for username:-" + username);
    }

    @GetMapping( "{username}" )
    public ResponseEntity<Object> getUserByUsername(@PathVariable( "username" ) @NotNull String username,
        HttpServletRequest request) throws Exception
    {
        validationService.isLoggedUserValid(username, request);
        ParentChild parentWithChild = parentService.getParentWithChild(username);
        return new ResponseEntity<>(parentWithChild, HttpStatus.OK);
    }

    @PostMapping( "addNew/child" )
    public ResponseEntity<Child> registerChild(@RequestBody Child child, HttpServletRequest request)
    {
        String username = request.getUserPrincipal().getName();
        Parent parent = parentService.findParentByUsername(username);
        child.setCreated(new Date());
        child.setLastUpdated(new Date());
        child.setParent(parent);
        this.childRepository.save(child);
        return new ResponseEntity<>(child, HttpStatus.CREATED);
    }
}

