package com.p2c.p2c.model;

import lombok.*;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.io.Serializable;
import java.util.Set;

@Entity
@Data
@Builder
@AllArgsConstructor

public class Role implements Serializable
{
    @Id
    private int id;
    private String role;
    public Role() {}
}
