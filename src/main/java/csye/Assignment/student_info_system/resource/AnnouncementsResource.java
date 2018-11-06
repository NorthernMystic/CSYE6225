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
	
	// ... webapi/Announcement/1 
	@GET
	@Path("/{announcementId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Announcement getAnnouncement(@PathParam("announcementId") long announcementId) {
		GenericServices service = GenericServices.getServiceInstance();
		
		return service.getItem(Announcement.class, announcementId);
	}
	
	@DELETE
	@Path("/{announcementId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Announcement deleteAnnouncement(@PathParam("AnnouncementId") long announcementId) {
		GenericServices service = GenericServices.getServiceInstance();
		
		return service.deleteItem(Announcement.class, announcementId);
	}
	

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Announcement addAnnouncement(Announcement announcement) {
		GenericServices service = GenericServices.getServiceInstance();
		if (service.getItem(Announcement.class, announcement.getId()) != null) {
			System.out.println("the announcement with this id is exist");
			return null;
		}
		
		service.addOrUpdateItem(announcement);
		return announcement;
	}
	
	
	
	@PUT
	@Path("/{announcementId}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Announcement updateAnnouncement(@PathParam("AnnouncementId") long announcementId, 
			Announcement announcement) {
		GenericServices service = GenericServices.getServiceInstance();
		if (announcementId != announcement.getId()) {
			System.out.println("the announcement Id you input is different from the id in your Announcement object");
			return null;
		}
		
		//if the AnnouncementId is not exist in the database, it will be created
		//if the AnnouncementId is already existed in the database, it will be overwrited
		service.addOrUpdateItem(announcement);
		return announcement;
	}
	
 }
