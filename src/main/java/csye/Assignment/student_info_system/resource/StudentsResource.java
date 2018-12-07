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

import csye.Assignment.student_info_system.datamodel.Student;
import csye.Assignment.student_info_system.service.GenericServices;
import csye.Assignment.student_info_system.service.RegisterService;

// .. /webapi/students
// webapi is defined in the src/main/webapp/web-inf/web.xml -> url pattern
@Path("students")
public class StudentsResource {
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Student> getAllStudentsByProgram() {
		GenericServices service = GenericServices.getServiceInstance();
		
		return service.getAllItems(Student.class);
	}
	
	// ... webapi/student/1 
	@GET
	@Path("/{studentId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Student getStudent(@PathParam("studentId") String studentId) {
		GenericServices service = GenericServices.getServiceInstance();
		
		return service.getItem(Student.class, studentId, "StudentId");
	}
	
	@DELETE
	@Path("/{studentId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Student deleteStudent(@PathParam("studentId") String studentId) {
		GenericServices service = GenericServices.getServiceInstance();
		RegisterService serviceR = RegisterService.getServiceInstance();
		Student student = service.getItem(Student.class, studentId, "StudentId");
		if (student == null) return null;
		
		for (String courseId: student.getEnrolledClass()) {
			if (student.getEmail() != null) {
				serviceR.subscribeTopic("arn:aws:sns:us-west-2:474694586562:" + courseId, student.getEmail());
			}
		}
		
		return service.deleteItem(student);
	}
	

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Student addstudent(Student student) {
		GenericServices service = GenericServices.getServiceInstance();
		
		service.addOrUpdateItem(student);
		return student;
	}
	
	
	
	@PUT
	@Path("/{studentId}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Student updatestudent(@PathParam("studentId") String studentId, 
			Student student) {
		GenericServices service = GenericServices.getServiceInstance();
		Student studentToRemove = service.getItem(Student.class, studentId, "StudentId");
		service.deleteItem(studentToRemove);
		RegisterService serviceR = RegisterService.getServiceInstance();
		for (String courseId: student.getEnrolledClass()) {
			if (student.getEmail() != null) {
				serviceR.subscribeTopic("arn:aws:sns:us-west-2:474694586562:" + courseId, student.getEmail());
			}
		}
		//if the studentId is not exist in the database, it will be created
		//if the studentId is already existed in the database, it will be overwrited
		service.addOrUpdateItem(student);
		return student;
	}
	
	
	//The register method
	@POST
	@Path("/{studentId}/register")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String updatestudent(@PathParam("studentId") String studentId, 
			String courseId) {
		RegisterService service = RegisterService.getServiceInstance();
		Boolean isRegistered = service.register(studentId, courseId);
		
		if (isRegistered) return "Registration success";
		return "Registration failed";
	}
 }
