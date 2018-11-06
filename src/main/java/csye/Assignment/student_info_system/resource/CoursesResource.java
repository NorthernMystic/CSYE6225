package csye.Assignment.student_info_system.resource;


import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import csye.Assignment.student_info_system.datamodel.Course;
import csye.Assignment.student_info_system.service.GenericServices;

// .. /webapi/Courses
@Path("courses")
public class CoursesResource {
		
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Course> getAllCoursesByProgram() {
		GenericServices service = GenericServices.getServiceInstance();
		
		return service.getAllItems(Course.class);
	}
	
	// ... webapi/Course/1 
	@GET
	@Path("/{courseId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Course getCourse(@PathParam("courseId") long courseId) {
		GenericServices service = GenericServices.getServiceInstance();
		
		return service.getItem(Course.class, courseId);
	}
	
	@DELETE
	@Path("/{courseId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Course deleteCourse(@PathParam("courseId") long courseId) {
		GenericServices service = GenericServices.getServiceInstance();
		
		return service.deleteItem(Course.class, courseId);
	}
	

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Course addCourse(Course course) {
		GenericServices service = GenericServices.getServiceInstance();
		if (service.getItem(Course.class, course.getId()) != null) {
			System.out.println("the course with this id is exist");
			return null;
		}
		
		service.addOrUpdateItem(course);
		return course;
	}
	
	
	
	@PUT
	@Path("/{courseId}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Course updateCourse(@PathParam("courseId") long courseId, 
			Course course) {
		GenericServices service = GenericServices.getServiceInstance();
		if (courseId != course.getId()) {
			System.out.println("the course Id you input is different from the id in your course object");
			return null;
		}
		
		//if the CourseId is not exist in the database, it will be created
		//if the CourseId is already existed in the database, it will be overwrited
		service.addOrUpdateItem(course);
		return course;
	}
	
 }
