package com.amazonaws.lambda.demo;

public class CourseUpdateEvent{
	private String courseId;
	private String department;
	private long rosterSize;
	private String topic;
	private String boardId;
	
    public CourseUpdateEvent() {
    
    }
    
    public CourseUpdateEvent(String courseId, String department, long rosterSize, String boardId) {
        this.courseId = courseId;
        this.department = department;
        this.rosterSize = rosterSize;
        this.boardId = boardId;
        this.topic = "";
    }

	public String getCourseId() {
		return courseId;
	}

	public String getDepartment() {
		return department;
	}

	public long getRosterSize() {
		return rosterSize;
	}

	public String getTopic() {
		return topic;
	}

	public String getBoardId() {
		return boardId;
	}

	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public void setRosterSize(long rosterSize) {
		this.rosterSize = rosterSize;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public void setBoardId(String boardId) {
		this.boardId = boardId;
	}
    
	@Override
	//seems must like this to output like the Json format
    public String toString() {
        return "{\"courseId\": \"" + courseId + "\", \"department\": \"" + department +
                "\", \"boardId\": \"" + boardId + "\", \"topic\": \"" + topic + "\", \"size\": " + rosterSize + "}";
    }
	
}