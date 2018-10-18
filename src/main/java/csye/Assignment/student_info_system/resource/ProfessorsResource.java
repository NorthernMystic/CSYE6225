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
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import csye.Assignment.student_info_system.datamodel.Professor;
import csye.Assignment.student_info_system.service.ProfessorsService;



// .. /webapi/professors
// webapi is defined in the src/main/webapp/web-inf/web.xml -> url pattern
@Path("professors")
public class ProfessorsResource {

	ProfessorsService profService = new ProfessorsService();
	
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Professor> getProfessorsByProgram(
			@QueryParam("program") String program) {
		
		try {
			if (program == null) {
				return profService.getAllProfessors();
			}
			return profService.getProfessorsByProgram(program);

		} catch (Exception e) {
		    e.printStackTrace();
		}
		
		return null;
	}

	
	
	
	// ... webapi/professor/1 
	@GET
	@Path("/{professorId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Professor getProfessor(@PathParam("professorId") long profId) {
		return profService.getProfessor(profId);
	}
	
	@DELETE
	@Path("/{professorId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Professor deleteProfessor(@PathParam("professorId") long profId) {
		return profService.deleteProfessor(profId);
	}
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Professor addProfessor(Professor prof) {
			return profService.addProfessor(prof);
	}
	
	@PUT
	@Path("/{professorId}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Professor updateProfessor(@PathParam("professorId") long profId, 
			Professor prof) {
		return profService.updateProfessorInformation(profId, prof);
	}
	
	public void addProfessor(String name, String department) {
		profService.addProfessor(name, department);
	}
 }
