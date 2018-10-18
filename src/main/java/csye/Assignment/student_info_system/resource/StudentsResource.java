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
import csye.Assignment.student_info_system.service.StudentsService;



// .. /webapi/students
// webapi is defined in the src/main/webapp/web-inf/web.xml -> url pattern
@Path("students")
public class StudentsResource {

	StudentsService studentService = new StudentsService();
	
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Student> getAllStudentsByProgram() {
		
		try {
			return studentService.getAllStudents();
		} catch (Exception e) {
		    e.printStackTrace();
		}
		
		return null;
	}
	
	// ... webapi/student/1 
	@GET
	@Path("/{studentId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Student getStudent(@PathParam("studentId") long studentId) {
		return studentService.getStudent(studentId);
	}
	
	@DELETE
	@Path("/{studentId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Student deleteStudent(@PathParam("studentId") long studentId) {
		return studentService.deleteStudent(studentId);
	}
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Student addstudent(Student student) {
			return studentService.addStudent(student);
	}
	
	@PUT
	@Path("/{studentId}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Student updatestudent(@PathParam("studentId") long studentId, 
			Student student) {
		return studentService.updateStudentInformation(studentId, student);
	}
	
 }
