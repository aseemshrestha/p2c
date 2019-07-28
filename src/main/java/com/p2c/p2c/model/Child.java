package com.p2c.p2c.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.p2c.p2c.constants.Gender;
import lombok.Data;
import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Date;

@Entity
@Data
public class Child extends BaseEntity
{

    @NotEmpty( message = "First Name is required" )
    @Column( nullable = false )
    private String firstName;

    @NotEmpty( message = "Last Name is required" )
    @Column( nullable = false )
    private String lastName;

    @Enumerated( EnumType.STRING )
    private Gender gender;

    @Column( nullable = false )
    private Date dob;

    @ManyToOne
    @JoinColumn( name = "parent_id", nullable = false )
    @JsonIgnore
    private Parent parent;

}
