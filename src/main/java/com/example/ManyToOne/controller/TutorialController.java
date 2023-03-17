package com.example.ManyToOne.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import com.example.ManyToOne.entity.Tutorial;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"/api"})
public class TutorialController {
    @Autowired
    TutorialRepo tutorialRepo;

    public TutorialController() {}

    @GetMapping("/tutorials")  // to get all the tutorials from the table
    public ResponseEntity<List<Tutorial>> getAllTutorials(@RequestParam(required = false) String title) {
        List<Tutorial> tutorials = new ArrayList<Tutorial>();

        if (title == null)
            tutorialRepo.findAll().forEach(tutorials::add); // tutorials is an ArrayList object to which we are adding each element returned by the findAll() method of tutorialRepo .
        else
            tutorialRepo.findByTitleContaining(title).forEach(tutorials::add);

        if (tutorials.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(tutorials, HttpStatus.OK); // here the tutorials is returned .
    }

    @GetMapping("/tutorials/{id}") // returns the tutorial based on id
    public ResponseEntity <Tutorial> getTutorialById(@PathVariable long id){ // this is how we get the id using path param which is path variable
        Optional<Tutorial> tutorial = tutorialRepo.findById(id) ;
        if (tutorial.isPresent()) {
            return new ResponseEntity<>(tutorial.get(), HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND) ;
        }
    }

    @PostMapping({"/tutorials"}) // to add tutorial
    public ResponseEntity<Tutorial> createTutorial(@RequestBody Tutorial tutorial) {
        Tutorial _tutorial = (Tutorial)this.tutorialRepo.save(new Tutorial(tutorial.getTitle(), tutorial.getDescription(), true));
        return new ResponseEntity<>(_tutorial, HttpStatus.CREATED);
    }

    @PutMapping({"/tutorials/{id}"}) // to update tutorial with the id
    public ResponseEntity<Tutorial> updateTutorial(@PathVariable("id") long id, @RequestBody Tutorial tutorial) {
        Optional<Tutorial> optionalTutorial = this.tutorialRepo.findById(id);
        if (optionalTutorial.isPresent()) {
            Tutorial _tutorial = (Tutorial)optionalTutorial.get();
            _tutorial.setTitle(tutorial.getTitle());
            _tutorial.setDescription(tutorial.getDescription());
            _tutorial.setPublished(tutorial.isPublished());
            return new ResponseEntity<>((Tutorial)this.tutorialRepo.save(_tutorial), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping({"/tutorials/{id}"}) // to delete to the tutorial with id
    public ResponseEntity<HttpStatus> deleteTutorial(@PathVariable("id") long id) {
        this.tutorialRepo.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping({"/tutorials"}) // to delete all the tutorials from the table (tutorials)
    public ResponseEntity<HttpStatus> deleteAllTutorials() {
        this.tutorialRepo.deleteAll();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping({"/tutorials/published"}) // to get the tutorials which have published attribute value as one (True)
    public ResponseEntity<List<Tutorial>> findByPublished() {
        List<Tutorial> tutorials = this.tutorialRepo.findByPublished(true);
        return tutorials.isEmpty() ? new ResponseEntity<>(HttpStatus.NO_CONTENT) : new ResponseEntity<>(tutorials, HttpStatus.OK);
    }
}
