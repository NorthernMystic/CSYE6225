package csye.Assignment.student_info_system.datamodel;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAutoGeneratedKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

@DynamoDBTable(tableName = "Registrars")
public class Registrar extends BasicDynamoObject {
	private String id;	//RegistrationId;
	private String offeringId;
	private String offeringType;
	private String department;
	
	private String perUnitPrice;

	public Registrar() {
		
	}

	public Registrar(String offeringId, String offeringType, String department) {
		this.offeringId = offeringId;
		this.offeringType = offeringType;
		this.department = department;
		this.perUnitPrice = "1514";
	}

	@DynamoDBHashKey(attributeName = "Id")
	@DynamoDBAutoGeneratedKey
	public String getId() {
		return id;
	}

	@DynamoDBAttribute(attributeName="OfferingId")
	public String getOfferingId() {
		return offeringId;
	}

	@DynamoDBAttribute(attributeName="OfferingType")
	public String getOfferingType() {
		return offeringType;
	}

	@DynamoDBAttribute(attributeName="Department")
	public String getDepartment() {
		return department;
	}

	@DynamoDBAttribute(attributeName="PerUnitPrice")
	public String getPerUnitPrice() {
		return perUnitPrice;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setOfferingId(String offeringId) {
		this.offeringId = offeringId;
	}

	public void setOfferingType(String offeringType) {
		this.offeringType = offeringType;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public void setPerUnitPrice(String perUnitPrice) {
		this.perUnitPrice = perUnitPrice;
	}

	
	
	
}