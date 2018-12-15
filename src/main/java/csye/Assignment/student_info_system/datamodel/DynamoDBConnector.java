package csye.Assignment.student_info_system.datamodel;



import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;

import com.amazonaws.auth.InstanceProfileCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;


public class DynamoDBConnector{

	private static AmazonDynamoDB dynamoDB = null;
	
	//singleton
	private static DynamoDBConnector dynamoInstance = null;
	
	private static DynamoDBMapper mapper;
	
	public DynamoDBConnector() {
		try {
			
			//build the instance of client connect to dynamoDB 
			//seem after set up the IAM role, we need to use InstanceProfileCredentialsProvider to replace ProfileCredentialsProvider
			
			//get temporary credentials, follow the temple of professor
			
//			InstanceProfileCredentialsProvider instanceProfileCredentialsProvider = InstanceProfileCredentialsProvider.getInstance();
//			instanceProfileCredentialsProvider.getCredentials();
//			
//			dynamoDB = AmazonDynamoDBClientBuilder
//					.standard()
//					.withCredentials(instanceProfileCredentialsProvider)
//					.withRegion(Regions.US_WEST_2)
//					.build();
			
			//prevent leaking by set false
			dynamoDB = AmazonDynamoDBClientBuilder
					.standard()
					.withCredentials(new InstanceProfileCredentialsProvider(false))
					.withRegion(Regions.US_WEST_2)
					.build();
			
			mapper = new DynamoDBMapper(dynamoDB);

			
			
			//------------------------------------------
			//use for local test
//			String key = localTestKeyPassWord.ACCES_KEY_ID;
//			String password = localTestKeyPassWord.SECRET_ACCESS_KEY;
//			BasicAWSCredentials awsCreds = new BasicAWSCredentials(key
//					, password);
//			dynamoDB = AmazonDynamoDBClientBuilder
//					.standard()
//					.withCredentials(new AWSStaticCredentialsProvider(awsCreds))
//					.withRegion(Regions.US_WEST_2)
//					.build();
//			mapper = new DynamoDBMapper(dynamoDB);
			
		} catch(Exception e) {
			System.out.println("The DynamoDB connect client initialization failed");
			System.out.println(e.toString());
		}
		
	}
	
	public static DynamoDBConnector getInstance() {
		if(dynamoInstance == null)
			dynamoInstance = new DynamoDBConnector();
		return dynamoInstance;
	} 
	
	public static DynamoDBMapper getMapper() {
		if(dynamoInstance == null)
			dynamoInstance = new DynamoDBConnector();
		return mapper;
	}
	
	public static AmazonDynamoDB getClient() {
		if (dynamoDB == null) dynamoInstance = new DynamoDBConnector();
		return dynamoDB;
	}
}