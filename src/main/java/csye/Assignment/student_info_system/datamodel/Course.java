package csye.Assignment.student_info_system.datamodel;

import java.util.List;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAutoGeneratedKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBIndexHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;


@DynamoDBTable(tableName = "Courses")
public class Course extends BasicDynamoObject {
	private String id;
	private String courseId;
	private Long professorId;
	private Long TAId;
	private String department;
	private Long boardId;
	private List<String> roster;
	private String topic;

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public Course() {
		
	}
	
	public Course(String courseId, Long professorId, Long TAId, String department, Long boardId, List<String> roster) {
		this.courseId = courseId;
		this.professorId = professorId;
		this.TAId = TAId;
		this.department = department;
		this.boardId = boardId;
		this.roster = roster;
	}
	
	@DynamoDBHashKey(attributeName = "Id")
	@DynamoDBAutoGeneratedKey
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@DynamoDBAttribute(attributeName="CourseId") 
	@DynamoDBIndexHashKey(attributeName="CourseId", globalSecondaryIndexName = "CourseId")
	public String getCourseId() {
		return courseId;
	}

	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}
	
	@DynamoDBAttribute(attributeName="ProfessorId")
	public Long getProfessorId() {
		return professorId;
	}

	public void setProfessorId(Long professorId) {
		this.professorId = professorId;
	}

	@DynamoDBAttribute(attributeName="TAId")
	public Long getTAId() {
		return TAId;
	}

	public void setTAId(Long TaId) {
		TAId = TaId;
	}
	
	@DynamoDBAttribute(attributeName="Department")
	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	@DynamoDBAttribute(attributeName="BoardId")
	public Long getBoard() {
		return boardId;
	}

	public void setBoard(Long boardId) {
		this.boardId = boardId;
	}

	@DynamoDBAttribute(attributeName="Roster")
	public List<String> getRoster() {
		return roster;
	}

	@DynamoDBAttribute(attributeName="Topic")
	public void setRoster(List<String> roster) {
		this.roster = roster;
	}
}
