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
	public Course getCourse(@PathParam("courseId") String courseId) {
		GenericServices service = GenericServices.getServiceInstance();
		
		return service.getItem(Course.class, courseId, "CourseId");
	}
	
	@DELETE
	@Path("/{courseId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Course deleteCourse(@PathParam("courseId") String courseId) {
		GenericServices service = GenericServices.getServiceInstance();
		
		Course course = service.getItem(Course.class, courseId, "CourseId");
		if (course == null) return null;
		return service.deleteItem(course);
	}
	

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Course addCourse(Course course) {
		GenericServices service = GenericServices.getServiceInstance();
		
		service.addOrUpdateItem(course);
		return course;
	}
	
	
	
	@PUT
	@Path("/{courseId}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Course updateCourse(@PathParam("courseId") String courseId, 
			Course course) {
		GenericServices service = GenericServices.getServiceInstance();
		Course courseToRemove = service.getItem(Course.class, courseId, "courseId");
		service.deleteItem(courseToRemove);

		//if the courseId is not exist in the database, it will be created
		//if the courseId is already existed in the database, it will be overwrited

		service.addOrUpdateItem(course);
		return course;
		
	}
	
 }
