package csye.Assignment.student_info_system.service;

//import com.amazonaws.auth.AWSStaticCredentialsProvider;
//import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.InstanceProfileCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;
import com.amazonaws.services.sns.model.CreateTopicRequest;
import com.amazonaws.services.sns.model.DeleteTopicRequest;
import com.amazonaws.services.sns.model.CreateTopicResult;

//import csye.Assignment.student_info_system.datamodel.localTestKeyPassWord;

public class CourseService{
	private static CourseService service = null;
	private static AmazonSNSClientBuilder snsClientBuilder;
	private static AmazonSNSClient  snsClient;
	
	public CourseService () {
		snsClientBuilder = AmazonSNSClientBuilder
				.standard()
				.withCredentials(new InstanceProfileCredentialsProvider(false))
				.withRegion(Regions.US_WEST_2);
		
		snsClient = (AmazonSNSClient) snsClientBuilder.build();
		
		
		
		//------------------------------------------
		//use for local test
//		String key = localTestKeyPassWord.ACCES_KEY_ID;
//		String password = localTestKeyPassWord.SECRET_ACCESS_KEY;
//		BasicAWSCredentials awsCreds = new BasicAWSCredentials(key
//				, password);
//		snsClientBuilder = AmazonSNSClientBuilder
//				.standard()
//				.withCredentials(new AWSStaticCredentialsProvider(awsCreds))
//				.withRegion(Regions.US_WEST_2);
//		
//		snsClient = (AmazonSNSClient) snsClientBuilder.build();
		
	}
	
	public static CourseService getServiceInstance() {
		if (service == null) service = new CourseService();
		return service;
	}
	
	public static AmazonSNSClient getSNSClient() {
		if (snsClient == null) service = new CourseService();
		return snsClient;
	}
	
	public String createTopicArn(String courseId) {
		CreateTopicRequest createTopicRequest = new CreateTopicRequest(courseId);
        CreateTopicResult createTopicResult = snsClient.createTopic(createTopicRequest);
        
        String topicArn = createTopicResult.getTopicArn();
        return topicArn;
	}
	
	public void removeTopicArn(String topicArn) {
		DeleteTopicRequest deleteTopicRequest = new DeleteTopicRequest(topicArn);
		snsClient.deleteTopic(deleteTopicRequest);
	}
}