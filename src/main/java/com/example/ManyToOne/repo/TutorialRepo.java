package com.example.ManyToOne.repo;


import java.util.List;

import com.example.ManyToOne.entity.Tutorial;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TutorialRepo extends JpaRepository<Tutorial, Long> {

    // Spring Data JPA uses a technique called Query Creation to generate queries based on the method names.

    // Spring Data JPA automatically creates a query based on the method name

    // naming convention should be like this : prefix[EntityAttribute][OperationalOperator]
    List<Tutorial> findByPublished(boolean published);

    List<Tutorial> findByTitleContaining(String title);
}
