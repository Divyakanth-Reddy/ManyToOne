package com.example.ManyToOne.controller;

import java.util.List;
import java.util.Optional;

import com.example.ManyToOne.entity.Comment;
import com.example.ManyToOne.entity.Tutorial;
import com.example.ManyToOne.repo.CommentRepo;
import com.example.ManyToOne.repo.TutorialRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"/api"})
public class CommentController {
    @Autowired
    private TutorialRepo tutorialRepo;
    @Autowired
    private CommentRepo commentRepo;

    public CommentController() {
    }

    @GetMapping({"/tutorials/{tutorialId}/comments"})
    public ResponseEntity<List<Comment>> getAllCommentsByTutorialId(@PathVariable("tutorialId") Long tutorialId) {
        Optional<Tutorial> tutorial = this.tutorialRepo.findById(tutorialId);
        if (tutorial.isPresent()) {
            List<Comment> comments = this.commentRepo.findByTutorialId(tutorialId);
            return new ResponseEntity<>(comments, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/comments/{id}")
    public ResponseEntity<Comment> getCommentsByTutorialId(@PathVariable(value = "id") Long id) {
        Optional<Comment> comment = commentRepo.findById(id);
        if (comment.isPresent()) {
            return new ResponseEntity<>(comment.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping({"/tutorials/{tutorialId}/comments"})
    public ResponseEntity<Comment> createComment(@PathVariable("tutorialId") Long tutorialId, @RequestBody Comment commentRequest) {
        Optional<Tutorial> tutorial = this.tutorialRepo.findById(tutorialId);
        if (tutorial.isPresent()) {
            commentRequest.setTutorial(tutorial.get());
            Comment comment = this.commentRepo.save(commentRequest);
            return new ResponseEntity<>(comment, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @PutMapping("/comments/{id}")
    public ResponseEntity<Comment> updateComment(@PathVariable("id") long id, @RequestBody Comment commentRequest) {
        Optional<Comment> optionalComment = commentRepo.findById(id);
        if (optionalComment.isPresent()) {
            Comment comment = optionalComment.get();
            comment.setContent(commentRequest.getContent());
            return new ResponseEntity<>(commentRepo.save(comment), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }



    @DeleteMapping({"/comments/{id}"})
    public ResponseEntity<HttpStatus> deleteComment(@PathVariable("id") long id) {
        this.commentRepo.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping({"/tutorials/{tutorialId}/comments"})
    public ResponseEntity<List<Comment>> deleteAllCommentsOfTutorial(@PathVariable("tutorialId") Long tutorialId) {
        Optional<Tutorial> tutorial = this.tutorialRepo.findById(tutorialId);
        if (tutorial.isPresent()) {
            this.commentRepo.deleteByTutorialId(tutorialId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
