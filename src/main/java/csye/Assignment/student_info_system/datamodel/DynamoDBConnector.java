package csye.Assignment.student_info_system.datamodel;



import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;


public class DynamoDBConnector{

	
	private static AmazonDynamoDB dynamoDB;
	
	//singleton
	private static DynamoDBConnector dynamoInstance = null;
	
	private static DynamoDBMapper mapper;
	
	public DynamoDBConnector() {
		try {
			BasicAWSCredentials awsCreds = new BasicAWSCredentials("AKIAJ6KKDJLJBMKE6C7Q"
					, "AMihmpXf4yEn3KX92zY2hizIgpX3txy363qjIi0/");
			
			//build the instance of client connect to dynamoDB 
			dynamoDB = AmazonDynamoDBClientBuilder
					.standard()
					.withCredentials(new AWSStaticCredentialsProvider(awsCreds))
					.withRegion(Regions.US_WEST_2)
					.build();
			
			mapper = new DynamoDBMapper(dynamoDB);
			
			//test with local dynamodb
//			dynamoDB = AmazonDynamoDBClientBuilder.standard().withEndpointConfiguration(
//		            // test with localhost
//		            new AwsClientBuilder.EndpointConfiguration("http://localhost:8000", "us-west-2"))
//		            .build();
			
			
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
	
	
}