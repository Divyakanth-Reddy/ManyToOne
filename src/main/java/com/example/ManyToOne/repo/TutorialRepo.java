package com.example.ManyToOne.repo;


import java.util.List;

import com.example.ManyToOne.entity.Tutorial;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TutorialRepo extends JpaRepository<Tutorial, Long> {
    List<Tutorial> findByPublished(boolean published);

    List<Tutorial> findByTitleContaining(String title);
}
