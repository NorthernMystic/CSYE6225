package csye.Assignment.student_info_system.service;

import java.util.List;

import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.spec.UpdateItemSpec;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;
import com.amazonaws.services.dynamodbv2.model.ReturnValue;
import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sns.model.ListSubscriptionsByTopicRequest;
import com.amazonaws.services.sns.model.ListSubscriptionsByTopicResult;
import com.amazonaws.services.sns.model.SubscribeRequest;
import com.amazonaws.services.sns.model.Subscription;
import com.amazonaws.services.sns.model.UnsubscribeRequest;

import csye.Assignment.student_info_system.datamodel.Course;
import csye.Assignment.student_info_system.datamodel.DynamoDBConnector;
import csye.Assignment.student_info_system.datamodel.Student;

public class RegisterService{
	private static DynamoDB dynamoDB;
	//singleton
	private static RegisterService service = null;
	
	public RegisterService() {
		dynamoDB = new DynamoDB(DynamoDBConnector.getClient());
	}
	
	public static RegisterService getServiceInstance() {
		if (service == null) service = new RegisterService();
		return service;
	}

	public boolean register(String studentId, String courseId) {
		GenericServices service = GenericServices.getServiceInstance();
		
		Student student = service.getItem(Student.class, studentId, "StudentId");
		Course course = service.getItem(Course.class, courseId, "CourseId");
		if (student == null || student.getEnrolledClass().size() >= 3) return false;
		if (course == null) return false;
		List<String> newEnrolledCourses = student.getEnrolledClass();
		newEnrolledCourses.add(courseId);
		
		//update the student
		//also can .withValueMap(new ValueMap().withNumber(":r", 5.5).withString(":p", "Everything happens all at once.")
		Table tableS = dynamoDB.getTable("Students");
		//withPrimaryKey("StudentId", studentId) doesn't work
		UpdateItemSpec updateItemSpec = new UpdateItemSpec().withPrimaryKey("Id", student.getId())
	            .withUpdateExpression("set EnrolledClasses= :a")
	            .withValueMap(new ValueMap().withList(":a", newEnrolledCourses))
	            .withReturnValues(ReturnValue.UPDATED_NEW);
		tableS.updateItem(updateItemSpec);
		
		//UpdateItemOutcome outcome = tableS.updateItem(updateItemSpec);
		//System.out.println("UpdateItem succeeded:\n" + outcome.getItem().toJSONPretty());
		List<String> roster = course.getRoster();
		roster.add(studentId);
		
		Table tableC = dynamoDB.getTable("Courses");
		updateItemSpec = new UpdateItemSpec().withPrimaryKey("Id", course.getId())
	            .withUpdateExpression("set Roster= :a")
	            .withValueMap(new ValueMap().withList(":a", roster))
	            .withReturnValues(ReturnValue.UPDATED_NEW);
		tableC.updateItem(updateItemSpec);
		
		subscribeTopic(course.getTopic(), student.getEmail());
		return true;
	}
	
	
	public void subscribeTopic(String topicArn, String email) {
		AmazonSNSClient  snsClient = CourseService.getSNSClient();
		SubscribeRequest subscribeRequest = new SubscribeRequest(topicArn, "Email", email);
		snsClient.subscribe(subscribeRequest);
	}
	
	public void unSubscribeTopic(String topicArn, String email) {
		AmazonSNSClient  snsClient = CourseService.getSNSClient();
		ListSubscriptionsByTopicRequest requestList = new ListSubscriptionsByTopicRequest(topicArn);
		ListSubscriptionsByTopicResult resultList = snsClient.listSubscriptionsByTopic(requestList);
		
		List<Subscription> subscriptions = resultList.getSubscriptions();
		for(Subscription s : subscriptions) {
			if(s.getEndpoint().equals(email)) {
				UnsubscribeRequest unsubscribeRequest = new UnsubscribeRequest(s.getSubscriptionArn());
				snsClient.unsubscribe(unsubscribeRequest);
				//assume one email will registered only once in this topic
				break;
			}
		}
	}
}