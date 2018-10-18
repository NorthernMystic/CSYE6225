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
import csye.Assignment.student_info_system.service.CoursesService;



// .. /webapi/Courses
// webapi is defined in the src/main/webapp/web-inf/web.xml -> url pattern
@Path("Courses")
public class CoursesResource {

	CoursesService CourseService = new CoursesService();
	
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Course> getAllCoursesByProgram() {
		
		try {
			return CourseService.getAllCourses();
		} catch (Exception e) {
		    e.printStackTrace();
		}
		
		return null;
	}
	
	@GET
	@Path("/{CourseId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Course getCourse(@PathParam("CourseId") long CourseId) {
		return CourseService.getCourse(CourseId);
	}
	
	@DELETE
	@Path("/{CourseId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Course deleteCourse(@PathParam("CourseId") long CourseId) {
		return CourseService.deleteCourse(CourseId);
	}
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Course addCourse(Course Course) {
			return CourseService.addCourse(Course);
	}
	
	@PUT
	@Path("/{CourseId}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Course updateCourse(@PathParam("CourseId") long CourseId, 
			Course Course) {
		return CourseService.updateCourseInformation(CourseId, Course);
	}
	
 }
