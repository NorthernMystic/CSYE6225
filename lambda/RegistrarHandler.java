package com.amazonaws.lambda.demo;


import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONObject;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class RegistrarHandler implements RequestHandler<CourseUpdateEvent, Integer> {
	
    @Override
    public Integer handleRequest(CourseUpdateEvent event, Context context) {
        context.getLogger().log("Received event: " + event);
        
        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost request = new HttpPost("http://LifanStudentInformationSystem-env.jpihynhysy.us-west-2.elasticbeanstalk.com/webapi/registerOffering");
        HttpResponse response = null;
        JSONObject json = new JSONObject();
        json.put("offeringId", event.getCourseId());
        json.put("offeringType", "Course");
        json.put("department", event.getDepartment());

        try {
            StringEntity params = new StringEntity(json.toString());
            request.addHeader("content-type", "application/json");
            request.setEntity(params);
            response = httpClient.execute(request);
        }catch (Exception ex) {
            ex.printStackTrace();
        }
        
        return 1;
    }

}

