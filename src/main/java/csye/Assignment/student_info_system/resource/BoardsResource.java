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

import csye.Assignment.student_info_system.datamodel.Board;
import csye.Assignment.student_info_system.service.GenericServices;

// .. /webapi/boards
// webapi is defined in the src/main/webapp/web-inf/web.xml -> url pattern
@Path("boards")
public class BoardsResource {
		
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Board> getAllboardsByProgram() {
		GenericServices service = GenericServices.getServiceInstance();
		
		return service.getAllItems(Board.class);
	}
	
	// ... webapi/board/1 
	@GET
	@Path("/{boardId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Board getboard(@PathParam("boardId") String boardId) {
		GenericServices service = GenericServices.getServiceInstance();
		
		return service.getItem(Board.class, boardId, "BoardId");
	}
	
	@DELETE
	@Path("/{boardId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Board deleteboard(@PathParam("boardId") String boardId) {
		GenericServices service = GenericServices.getServiceInstance();
		
		Board board = service.getItem(Board.class, boardId, "BoardId");
		if (board == null) return null;
		return service.deleteItem(board);
	}
	

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Board addboard(Board board) {
		GenericServices service = GenericServices.getServiceInstance();
		
		service.addOrUpdateItem(board);
		return board;
	}
	
	
	@PUT
	@Path("/{boardId}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Board updateboard(@PathParam("boardId") String boardId, 
			Board board) {
		GenericServices service = GenericServices.getServiceInstance();

		Board boardToRemove = service.getItem(Board.class, boardId, "boardId");
		service.deleteItem(boardToRemove);

		//if the boardId is not exist in the database, it will be created
		//if the boardId is already existed in the database, it will be overwrited

		service.addOrUpdateItem(board);
		return board;
	}
	
 }
