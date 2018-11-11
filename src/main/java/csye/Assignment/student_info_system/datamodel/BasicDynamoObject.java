package csye.Assignment.student_info_system.datamodel;

public abstract class BasicDynamoObject {
	public String id;
	
	public abstract String getId();
	public abstract void setId(String id);
}