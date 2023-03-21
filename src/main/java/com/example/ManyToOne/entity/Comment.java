package com.example.ManyToOne.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(
        name = "comments"
)
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Lob // ( here it is unnecessary ) when string exceeds the max size ( 65k  bytes ) , then this annotation will be helpful to map Large Objects
    private String content;

    // it is better to use @ManyToOne over @OneToMany for Unidirectional to save storage and other reasons .

    // the default fetch type of @ManyToOne is Eager , since this is bad for performance if we have more child entities , therefore we use Lazy .
    @ManyToOne(fetch = FetchType.LAZY, optional = false) // optional= false indicates that each Comment instance belongs to exactly one Tutorial instance .
    @JoinColumn(name = "tutorial_id", nullable = false) // name of the foreign key column and its value cannot be null
    @OnDelete(action = OnDeleteAction.CASCADE) // when a tutorial is deleted then the corresponding comments of that tutorialId will be deleted
    @JsonIgnore // this annotation is used to avoid circular reference , when the object is converted to JSON this field will not be included in the output .
    private Tutorial tutorial;

    public Comment() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Tutorial getTutorial() {
        return this.tutorial;
    }

    public void setTutorial(Tutorial tutorial) {
        this.tutorial = tutorial;
    }
}
