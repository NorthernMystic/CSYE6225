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

import csye.Assignment.student_info_system.datamodel.Lecture;
import csye.Assignment.student_info_system.service.LecturesService;



// .. /webapi/Lectures
// webapi is defined in the src/main/webapp/web-inf/web.xml -> url pattern
@Path("lectures")
public class LecturesResource {

	LecturesService LectureService = new LecturesService();
	
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Lecture> getAllLecturesByProgram() {
		
		try {
			return LectureService.getAllLectures();
		} catch (Exception e) {
		    e.printStackTrace();
		}
		
		return null;
	}
	
	@GET
	@Path("/{LectureId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Lecture getLecture(@PathParam("LectureId") long LectureId) {
		return LectureService.getLecture(LectureId);
	}
	
	@DELETE
	@Path("/{LectureId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Lecture deleteLecture(@PathParam("LectureId") long LectureId) {
		return LectureService.deleteLecture(LectureId);
	}
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Lecture addLecture(Lecture Lecture) {
			return LectureService.addLecture(Lecture);
	}
	
	@PUT
	@Path("/{LectureId}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Lecture updateLecture(@PathParam("LectureId") long LectureId, 
			Lecture Lecture) {
		return LectureService.updateLectureInformation(LectureId, Lecture);
	}
	
 }
