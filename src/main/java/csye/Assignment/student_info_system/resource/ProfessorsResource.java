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
	public Professor getProfessor(@PathParam("professorId") String profId) {
		GenericServices service = GenericServices.getServiceInstance();
		
		return service.getItem(Professor.class, profId, "ProfessorId");
	}
	
	@DELETE
	@Path("/{professorId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Professor deleteProfessor(@PathParam("professorId") String profId) {
		GenericServices service = GenericServices.getServiceInstance();
		
		Professor prof = service.getItem(Professor.class, profId, "ProfessorId");
		if (prof == null) return null;
		return service.deleteItem(prof);
	}
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Professor addProfessor(Professor prof) {
		GenericServices service = GenericServices.getServiceInstance();
		
		service.addOrUpdateItem(prof);
		return prof;
	}
	
	@PUT
	@Path("/{professorId}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Professor updateProfessor(@PathParam("professorId") String profId, 
			Professor prof) {
		GenericServices service = GenericServices.getServiceInstance();
		Professor profToRemove = service.getItem(Professor.class, profId, "ProfessorId");
		service.deleteItem(profToRemove);

		//if the profId is not exist in the database, it will be created
		//if the profId is already existed in the database, it will be overwrited
		
		service.addOrUpdateItem(prof);
		return prof;
	}
	

 }
