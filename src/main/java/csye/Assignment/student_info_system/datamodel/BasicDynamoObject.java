package csye.Assignment.student_info_system.datamodel;

public abstract class BasicDynamoObject {
	public long id;
	
	public abstract long getId();
	public abstract void setId(long id);
}