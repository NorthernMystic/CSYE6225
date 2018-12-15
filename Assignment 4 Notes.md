### Diffuclt part in this assignment



If the course is not new, the workflow stops.

If the course is new, the workflow continus.



How to do it?

https://console.aws.amazon.com/states/home?region=us-east-1#/statemachines/create?mode=blueprints



Follow the Choice state template



After test, we know 

1. end : true can not in Choice state
2. resource can not in Choice state, it can exit at Task state

Since the choice example is 

```
"ChoiceState": {
      "Type" : "Choice",
      "Choices": [
        {
          "Variable": "$.foo",
          "NumericEquals": 1,
          "Next": "FirstMatchState"
        },
        {
          "Variable": "$.foo",
          "NumericEquals": 2,
          "Next": "SecondMatchState"
        }
      ],
      "Default": "DefaultState"
    }
```

base on https://docs.aws.amazon.com/step-functions/latest/dg/amazon-states-language-input-output-processing.html

and the video: https://www.youtube.com/watch?v=8rmgF-SbcIk



we know the $.foo is the foo variable in the state machine 

more explaination:

https://stackoverflow.com/questions/44956869/how-to-specify-multiple-result-path-values-in-aws-step-functions

also we can add variable as json into the state machine during the workflow

Thus, I guess I can use the structure like 

```
  "StartAt": "CheckNewCourse",
  "States": {
    "CourseUpdateTrigger": {
      "Type" : "Task",
      "Resource": "...",
      "Next": "CheckNewCourse"
    },
    "CheckNewCourse": {
      "Type": "Choice",
      "Choices": [
        {
          "Variable": "$.isNew",
          "NumericEquals": 1,
          "Next": "CheckSeminar"
        },
        {
          "Variable": "$.isNew",
          "NumericEquals": 0,
          "Next": "NoMoreUpdateState"
        }
      ]
    },
```



Base on the AWS document: https://docs.aws.amazon.com/step-functions/latest/dg/tutorial-creating-lambda-state-machine.html

https://www.youtube.com/watch?v=VXa2eVatW54



I guess the CourseTrigger is the the start of the step function but not the first step of the step function. Which means we wouldn't write this lambda function in the Step function console.



#### Then the first problem is how to call step function by lambda function

Find the AWSStepFunctionClient, check the document.

https://stackoverflow.com/questions/49043978/invoking-aws-step-function-from-java-lambda

!!!must import aws-java-sdk-stepfunctions

Seems a little difficult, we can skip the first step. Using the test execution in step function to simulate the result of first step. 



#### Then the second problem is how to check multiple conditions in the step function

Follow https://docs.aws.amazon.com/step-functions/latest/dg/amazon-states-language-choice-state.html

We can use "And" here

Follow the https://docs.aws.amazon.com/step-functions/latest/dg/amazon-states-language-choice-state.html?shortFooter=true

We know that we can not detect if the list is empty directly with the choice condition

https://github.com/awslabs/statelint/issues/7

Also see on this github Issue.

We may not even able to test if it is null directly in the step function even JSON can save null as a value



So, When we can write the value (e.g isBoardIdEmpty an the Start)

Or we can also set the default valueo f boardId as empty string like "" and check that

For the empty roster test, I add another variable size to simplify the process. (may be can use loop something)





The test step function

```
{
  "StartAt": "CheckNewCourse",
  "States": {
    "CheckNewCourse": {
      "Type": "Choice",
      "Choices": [
        {
          "And": [
            {
              "Variable": "$.boardId",
              "StringEquals": ""
            },
            {
              "Variable": "$.topic",
              "StringEquals": ""
            },
            {
              "Variable": "$.size",
              "NumericEquals": 0
            }
          ],
          "Next": "CheckSeminar"
        }
      ],
      "Default": "NoMoreUpdateState"
    },
    "CheckSeminar": {
      "Type": "Choice",
      "Choices": [
        {
          "Not": {
            "Variable": "$.department",
            "StringEquals": "Seminar"
          },
          "Next": "RegisterAndBoard"
        }
      ],
      "Default": "NoMoreUpdateState"
    },
    "RegisterAndBoard": {
      "Type" : "Succeed"
    },
    "NoMoreUpdateState": {
      "Type": "Fail",
      "Error": "DefaultStateError",
      "Cause": "No Matches!"
    }
  }
}
```

The test JSON

```
{
    "boardId": "",
  	"topic": "",
  	"size": 0,
  	"department": "Seminar"
}

and

{
    "boardId": "",
  	"topic": "",
  	"size": 0,
  	"department": "CSYE"
}
```

The step code works.





Then I first finish the step 4 with another seperate service (read the dynamodb by server not by lambda )

Test with localhost: OK. Seems work well.

Now we first deploy the service to the elasticBeanstalk

Then we try to write the httprequest and test locally

follow the link:

https://stackoverflow.com/questions/3324717/sending-http-post-request-in-java



One error occurs:

Target host is not specified

The reason is :

https://stackoverflow.com/questions/24985771/org-apache-http-protocolexception-target-host-is-not-specified



Interesting, the error message is kind of misleading. 

Just need to complete the url, do not forget the "http://"



But the post request seems doesn't work since the DynamoDB do not have new record.

Thus, we want to get the content of the response.

https://stackoverflow.com/questions/309424/how-to-read-convert-an-inputstream-into-a-string-in-java

From the response., we see "HTTP Status 415 – Unsupported Media Type"

After search the internet, It seems we need to send the JSON

```
List<NameValuePair> params = new ArrayList<NameValuePair>(3);
        params.add(new BasicNameValuePair("offeringId", "6225"));
        params.add(new BasicNameValuePair("offeringType", "Course"));
        params.add(new BasicNameValuePair("department", "CSYE"));
```

Send the parameter seems not works.

So, follow https://stackoverflow.com/questions/7181534/http-post-using-json-in-java

we rewrite the test case.

```
try {
	StringEntity params =new StringEntity("details={\"offeringId\":\"6225\",\"offeringType\":\"Course\", \"department\":\"CSYE\"}");
	request.addHeader("content-type", "application/x-www-form-urlencoded");
	request.setEntity(params);
	response = httpClient.execute(request);
}catch (Exception ex) {
	ex.printStackTrace();
}
```





Still "HTTP Status 415 – Unsupported Media Type"

The origin server is refusing to service the request because the payload is in a format not supported by this method on the target resource



Try to build JSON Object directly

Seems works.

```
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

public class HttpRequestTest {

    public static void main(String[] args) throws IOException {
        //HttpClient httpclient = HttpClients.createDefault();
        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost request = new HttpPost("http://LifanStudentInformationSystem-env.jpihynhysy.us-west-2.elasticbeanstalk.com/webapi/registerOffering");
        HttpResponse response = null;
        JSONObject json = new JSONObject();
        json.put("offeringId", "6225");
        json.put("offeringType", "Course");
        json.put("department", "CSYE");

        try {
            StringEntity params = new StringEntity(json.toString());
            request.addHeader("content-type", "application/json");
            request.setEntity(params);
            response = httpClient.execute(request);
        }catch (Exception ex) {
            ex.printStackTrace();
        }

        if (response != null){
            HttpEntity entity = response.getEntity();

            if (entity != null) {
                try (InputStream inputStream = entity.getContent()) {
                    Scanner s = new Scanner(inputStream).useDelimiter("\\A");
                    String result = s.hasNext() ? s.next() : "";
                    System.out.println(result);
                }
            }
        }

    }
}

```



So after that, we will back to the what to send to the step function 

base on : https://docs.aws.amazon.com/lambda/latest/dg/java-handler-io-type-pojo.html

```
public static ResponseClass handleRequest(RequestClass request, Context context){
        String greetingString = String.format("Hello %s, %s.", request.firstName, request.lastName);
        return new ResponseClass(greetingString);
}
```

We can create a state like responseClass (CourseUpdateEvent) something like that



After finish the code, we deploy the lambda function to the cloud and test with customized test case.

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
          "CourseId": {
            "S": "6225"
          },
          "Department": {
            "S": "CSYE"
          },
          "BoardId": {
            "S": ""
          },
          "Roster": {
            "L": []
          }
        },
        "StreamViewType": "NEW_AND_OLD_IMAGES",
        "SequenceNumber": "111",
        "SizeBytes": 26
      },
      "awsRegion": "us-west-2",
      "eventName": "INSERT",
      "eventSourceARN": "arn:aws:dynamodb:us-west-2:account-id:table/ExampleTableWithStream/stream/2015-06-27T00:48:05.899",
      "eventSource": "aws:dynamodb"
    }
  ]
}
```



Error: Unable to load credentials from service endpoint: com.amazonaws.SdkClientException com.amazonaws.SdkClientException: Unable to load credentials from service endpoint at com.amazonaws.auth.EC2CredentialsFetcher.handleError



Seems I forget to add the the permission to access to the elasticbeanstalk for the step function role?

And the full access and test again.

Error still exist. Check the log carefully. 

at com.amazonaws.lambda.demo.CourseUpdateHandler.handleRequest(CourseUpdateHandler.java:52)

at com.amazonaws.services.stepfunctions.AWSStepFunctionsClient.startExecution(AWSStepFunctionsClient.java:1414)

at com.amazonaws.http.AmazonHttpClient$RequestExecutor.access$500(AmazonHttpClient.java:674)



Seems the lambda can not access the step function?

Doesn't work even we add full access to EC2 instance for lambda role.



Try add full access to EC2 instance and lambda function for step function role.

Still error???

Try to remove:

```
withCredentials(**new** InstanceProfileCredentialsProvider(**false**))
```

Ok. It can move to next step. Do not know why, but works.



Invalid State Machine Execution Input: 'com.fasterxml.jackson.core.JsonParseException: Unexpected character ('"' (code 34)): was expecting comma to separate Object entries
at [Source: (String)"{"courseId": "6225", "department": "CSYE", "boardId": """, "size": "0"}"; line: 1, column: 58]'



Got it: the toString method is wrong since the size is a number, should not have "" around the value. Also another typo



Lambda execute Step fuction successfully. Fail at step RegisterAndBoard

```
{
  "error": "Lambda.AWSLambdaException",
  "cause": "User: arn:aws:sts::474694586562:assumed-role/Assignment4StepFunctionRole/KPooZZhQYIgugPwGDWetiaWQGcdMkCoA is not authorized to perform: lambda:InvokeFunction on resource: arn:aws:lambda:us-west-2:474694586562:function:Registrar with an explicit deny (Service: AWSLambda; Status Code: 403; Error Code: AccessDeniedException; Request ID: 5d8d8631-7dba-4f7e-843e-9f8826ff9040)"
}
```

https://forums.aws.amazon.com/thread.jspa?messageID=748566

Lambda permission model is composed of 2 roles: an invoke permission and an execution role. 

But I have already give it a full access permission.

Not sure why it still not work.



The intersting thing is, if we simulate the execution from the Step function console, it works.

But if we invoke the lambda function in step function by another lambda function, the access will be denied.

What the hell???

En???? I didn't change anything? But is works???

Emmmm, I guess the update of the IAM role will need sometimes (maybe several minutes)