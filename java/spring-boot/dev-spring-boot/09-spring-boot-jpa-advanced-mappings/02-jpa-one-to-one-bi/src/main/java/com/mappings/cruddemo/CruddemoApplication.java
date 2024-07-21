package com.mappings.cruddemo;

import com.mappings.cruddemo.dao.AppDAO;
import com.mappings.cruddemo.entity.Instructor;
import com.mappings.cruddemo.entity.InstructorDetail;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class CruddemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(CruddemoApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner (AppDAO appDAO) {
		return runner -> {
//			createInstructor(appDAO);

//			findInstructor(appDAO);

//			deleteInstructor(appDAO);

//			findInstructorDetail(appDAO);

			deleteInstructorDetail(appDAO);
		};
	}

	private void deleteInstructorDetail(AppDAO appDAO) {
		int id = 3;
		System.out.println("Deleting instructor id: " + id);
		appDAO.deleteInstructorDetailById(id);
		System.out.println("Done!");
	}

	private void findInstructorDetail(AppDAO appDAO) {
		int id = 2;
		InstructorDetail instructorDetail = appDAO.findInstructorDetailById(id);
		System.out.println("instructor details: " + instructorDetail);
		System.out.println("associated instructor: " + instructorDetail.getInstructor());
		System.out.println("Done!");
	}

	private void deleteInstructor(AppDAO appDAO) {
		int id = 1;
		System.out.println("Deleting instructor: " + id);
		appDAO.deleteInstructorById(id);
		System.out.println("Done!");
	}

	private void findInstructor(AppDAO appDAO) {
		int id = 2;
		System.out.println("Finding instructor id: " + id);
		Instructor instructor = appDAO.findInstructorById(id);
		System.out.println("Instructor is: " + instructor);
		System.out.println("associated instructor detail only: " + instructor.getInstructorDetail());
	}

	private void createInstructor(AppDAO appDAO) {
//		create the instructor
//		Instructor instructor = new Instructor("Chad", "Darby", "darby@luv2code.com");
		Instructor instructor = new Instructor("Dmytro ", "Mezhenskyi", "dmytro@decodedfrontend.io");

//		create the instructor detail
		InstructorDetail instructorDetail = new InstructorDetail(
				"www.youtube.com/@DecodedFrontend",
				"Angular Developer!!!"
		);

//		associate the objects
		instructor.setInstructorDetail(instructorDetail);

//		save the instructor
//		this will also save the details object, because of CascadeType.ALL
		System.out.println("Saving instructor: " + instructor);
		appDAO.save(instructor);
		System.out.println("Done!");
	}

}
