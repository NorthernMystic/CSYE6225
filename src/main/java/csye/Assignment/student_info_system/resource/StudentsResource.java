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
	public Student getStudent(@PathParam("studentId") long studentId) {
		GenericServices service = GenericServices.getServiceInstance();
		
		return service.getItem(Student.class, studentId);
	}
	
	@DELETE
	@Path("/{studentId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Student deleteStudent(@PathParam("studentId") long studentId) {
		GenericServices service = GenericServices.getServiceInstance();
		
		return service.deleteItem(Student.class, studentId);
	}
	

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Student addstudent(Student student) {
		GenericServices service = GenericServices.getServiceInstance();
		if (service.getItem(Student.class, student.getId()) != null) {
			System.out.println("the student with this id is exist");
			return null;
		}
		
		service.addOrUpdateItem(student);
		return student;
	}
	
	
	
	@PUT
	@Path("/{studentId}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Student updatestudent(@PathParam("studentId") long studentId, 
			Student student) {
		GenericServices service = GenericServices.getServiceInstance();
		if (studentId != student.getId()) {
			System.out.println("the student Id you input is different from the id in your student object");
			return null;
		}
		
		//if the studentId is not exist in the database, it will be created
		//if the studentId is already existed in the database, it will be overwrited
		service.addOrUpdateItem(student);
		return student;
	}
	
 }
