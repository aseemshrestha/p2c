package com.p2c.p2c.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Table( name = "album" )
public class Album extends BaseEntity
{

    @Column( nullable = false )
    private String albumName;
    @Column( nullable = false )
    private String title;
    private String description;
    @JsonIgnore
    @ManyToOne
    @JoinColumn( name = "post_id", nullable = false )
    private Post post;
    @OneToMany( cascade = CascadeType.ALL, mappedBy = "album", fetch = FetchType.EAGER )
    private List<Media> media;

}
