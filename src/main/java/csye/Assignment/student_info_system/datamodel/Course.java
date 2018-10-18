package csye.Assignment.student_info_system.datamodel;

import java.util.List;

public class Course {
	private List<Lecture> lectures;
	private String board;
	private List<Student> roster;
	private List<Student> enrolledList;
	private Professor professor;
	private Student teachingAssistant;
	
	public Course() {
		
	}
	
	public Course(List<Lecture> lectures, String board, List<Student> roster,
			List<Student> enrolledList, Professor professor, Student teachingAssistant) {
		this.lectures = lectures;
		this.board = board;
		this.roster = roster;
		this.enrolledList = enrolledList;
		this.professor = professor;
		this.teachingAssistant = teachingAssistant;
	}

	public List<Lecture> getLectures() {
		return lectures;
	}

	public void setLectures(List<Lecture> lectures) {
		this.lectures = lectures;
	}

	public String getBoard() {
		return board;
	}

	public void setBoard(String board) {
		this.board = board;
	}

	public List<Student> getRoster() {
		return roster;
	}

	public void setRoster(List<Student> roster) {
		this.roster = roster;
	}

	public List<Student> getEnrolledList() {
		return enrolledList;
	}

	public void setEnrolledList(List<Student> enrolledList) {
		this.enrolledList = enrolledList;
	}

	public Professor getProfessor() {
		return professor;
	}

	public void setProfessor(Professor professor) {
		this.professor = professor;
	}

	public Student getTeachingAssistant() {
		return teachingAssistant;
	}

	public void setTeachingAssistant(Student teachingAssistant) {
		this.teachingAssistant = teachingAssistant;
	}
	
	
}
