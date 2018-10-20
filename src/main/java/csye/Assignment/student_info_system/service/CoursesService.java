package csye.Assignment.student_info_system.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import csye.Assignment.student_info_system.datamodel.InMemoryDatabase;
import csye.Assignment.student_info_system.datamodel.Course;


public class CoursesService {
	private long nextAvailableId = 0;
	static HashMap<Long, Course> course_Map = InMemoryDatabase.getCourseDB();
	
	// Getting a list of all Courses
	public List<Course> getAllCourses() {	
		//Getting the list
		List<Course> list = new ArrayList<>();
		for (Course Course : course_Map.values()) {
			list.add(Course);
		}
		return list ;
	}

	// Adding a Course
	public void addCourse() {
		//Create a Course Object
		Course Course = new Course();
		course_Map.put(nextAvailableId, Course);
		
		nextAvailableId++;
	}
	
	public Course addCourse(Course Course) {	
		course_Map.put(nextAvailableId, Course);
		nextAvailableId++;
		return Course;
	}
	
	// Getting One Course
	public Course getCourse(Long CourseId) {
		return course_Map.get(CourseId);
	}
	
	// Deleting a Course
	public Course deleteCourse(Long courseId) {
		if (!course_Map.containsKey(courseId)) return null;
		Course deletedCourseDetails = course_Map.get(courseId);
		course_Map.remove(courseId);
		return deletedCourseDetails;
	}
	
	// Updating Course Info
	public Course updateCourseInformation(Long CourseId, Course Course) {
		// Publishing New Values
		course_Map.put(CourseId, Course) ;
		
		return Course;
	}
	
		
}
