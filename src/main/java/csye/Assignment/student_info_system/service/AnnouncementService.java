package csye.Assignment.student_info_system.service;

import java.util.HashMap;
import java.util.List;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;

import csye.Assignment.student_info_system.datamodel.Announcement;
import csye.Assignment.student_info_system.datamodel.DynamoDBConnector;

public class AnnouncementService{
	
	private static DynamoDBMapper mapper;
	
	//singleton
	private static AnnouncementService service = null;
	
	public AnnouncementService() {
		mapper = DynamoDBConnector.getMapper(); //the static variable should be accessed by a static way
	}
	
	public static AnnouncementService getServiceInstance() {
		if (service == null) service = new AnnouncementService();
		return service;
	}
	
	public Announcement getAnnouncement(String boardId, String announcementId) {
		HashMap<String, AttributeValue> eav = new HashMap<String, AttributeValue>();
		eav.put(":v1",  new AttributeValue().withS(boardId));
		eav.put(":v2",  new AttributeValue().withS(announcementId));

		DynamoDBQueryExpression<Announcement> queryExpression = new DynamoDBQueryExpression<Announcement>()
		    .withIndexName("BoardId-AnnouncementId-Index")
		    .withConsistentRead(false)
		    .withKeyConditionExpression("BoardId = :v1 and begins_with(AnnouncementId, :v2)")
		    .withExpressionAttributeValues(eav);

		List<Announcement> iList =  mapper.query(Announcement.class, queryExpression);
		if (iList.size() == 0) return null;
		return iList.get(0);
	}
}