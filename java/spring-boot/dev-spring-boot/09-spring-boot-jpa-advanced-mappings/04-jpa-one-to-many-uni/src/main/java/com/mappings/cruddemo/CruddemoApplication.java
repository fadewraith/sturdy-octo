package com.mappings.cruddemo;

import com.mappings.cruddemo.dao.AppDAO;
import com.mappings.cruddemo.entity.Course;
import com.mappings.cruddemo.entity.Instructor;
import com.mappings.cruddemo.entity.InstructorDetail;
import com.mappings.cruddemo.entity.Review;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class CruddemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(CruddemoApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner (AppDAO appDAO) {
		return runner -> {
//			createCourseAndReviews(appDAO);

//			retrieveCourseAndReviews(appDAO);

			deleteCourseAndReviews(appDAO);
		};
	}

	private void deleteCourseAndReviews(AppDAO appDAO) {
		int id = 10;
		System.out.println("Deleting course id: " + id);;
		appDAO.deleteCourseById(id);
		System.out.println("Done!");
	}

	private void retrieveCourseAndReviews(AppDAO appDAO) {
		int id = 10;
		Course course = appDAO.findCourseAndReviewsByCourseId(id);
		System.out.println(course);
		System.out.println(course.getReviews());
		System.out.println("Done!");
	}

	private void createCourseAndReviews(AppDAO appDAO) {
		Course course = new Course("Pacman - How to score good points!");
		course.addReview(new Review("Nice course"));
		course.addReview(new Review("Well done"));
		course.addReview(new Review("Not upto the mark"));
		System.out.println("Saving the course");
		System.out.println(course);
		System.out.println(course.getReviews());
		appDAO.save(course);
		System.out.println("Done");
	}

	private void deleteCourse(AppDAO appDAO) {
		int id = 10;
		System.out.println("deleting course id: " + id);
		appDAO.deleteCourseById(id);
		System.out.println("done!");
	}

	private void updateCourse(AppDAO appDAO) {
		int id = 11;
		System.out.println("finding course id: " + id);
		Course course = appDAO.findCourseById(id);
		System.out.println("updating course id: " + id);
		course.setTitle("Reusable Forms");
		appDAO.update(course);
		System.out.println("Done!");
	}

	private void updateInstructor(AppDAO appDAO) {
		int id = 1;
		System.out.println("finding instructor id: " + id);
		Instructor instructor = appDAO.findInstructorById(id);
		System.out.println("updating instructor id: " + id);
		instructor.setLastName("Developer");
		appDAO.update(instructor);
		System.out.println("Done!");
	}

	private void findInstructorWithCoursesJoinFetch(AppDAO appDAO) {
		int id = 1;
		System.out.println("finding instructor id: " + id);
		Instructor instructor = appDAO.findInstructorByIdJoinFetch(id);
		System.out.println("instructor: " + instructor);
		System.out.println("associated courses: " + instructor.getCourses());
		System.out.println("done!");
	}

	private void findCoursesForInstructor(AppDAO appDAO) {
		int id = 1;
		System.out.println("Finding instructor id: " + id);
		Instructor instructor = appDAO.findInstructorById(id);
		System.out.println("instructor: " + instructor);

		System.out.println("finding courses for instructor id: " + id);
		List<Course> courses = appDAO.findCoursesByInstructorId(id);

		instructor.setCourses(courses);

		System.out.println("associated courses: " + instructor.getCourses());

		System.out.println("Done!");
	}

	private void findInstructorWithCourses(AppDAO appDAO) {
		int id = 1;
		System.out.println("Finding instructor id: " + id);
		Instructor instructor = appDAO.findInstructorById(id);
		System.out.println("instructor: " + instructor);
		System.out.println("associated courses: " + instructor.getCourses());
		System.out.println("Done!");
	}

	private void createInstructorWithCourses(AppDAO appDAO) {

		Instructor instructor = new Instructor("Dmytro ", "Mezhenskyi", "dmytro@decodedfrontend.io");

//		create the instructor detail
		InstructorDetail instructorDetail = new InstructorDetail(
				"www.youtube.com/@DecodedFrontend",
				"Angular Developer!!!"
		);

//		associate the objects
		instructor.setInstructorDetail(instructorDetail);

//		create some courses
		Course course1 = new Course("SOLID Principles");
		Course course2 = new Course("Advance Angular Concepts");
		Course course3 = new Course("Advanced Reactive Forms");

		instructor.add(course1);
		instructor.add(course2);
		instructor.add(course3);

//		this will also save the courses bcoz of CASCADE type persist
		System.out.println("Saving instructor: " + instructor);
		System.out.println("The courses: " + instructor.getCourses());

		appDAO.save(instructor);

		System.out.println("Done!");

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
