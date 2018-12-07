package com.amazonaws.lambda.demo;

import java.util.Map;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.DynamodbEvent;
import com.amazonaws.services.lambda.runtime.events.DynamodbEvent.DynamodbStreamRecord;
import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;
import com.amazonaws.services.sns.model.PublishRequest;

public class LambdaFunctionHandler implements RequestHandler<DynamodbEvent, Integer> {

	private AmazonSNSClientBuilder snsClientBuilder = AmazonSNSClientBuilder
			.standard()
			.withRegion(Regions.US_WEST_2);
	
	private AmazonSNSClient snsClient = (AmazonSNSClient) snsClientBuilder.build();
	
    @Override
    public Integer handleRequest(DynamodbEvent event, Context context) {
        context.getLogger().log("Received event: " + event);
        boolean isSent = false;
        
		for(DynamodbStreamRecord record : event.getRecords()) {
			if(record != null) {
				String curEvent = record.getEventName();
				//we only forward new announcement
				if(!curEvent.equals("INSERT"))
					continue;
				
				isSent = true;
				
				Map<String, AttributeValue> map = record.getDynamodb().getNewImage();
				//can not pass test
				//String topicArn = "arn:aws:sns:us-west-2:474694586562:" + map.get("CourseId").getS();
				String topicArn = "arn:aws:sns:us-west-2:474694586562:";
				String subject = "New Announcement";
				StringBuilder message = new StringBuilder();
				message.append("There is a new announcement:\n");
				
				if (map != null && map.get("AnnouncementText")!= null) {
					message.append(map.get("AnnouncementText").getS());
				}
				
				if (map != null && map.get("CourseId")!= null) {
					topicArn = new String(topicArn + map.get("CourseId").getS());
					sendNotification(topicArn, subject, message.toString());
				}
				
			}
		}
		
		return isSent ? 1 : 0;
    }
    
    private void sendNotification(String topicArn, String subject, String message) {
		PublishRequest pRequest = new PublishRequest(topicArn, message, subject);
		snsClient.publish(pRequest);
    }
}