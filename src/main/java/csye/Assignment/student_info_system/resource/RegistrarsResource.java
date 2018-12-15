package csye.Assignment.student_info_system.resource;



import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import csye.Assignment.student_info_system.datamodel.Board;
import csye.Assignment.student_info_system.datamodel.Registrar;
import csye.Assignment.student_info_system.service.GenericServices;

@Path("registerOffering")
public class RegistrarsResource {
		


	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Registrar addRegistrar(Registrar registrar) {
		
		GenericServices service = GenericServices.getServiceInstance();
		service.addOrUpdateItem(registrar);
		Board board = new Board(registrar.getOfferingId());	//get the courseId
		service.addOrUpdateItem(board);
		return registrar;
	}
	

}
