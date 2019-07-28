package com.p2c.p2c.controllers;

import com.p2c.p2c.model.Child;
import com.p2c.p2c.model.Parent;
import com.p2c.p2c.repository.ChildRepository;
import com.p2c.p2c.repository.ParentRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping( "/api/admin" )
public class AdminController
{
    private final ParentRepository parentRepository;
    private final ChildRepository childRepository;

    public AdminController(ParentRepository parentRepository, ChildRepository childRepository)
    {
        this.parentRepository = parentRepository;
        this.childRepository = childRepository;
    }

    @GetMapping( "user/all" )
    public ResponseEntity<?> getAllUsers()
    {
        List<Parent> listOfUsers = this.parentRepository.findAll();
        return new ResponseEntity<>(listOfUsers, HttpStatus.OK);
    }

    @GetMapping( "child/all" )
    public ResponseEntity<?> getAllChildren()
    {
        List<Child> listOfUsers = this.childRepository.findAll();
        return new ResponseEntity<>(listOfUsers, HttpStatus.OK);
    }
}
