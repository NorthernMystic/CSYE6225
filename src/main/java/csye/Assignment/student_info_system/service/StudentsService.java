package csye.Assignment.student_info_system.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import csye.Assignment.student_info_system.datamodel.InMemoryDatabase;
import csye.Assignment.student_info_system.datamodel.Student;


public class StudentsService {
	private long nextAvailableId = 0;
	static HashMap<Long, Student> student_Map = InMemoryDatabase.getStudentDB();
	
	// Getting a list of all students
	public List<Student> getAllStudents() {	
		//Getting the list
		List<Student> list = new ArrayList<>();
		for (Student student : student_Map.values()) {
			list.add(student);
		}
		return list ;
	}

	// Adding a student
	public void addStudent() {
		//Create a Student Object
		Student student = new Student();
		student_Map.put(nextAvailableId, student);
		
		nextAvailableId++;
	}
	
	public Student addStudent(Student student) {	
		student_Map.put(nextAvailableId, student);
		student.setId(nextAvailableId);
		nextAvailableId++;
		return student;
	}
	
	// Getting One student
	public Student getStudent(Long studentId) {
		return student_Map.get(studentId);
	}
	
	// Deleting a student
	public Student deleteStudent(Long studentId) {
		if (!student_Map.containsKey(studentId)) return null;
		Student deletedstudentDetails = student_Map.get(studentId);
		student_Map.remove(studentId);
		return deletedstudentDetails;
	}
	
	// Updating student Info
	public Student updateStudentInformation(Long studentId, Student student) {
		student.setId(studentId);
		// Publishing New Values
		student_Map.put(studentId, student) ;
		
		return student;
	}
	
		
}
