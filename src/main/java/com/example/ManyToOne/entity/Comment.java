package com.example.ManyToOne.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(
        name = "comments"
)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // @Lob  ( here it is unnecessary ) when string exceeds the max size ( 65k  bytes ) , then this annotation will be helpful to map Large Objects
    private String content;

    // it is better to use @ManyToOne over @OneToMany for Unidirectional to save storage and other reasons .

    // the default fetch type of @ManyToOne is Eager , since this is bad for performance if we have more child entities , therefore we use Lazy .
        @ManyToOne(fetch = FetchType.LAZY,  cascade = CascadeType.ALL) // optional= false indicates that each Comment instance belongs to exactly one Tutorial instance .
        @JoinColumn(name = "tutorial_id" )  // name of the foreign key column and its value cannot be null by default
        @OnDelete(action = OnDeleteAction.CASCADE) // when a tutorial is deleted then the corresponding comments of that tutorialId will be deleted only in bidirectional
        @JsonIgnore // this annotation is used to avoid circular reference , when the object is converted to JSON this field will not be included in the output .
        private Tutorial tutorial;

    public Comment( String content , Tutorial tutorial){
        this.content =content ;
        this.tutorial = tutorial ;
    }
    }
