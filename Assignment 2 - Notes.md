https://cloudacademy.com/blog/how-to-develop-lambda-applications-aws-toolkit-eclipse/
open help and then Install New Software

In next window, enter https://aws.amazon.com/eclipse in the text box labeled “Work with” at the top of the dialog. Press Add button and in the dialog box enter “AWS Toolkit” or any name that you are comfortable with but signifies it is the AWS Toolkit.

-----------------
add maven dependency

<>
​	<>
<>

to add the libary of aws sdk





Local dynamodb image

https://hub.docker.com/r/amazon/dynamodb-local/



Test with local Dynamodb:

https://github.com/aws-samples/aws-sam-java-rest/blob/master/src/main/java/com/amazonaws/dao/OrderDao.java

https://github.com/aws-samples/aws-sam-java-rest/blob/master/src/test/java/com/amazonaws/dao/OrderDaoTest.java



The method setEndpoint(String) from the type AmazonWebServiceClient is deprecated

even hard to find the code to connect to local dynamodb, LOL

After checked the document:

https://docs.aws.amazon.com/AWSJavaSDK/latest/javadoc/com/amazonaws/AmazonWebServiceClient.html#setEndpoint-java.lang.String-

Deprecated. use AwsClientBuilder.setEndpointConfiguration(AwsClientBuilder.EndpointConfiguration) for example: builder.setEndpointConfiguration(new EndpointConfiguration(endpoint, signingRegion));

This method is not threadsafe. Endpoints should be configured when the client is created and before any service requests are made. Changing it afterwards creates inevitable race conditions for any service requests in transit.

https://www.programcreek.com/java-api-examples/index.php?api=com.amazonaws.client.builder.AwsClientBuilder





create Table in local DynamoDB

https://stackoverflow.com/questions/34984880/dynamodb-create-tables-in-local-machine/43385883


@DynamoDBAttribute and @DynamoDBHashKey
and mapper.load(Student.class, studentId);
https://docs.aws.amazon.com/AWSJavaSDK/latest/javadoc/com/amazonaws/services/dynamodbv2/datamodeling/DynamoDBMapper.html

must above the get method
the get method must follow the pattern
otherwise the dynamodb can not identify the variable

OK
public String getImageURL() {
​		return imageURL;
}


No
public String getImageAddress() {
​		return imageURL;
}




operation on Dynamodb by java (Example to use DBMapper)

https://docs.aws.amazon.com/amazondynamodb/latest/developerguide/DynamoDBMapper.ArbitraryDataMapping.html




refactor the code, make all object extend the basic object to simplify the code


how to pass the class as the parameter
https://stackoverflow.com/questions/17849385/passing-class-name-as-parameter
The API document of DynamoMapper
https://docs.aws.amazon.com/AWSJavaSDK/latest/javadoc/com/amazonaws/services/dynamodbv2/datamodeling/DynamoDBMapper.html

base on this two:
I write the code:
public <T extends BasicDynamoObject> List<T> getAllItems(Class<T> objectClass){
​	return mapper.scan(objectClass, new DynamoDBScanExpression());
}
which works fine



The final version:

```
	public <T extends BasicDynamoObject> T deleteItem(Class<T> objectClass, long id) {
		T object = mapper.load(objectClass, id);
		mapper.delete(object, new DynamoDBDeleteExpression());
		return object;
	}
	
	public <T extends BasicDynamoObject> T getItem(Class<T> objectClass, long id) {
		return mapper.load(objectClass, id);
	}
	
	public <T extends BasicDynamoObject> List<T> getAllItems(Class<T> objectClass){
		return mapper.scan(objectClass, new DynamoDBScanExpression());
	}
	
	public <T extends BasicDynamoObject> T addOrUpdateItem(T object) {
		mapper.save(object);
		return object;
	}
```



auto generate Key
https://docs.aws.amazon.com/amazondynamodb/latest/developerguide/DynamoDBMapper.Annotations.html#DynamoDBMapper.Annotations.DynamoDBAutoGeneratedKey

IndexHashKey
GSI with attribute
https://www.programcreek.com/java-api-examples/index.php?api=com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBIndexHashKey

(ensure text size is no more than 160 characters)
seem to be the limit of Dynamodb but not the requirement





------------------------------

Test Process:

Nov 7

1.
The DynamoDBConnector and GenericService is static
can use mapper = DynamoDBConnector.getMapper(); directly

2.
http://localhost:8080/student-info-system/webapi/students
local host get:

threw exception [com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMappingException: could not instantiate class com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBGeneratedUuid$Generator] with root cause
com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMappingException: type [class java.util.UUID] is not supported; no conversion from class java.lang.Long

Only String properties can be marked as auto-generated keys.

***
change Id type from long to String


get: []
solved


3. then test with post
  http://localhost:8080/student-info-system/webapi/students

{
​	"firstName": "Lily",
​	"lastName": "Zhang",
​	"studentId": 1,
​	"joinDate": "2018-09",
​	"department": "CSYE",
​	"enrolledClass" : ["6225"]
}


Student[Id]; no HASH key value present


Try to solve:
when  create Dynamo Table
set up
-------------------------

Secondary indexes
Name                       Type    Partition key        Sort key   Projected Attributes
studentId-index     	   GSI    studentId (Number)      -			ALL

Not work:
Still Student[Id]; no HASH key value present


Fine...get the reason
I should not write like this...
Since if the Id has not generated by the DynamoDB, the student.getId will get null

if (service.getItem(Student.class, student.getId()) != null) {
​	System.out.println("the student with this id is exist");
​	return null;
}


4.
Student[objectId]; could not unconvert attribute

fine: remove objectID

seems the even we do not use attribute, the object of any get method will still be saved

thus, we can not use another method to return student/course/xxx id

may be we can just not check the validation emmmm


5.
https://docs.aws.amazon.com/amazondynamodb/latest/developerguide/DynamoDBMapper.Methods.html

with delete

first want try
​	public Student deleteStudent(Class objectClass, long studentId) {
​		Student student= (Student)mapper.load(objectClass, studentId);
​		mapper.delete(student, new DynamoDBDeleteExpression());
​		return student;
​	}


with error:
could not invoke public void csye.Assignment.student_info_system.datamodel.Student.setId(java.lang.String) on class csye.Assignment.student_info_system.datamodel.Student with value 1 of type class java.lang.Long] with root cause


en....
seem the IndexHashKey can only used to query??

try another way

	public Student deleteStudent(Class objectClass, long studentId) {
		
		DynamoDBQueryExpression<Student> queryExpression = new DynamoDBQueryExpression<Student>()
			    .withIndexName("StudentId")
			    .withKeyConditionExpression("StudentId = :" + studentId);
	
			List<Student> list =  mapper.query(objectClass, queryExpression);
			Student res = null;
			for (Student student: list) {
				res = student;
				mapper.delete(student, new DynamoDBDeleteExpression());
			}
			return res;
	}

Error:
​	Invalid KeyConditionExpression: An expression attribute value used in expression is not defined; attribute value: :1 (Service: AmazonDynamoDBv2; Status Code: 400; Error Code: ValidationException;

after check the document of AttributeValue
https://docs.aws.amazon.com/AWSJavaSDK/latest/javadoc/com/amazonaws/services/dynamodbv2/model/AttributeValue.html

change the method to

	public Student deleteStudent(Class objectClass, long studentId) {
		HashMap<String, AttributeValue> eav = new HashMap<String, AttributeValue>();
		eav.put(":v1",  new AttributeValue().withN(studentId + ""));
		
		DynamoDBQueryExpression<Student> queryExpression = new DynamoDBQueryExpression<Student>()
			    .withIndexName("StudentId")
			    .withKeyConditionExpression("StudentId = :v1")
				.withExpressionAttributeValues(eav);
	
			List<Student> list =  mapper.query(objectClass, queryExpression);
			Student res = null;
			for (Student student: list) {
				res = student;
				mapper.delete(student, new DynamoDBDeleteExpression());
			}
			return res;
	}


Error:
​	The table does not have the specified index: StudentId (Service: AmazonDynamoDBv2; Status Code: 400; Error Code: ValidationException; Request ID: UF5EJPH1Q4CQKLV76SQ8RP4HKBVV4KQNSO5AEMVJF66Q9ASUAAJG)

	after change the student class and genericService
	
	@DynamoDBIndexHashKey(attributeName="StudentId", globalSecondaryIndexName = "studentid-index")
	public long getStudentId() {
		return studentId;
	}
	
	public Student deleteStudent(Class objectClass, long studentId) {
		HashMap<String, AttributeValue> eav = new HashMap<String, AttributeValue>();
		eav.put(":v1",  new AttributeValue().withN(studentId + ""));
		
		DynamoDBQueryExpression<Student> queryExpression = new DynamoDBQueryExpression<Student>()
			    .withIndexName("studentid-index")
			    .withKeyConditionExpression("StudentId = :v1")
				.withExpressionAttributeValues(eav);
	
			List<Student> list =  mapper.query(objectClass, queryExpression);
			Student res = null;
			for (Student student: list) {
				res = student;
				mapper.delete(student, new DynamoDBDeleteExpression());
			}
			return res;
	}

Still Error:
The table does not have the specified index: studentid-index (Service: AmazonDynamoDBv2; Status Code: 400; Error Code: ValidationException;

//not sure why
but when I change to StudentID for both DynamoDB console and globalSecondaryIndexName in anotaion of student class
and add .withConsistentRead(false) in genericService, everything goes corret LoL



11.8
1.
Deal with range key

https://stackoverflow.com/questions/27329461/what-is-hash-and-range-primary-key

Example:
https://aws.amazon.com/blogs/developer/the-dynamodbmapper-local-secondary-indexes-and-you/


DynamoDBIndexRangeKey marks the property as an alternate range key to be used in a local secondary index. 


That's why the requirement is:
announcement/boardID_announcementID


2.
Fisrt test how to get boardID and announcementID seperately
change the restful Api to 

	@GET
	@Path("/{boardId}_{announcementId}")
	@Produces(MediaType.APPLICATION_JSON)
	public String getAnnouncement(@PathParam("boardId") String boardId, @PathParam("announcementId") String announcementId) {
		return new String(boardId + " " + announcementId);
	}

and test with postman with url
http://localhost:8080/student-info-system/webapi/announcements/1_1

Get the right response
1 1


3.
Then write a new service for announcement

https://aws.amazon.com/blogs/developer/the-dynamodbmapper-local-secondary-indexes-and-you/

still follow
https://docs.aws.amazon.com/amazondynamodb/latest/developerguide/DynamoDBMapper.Methods.html


then get:

	@DynamoDBIndexRangeKey(attributeName="AnnouncementId", globalSecondaryIndexName = "Board-Announcement-Index")
	public String getAnnouncementId() {
		return announcementId;
	}


	@DynamoDBIndexHashKey(attributeName="BoardId", globalSecondaryIndexName = "Board-Announcement-Index")
	public String getBoardId() {
		return boardId;
	}



	public Announcement getAnnouncement(String boardId, String announcementId) {
		HashMap<String, AttributeValue> eav = new HashMap<String, AttributeValue>();
		eav.put(":v1",  new AttributeValue().withS(boardId));
		eav.put(":v2",  new AttributeValue().withS(announcementId));
	
		DynamoDBQueryExpression<Announcement> queryExpression = new DynamoDBQueryExpression<Announcement>()
		    .withIndexName("Board-Announcement-Index")
		    .withConsistentRead(false)
		    .withKeyConditionExpression("BoardId = :v1 and begins_with(AnnouncementId, :v2)")
		    .withExpressionAttributeValues(eav);
	
		List<Announcement> iList =  mapper.query(Announcement.class, queryExpression);
		if (iList.size() == 0) return null;
		return iList.get(0);
	}


When test with LifanStudentInformationSystem-env.jpihynhysy.us-west-2.elasticbeanstalk.com/webapi/students

com.amazonaws.services.dynamodbv2.model.AmazonDynamoDBException: User: arn:aws:sts::*******:assumed-role&#47;aws-elasticbeanstalk-ec2-role&#47;******* is not authorized to perform: dynamodb:Scan on resource: arn:aws:dynamodb:us-west-2:******:table&#47;Students (Service: AmazonDynamoDBv2; Status Code: 400; Error Code: AccessDeniedException)


try:

InstanceProfileCredentialsProvider instanceProfileCredentialsProvider = InstanceProfileCredentialsProvider.getInstance();
​			instanceProfileCredentialsProvider.getCredentials();
​			
​			
			dynamoDB = AmazonDynamoDBClientBuilder
					.standard()
					.withCredentials(instanceProfileCredentialsProvider)
					.withRegion(Regions.US_WEST_2)
					.build();

not work



Finally get it:
We need also add the permission for role of EC2 instance where our elasticbeanstalk deployed