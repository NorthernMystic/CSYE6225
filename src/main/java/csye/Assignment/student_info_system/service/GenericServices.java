package csye.Assignment.student_info_system.service;

import java.util.HashMap;
import java.util.List;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDeleteExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;

import csye.Assignment.student_info_system.datamodel.BasicDynamoObject;
import csye.Assignment.student_info_system.datamodel.DynamoDBConnector;

public class GenericServices{
	private static DynamoDBMapper mapper;
	
	
	//singleton
	private static GenericServices service = null;
	
	public GenericServices() {
		mapper = DynamoDBConnector.getMapper(); //the static variable should be accessed by a static way
	}
	
	public static GenericServices getServiceInstance() {
		if (service == null) service = new GenericServices();
		return service;
	}
	
	public <T extends BasicDynamoObject> T deleteItem(T object) {
		mapper.delete(object, new DynamoDBDeleteExpression());
		return object;
	}
	
	public <T extends BasicDynamoObject> T getItem(Class<T> objectClass, String objectId, String indexName) {
		HashMap<String, AttributeValue> eav = new HashMap<String, AttributeValue>();
		eav.put(":v1",  new AttributeValue().withS(objectId));
		
		DynamoDBQueryExpression<T> queryExpression = new DynamoDBQueryExpression<T>()
			    .withIndexName(indexName)
			    .withKeyConditionExpression(indexName + " = :v1")
			    .withConsistentRead(false)
				.withExpressionAttributeValues(eav);

		List<T> list =  mapper.query(objectClass, queryExpression);
		if (list.size() == 0) return null;
		return list.get(0);
	}
	
	public <T extends BasicDynamoObject> List<T> getAllItems(Class<T> objectClass){
		return mapper.scan(objectClass, new DynamoDBScanExpression());
	}
	
	public <T extends BasicDynamoObject> T addOrUpdateItem(T object) {
		mapper.save(object);
		return object;
	}
	
}