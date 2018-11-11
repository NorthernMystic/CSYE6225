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

import csye.Assignment.student_info_system.datamodel.Announcement;
import csye.Assignment.student_info_system.service.AnnouncementService;
import csye.Assignment.student_info_system.service.GenericServices;


// .. /webapi/announcements
// webapi is defined in the src/main/webapp/web-inf/web.xml -> url pattern
@Path("announcements")
public class AnnouncementsResource {
		
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Announcement> getAllAnnouncementsByProgram() {
		GenericServices service = GenericServices.getServiceInstance();
		
		return service.getAllItems(Announcement.class);
	}
	
	// ... webapi/Announcement/1_1	
	@GET
	@Path("/{boardId}_{announcementId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Announcement getAnnouncement(@PathParam("boardId") String boardId, @PathParam("announcementId") String announcementId) {
		AnnouncementService Aservice = AnnouncementService.getServiceInstance();
		
		return Aservice.getAnnouncement(boardId, announcementId);
	}
	
	@DELETE
	@Path("/{boardId}_{announcementId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Announcement deleteAnnouncement(@PathParam("boardId") String boardId, @PathParam("announcementId") String announcementId) {
		GenericServices service = GenericServices.getServiceInstance();
		AnnouncementService Aservice = AnnouncementService.getServiceInstance();
		
		Announcement announcement = Aservice.getAnnouncement(boardId, announcementId);
		if (announcement == null) return null;
		return service.deleteItem(announcement);
	}
	

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Announcement addAnnouncement(Announcement announcement) {
		GenericServices service = GenericServices.getServiceInstance();
		
		service.addOrUpdateItem(announcement);
		return announcement;
	}
	
	
	
	@PUT
	@Path("/{boardId}_{announcementId}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Announcement updateAnnouncement(@PathParam("boardId") String boardId, @PathParam("announcementId") String announcementId, 
			Announcement announcement) {
		GenericServices service = GenericServices.getServiceInstance();
		AnnouncementService Aservice = AnnouncementService.getServiceInstance();
		
		Announcement announcementToRemove = Aservice.getAnnouncement(boardId, announcementId);
		service.deleteItem(announcementToRemove);
		service.addOrUpdateItem(announcement);
		return announcement;
	}
	
 }
