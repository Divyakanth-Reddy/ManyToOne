package com.example.ManyToOne.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tutorials" )
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Tutorial {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "title")
    private String title;
    @Column(name = "description")
    private String description;
    @Column(name = "published")
    private boolean published;

    // this is to create a OneToMany bidirectional .
//     @OneToMany(mappedBy = "tutorial" , cascade = CascadeType.ALL, orphanRemoval = true )
//      private List<Comment> comments = new ArrayList<>(); ;
    // uncomment getter and setter for this .

// uncomment this when applying Bi Directional

//    public List<Comment> getComments() {
//        return comments;
//    }
//
//    public void setComments(List<Comment> comments) {
//        this.comments = comments;
//    }

    }
