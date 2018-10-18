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

import csye.Assignment.student_info_system.datamodel.Program;
import csye.Assignment.student_info_system.service.ProgramsService;



// .. /webapi/Programs
// webapi is defined in the src/main/webapp/web-inf/web.xml -> url pattern
@Path("Programs")
public class ProgramsResource {

	ProgramsService ProgramService = new ProgramsService();
	
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Program> getAllProgramsByProgram() {
		
		try {
			return ProgramService.getAllPrograms();
		} catch (Exception e) {
		    e.printStackTrace();
		}
		
		return null;
	}
	
	@GET
	@Path("/{ProgramId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Program getProgram(@PathParam("ProgramId") long ProgramId) {
		return ProgramService.getProgram(ProgramId);
	}
	
	@DELETE
	@Path("/{ProgramId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Program deleteProgram(@PathParam("ProgramId") long ProgramId) {
		return ProgramService.deleteProgram(ProgramId);
	}
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Program addProgram(Program Program) {
			return ProgramService.addProgram(Program);
	}
	
	@PUT
	@Path("/{ProgramId}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Program updateProgram(@PathParam("ProgramId") long ProgramId, 
			Program Program) {
		return ProgramService.updateProgramInformation(ProgramId, Program);
	}
	
 }
