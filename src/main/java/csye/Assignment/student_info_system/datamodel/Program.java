package csye.Assignment.student_info_system.datamodel;

import java.util.List;

public class Program {
	private List<Course> courseList;
	
	public Program() {
		
	}
	
	public Program(List<Course> courseList) {
		this.courseList = courseList;
	}

	public List<Course> getCourseList() {
		return courseList;
	}

	public void setCourseList(List<Course> courseList) {
		this.courseList = courseList;
	}
	
	
}