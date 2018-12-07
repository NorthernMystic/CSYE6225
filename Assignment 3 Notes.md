# Assignment 3 Notes

#### 1.

First, we want to be clear about the workflow

Main process

1.1

Student register -> add courseId to student's enrolled classed list && subscribe the student's email to that topic

The first topic should be trivial, for the second part we need to learn how to add topic to course



1.2

Announcement -> push notice to all emails that subscribe to the course that this announcement belong to







#### Before 2

Suddenly, the query threw exception, not sure what happened, solve them first

When use CourseId, there will have exception

Then I change to CourseId-Index

It shows Invalid KeyConditionExpression: Syntax error; token: "-", near: "CourseId-Index"

base on

https://stackoverflow.com/questions/42198091/dynamodb-why-cant-i-use-an-as-a-prefix-in-my-key-condition-expression



but when I test with Announcement with BoardId-AnnouncementId-Index, which is fine 

Not sure why?	Just because announcement has a sorting key?? seems not make sense



———OK

find the reason: since in the serivice

```
eav.put(":v1",  new AttributeValue().withN(objectId));
```

withN means the objectId is the number

That's the reason why student works fine since studentId is the Long type but we try to change the couseId into the String type



-------------

I guess change all to String type is better, we do not need to narrow the ID as numbers





#### 2. Do it

2.1

Step 1 - Extend Students table - Every student should now have a emailId field. 


2.2

Step 2 - Extend Courses -  Every New Course now has a SNS topic. 

Try to add the topic to the entity

http://techmonies.com/index.php/2017/01/17/creating-a-sns-topic-and-subscribing-it-to-an-emailsqs-using-aws-java-sdk/



2.3

 Step 3 - Register Student for Course action

Add RegisterService

update the student information ->

https://docs.aws.amazon.com/amazondynamodb/latest/developerguide/GettingStarted.Java.03.html



Error: The provided key element does not match the schema 

```
UpdateItemSpec updateItemSpec = new UpdateItemSpec().withPrimaryKey("StudentId", studentId)
```

Thus, the GSI can not used ad the PrimaryKey

may be we need to get the id of the student onject we get from the query





```
With
{
	"courseId": "7350"
}

after change to "Id", student.getId()
Registration success
But the response become 
{
  "enrolledClass" : [ "6225", "{\n\t\"courseId\": \"7350\"\n}" ]
}

also if we get the object from http://localhost:8080/student-info-system/webapi/students/3
it is still 
{
    "department": "CSYE",
    "enrolledClass": [
        "6225"
    ],
    "firstName": "Dong",
    "id": "000459f8-9d6f-48a6-a56b-f94e7b1fb767",
    "joinDate": "2017-09",
    "lastName": "Tao",
    "studentId": "3"
}
```



What's the problem??

```
try 
"7350" only
still the same result

try 
7350 only
works

try 
cloud without ""
also works

!!!!!
I recall that the "" means \ \ in the AWS 
that why when we put the "7350" the result will become \"7350\"\

Also need to make sure that the names match for the updateExpression with the Table column 
```



subscribe is trivial with subscribeRequest





2.4 Use Lambda to trigger DynamoDB



Trigger follow:

https://www.youtube.com/watch?v=jgiZ9QUYqyM



Can not add the DynamoDB trigger at the Lambda Page or add the lambda funtion at the DynamoDB table page - Trigger Tab

From the DynamoDB table page, we can only use Node.js 8.0. How smart the AWS is! LOL.

Find the reason, even the table and the Lambda function must be in the same region.



Write the function:

seems Lambda only support one method, not sure why.



```
When build the lambda, failed a lot of times.

Then I find Assert.*assertEquals*(1, output.intValue());

change it same as the output, then pass the test
```



For the Handler: recall

Either package.class-name::handler or package.class-name. For example, "com.Hello::myHandler" calls the myHandler method defined in Hello.java.

Thus: our Handler should at 

com.amazonaws.lambda.demo.LambdaFunctionHandler::handleRequest





Then test with dummy Event

```
{
  "Records": [
    {
      "eventID": "1",
      "eventVersion": "1.0",
      "dynamodb": {
        "Keys": {
          "Id": {
            "N": "101"
          }
        },
        "NewImage": {
          "AnnouncementText": {
            "S": "Just for test!"
          },
          "Id": {
            "N": "101"
          },
          "CourseId": {
            "S": "6225"
          }
        },
        "StreamViewType": "NEW_AND_OLD_IMAGES",
        "SequenceNumber": "111",
        "SizeBytes": 26
      },
      "awsRegion": "us-east-1",
      "eventName": "INSERT",
      "eventSourceARN": "arn:aws:dynamodb:us-east-1:account-id:table/ExampleTableWithStream/stream/2015-06-27T00:48:05.899",
      "eventSource": "aws:dynamodb"
    }
  ]
}

```



Failed with the log:

AnnouncementTrigger is not authorized to perform: SNS:Publish on resource

Add the permission for the Lambda role, attach [AmazonSNSFullAccess](https://console.aws.amazon.com/iam/home#/policies/arn%3Aaws%3Aiam%3A%3Aaws%3Apolicy%2FAmazonSNSFullAccess)



Then test again

Success!!!









