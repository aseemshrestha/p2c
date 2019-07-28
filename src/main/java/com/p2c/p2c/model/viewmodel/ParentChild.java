package com.p2c.p2c.model.viewmodel;

import com.p2c.p2c.model.Child;
import lombok.Data;
import java.util.Date;
import java.util.List;

@Data
public class ParentChild
{
    private String firstName;
    private String lastName;
    private String email;
    private Date joinedOn;
    private List<Child> child;
}
