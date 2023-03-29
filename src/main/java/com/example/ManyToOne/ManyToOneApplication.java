package com.example.ManyToOne;

import com.example.ManyToOne.entity.Comment;
import com.example.ManyToOne.entity.Tutorial;
import com.example.ManyToOne.repo.CommentRepo;
import com.example.ManyToOne.repo.TutorialRepo;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.security.PrivateKey;
import java.util.Optional;


@SpringBootApplication
public class ManyToOneApplication implements CommandLineRunner {

	private static final Logger logger = LoggerFactory.getLogger(ManyToOneApplication.class);

	@Autowired
	private EntityManager entityManager ;

	@Autowired
	private CommentRepo commentRepo ;

	@Autowired
	private TutorialRepo tutorialRepo ;

	public static void main(String[] args) {
		//SpringApplication.run(ManyToOneApplication.class, args);
		SpringApplication app = new SpringApplication(ManyToOneApplication.class);
		app.run(args);
	}
		@Override
		@Transactional // added after the small error other than detached entity
		public void run(String... arg0) throws Exception
		{

		     	logger.info(  " Initial comments are  "  +  commentRepo.count() ) ;
			    logger.info(  " Initial tutorial are  "  +  tutorialRepo.count()  ) ;


			    Tutorial tutorial = new Tutorial() ;
				tutorial.setTitle(" Spring Boot ");
				tutorial.setDescription(" Spring boot basics for beginners ");
				tutorial.setPublished(true);
//				tutorialRepo.save(tutorial); // use this in case of Bi directional no need to use EntityManager persist .
			    entityManager.persist(tutorial);

				Comment comment1 = new Comment(  ) ;
				comment1.setContent("Awesome");
				comment1.setTutorial(tutorial);

				Comment comment2 = new Comment(  ) ;
			    comment2.setContent("Loved it");
				comment2.setTutorial(tutorial);

			    commentRepo.save(comment1);
				commentRepo.save(comment2);
//			    Comment comment2 = new Comment(  " Loved it " , tutorial) ;


//				commentRepo.save( comment1 ) ;
//			    commentRepo.save( comment2 ) ;

			// use this when there is @OneToMany Bi Directional
//				tutorial.getComments().add(comment1);
//				tutorial.getComments().add(comment2); // bi-directional relationship between the tutorial object and the comment2 object.

//				tutorialRepo.save(tutorial);


			    Tutorial tutorial2 = new Tutorial() ;
			    tutorial2.setTitle(" Spring ");
			    tutorial2.setDescription(" Spring basics for beginners ");
			    tutorial2.setPublished(true);
			 //	tutorialRepo.save(tutorial2);
				entityManager.persist(tutorial2);

			    Comment comment4 = new Comment( " woaah  " , tutorial2) ;
			    Comment comment5 = new Comment(  " nicee" , tutorial2) ;
			    Comment comment6	 = new Comment(  " liked it " , tutorial2) ;

		     	commentRepo.save( comment4 ) ;
			    commentRepo.save( comment5 ) ;
			    commentRepo.save( comment6 ) ;

//				tutorial2.getComments().add(comment4);
//				tutorial2.getComments().add(comment5);
//				tutorial2.getComments().add(comment6);


//				tutorialRepo.save(tutorial2);

			   logger.info(  " total tutorials before deleting are  "  +  tutorialRepo.count()  ) ;
			   logger.info(  "total comments before deleting tutorial  "  +  commentRepo.count() );

			   commentRepo.deleteByTutorialId(1); // since i used @ManyToOne , we cannot delete comments directly when we delete tutorial
			   tutorialRepo.deleteById(1L);  // otherwise

//			   tutorialRepo.deleteById(1L); // use this in case of Bi directional
//
     		   logger.info(  " total tutorials after deleting "  +  tutorialRepo.count()  ) ;
			   logger.info(  "total comments after deleting tutorial  "  +  commentRepo.count() );
		}
}
