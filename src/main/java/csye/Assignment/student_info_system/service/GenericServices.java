package csye.Assignment.student_info_system.service;

import java.util.List;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDeleteExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;

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
	
}