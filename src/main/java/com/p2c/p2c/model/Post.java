package com.p2c.p2c.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Data
@Table(name = "post")
public class Post extends BaseEntity
{
    @NotNull(message = "title is required")
    private String title;
    private String description;
    @NotNull
    private String lastUpdatedBy;
    @JsonIgnore
    private String ip;
    @JsonIgnore
    private String browser;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "post", fetch = FetchType.LAZY)
    private List<Album> albums;

}
