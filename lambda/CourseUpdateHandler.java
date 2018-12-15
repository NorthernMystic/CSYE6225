package com.amazonaws.lambda.demo;

import com.amazonaws.auth.InstanceProfileCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.DynamodbEvent;
import com.amazonaws.services.lambda.runtime.events.DynamodbEvent.DynamodbStreamRecord;
import com.amazonaws.services.stepfunctions.AWSStepFunctions;
import com.amazonaws.services.stepfunctions.AWSStepFunctionsClientBuilder;
import com.amazonaws.services.stepfunctions.model.StartExecutionRequest;


public class CourseUpdateHandler implements RequestHandler<DynamodbEvent, Integer> {
	
	//LOL the document has a typo, it write as use AwsClientBuilder.withCredentials(AWSCredentialsProvider) 
	//but it actually should be AWSStepFunctionsClientBuilder.withCredentials(AWSCredentialsProvider) 
	//spend half hour on it 
	private AWSStepFunctions client = AWSStepFunctionsClientBuilder
			.standard()
			.withRegion(Regions.US_WEST_2).build();
	
    @Override
    public Integer handleRequest(DynamodbEvent event, Context context) {
        context.getLogger().log("Received event: " + event);
        boolean isSuccess = false;
        
        
        for(DynamodbStreamRecord record : event.getRecords()) {
			if(record != null) {
				String curEvent = record.getEventName();
				//we only forward new announcement
				if(!curEvent.equals("INSERT"))
					continue;
				
				isSuccess = true;
				
				String courseId = record.getDynamodb().getNewImage().get("CourseId").getS();
                String department = record.getDynamodb().getNewImage().get("Department").getS();
                long size = record.getDynamodb().getNewImage().get("Roster").getL().size();
                String boardId = "";
                if (record.getDynamodb().getNewImage().get("BoardId") != null){
                	boardId = record.getDynamodb().getNewImage().get("BoardId").getS();
                }
                
                CourseUpdateEvent state = new CourseUpdateEvent(courseId, department,size, boardId);

                StartExecutionRequest request = new StartExecutionRequest();
                request.setInput(state.toString());
                request.setStateMachineArn("arn:aws:states:us-west-2:474694586562:stateMachine:Assignment4");
                client.startExecution(request);
				
			}
		}
        
        
        return isSuccess ? 1 : 0;
    }

}