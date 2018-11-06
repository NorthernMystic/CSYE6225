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
import csye.Assignment.student_info_system.service.GenericServices;



// .. /webapi/professors
// webapi is defined in the src/main/webapp/web-inf/web.xml -> url pattern
@Path("professors")
public class ProfessorsResource {
	
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Professor> getProfessorsByProgram(
			@QueryParam("program") String program) {
		
		try {
			GenericServices service = GenericServices.getServiceInstance();
			
			return service.getAllItems(Professor.class);

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
		GenericServices service = GenericServices.getServiceInstance();
		
		return service.getItem(Professor.class, profId);
	}
	
	@DELETE
	@Path("/{professorId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Professor deleteProfessor(@PathParam("professorId") long profId) {
		GenericServices service = GenericServices.getServiceInstance();
		
		return service.deleteItem(Professor.class, profId);
	}
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Professor addProfessor(Professor prof) {
		GenericServices service = GenericServices.getServiceInstance();
		if (service.getItem(Professor.class, prof.getId()) != null) {
			System.out.println("the professor with this id is exist");
			return null;
		}
		
		service.addOrUpdateItem(prof);
		return prof;
	}
	
	@PUT
	@Path("/{professorId}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Professor updateProfessor(@PathParam("professorId") long profId, 
			Professor prof) {
		GenericServices service = GenericServices.getServiceInstance();
		if (profId != prof.getId()) {
			System.out.println("the PROFESSOR Id you input is different from the id in your professor object");
			return null;
		}
		
		//if the studentId is not exist in the database, it will be created
		//if the studentId is already existed in the database, it will be overwrited
		service.addOrUpdateItem(prof);
		return prof;
	}
	

 }
