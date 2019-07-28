package com.p2c.p2c.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "parent", indexes = {
        @Index(name = "idxemail", columnList = "email", unique = true),
        @Index(name = "idxusername", columnList = "username", unique = true)})
@Data
public class Parent extends BaseEntity {

    @NotEmpty(message = "First Name is required")
    @Column(nullable = false)
    private String firstName;

    @NotEmpty(message = "Last Name is required")
    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false, unique = true, length = 200)
    @Length(max = 200)
    @JsonIgnore
    private String username;

    @NotEmpty(message = "Password is required")
    @Column(nullable = false)
    @JsonIgnore
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;


    @Email(message = "Email should be valid")
    @Column(nullable = false, unique = true, length = 200)
    @Length(max = 200)
    private String email;

    @JsonIgnore
    private String ip;
    @JsonIgnore
    private String browser;

    private int isActive;

    @ManyToOne
    @JoinColumn
    private Role role;


}
