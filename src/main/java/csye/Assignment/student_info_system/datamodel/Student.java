package csye.Assignment.student_info_system.datamodel;

import java.awt.Image;
import java.util.List;

public class Student {
	private String firstName;
	private String lastName;
	private Long id;
	private Image image;
	private List<String> enrolledClass;
	
	public Student() {
		
	}
	
	public Student(String firstName, String lastName, Long id, List<String> enrolledClass) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.id = id;
		this.image = null;
		this.enrolledClass = enrolledClass;
	}
	
	public Student(String firstName, String lastName, Long id, Image image, List<String> enrolledClass) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.id = id;
		this.image = image;
		this.enrolledClass = enrolledClass;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public List<String> getEnrolledClass() {
		return enrolledClass;
	}

	public void setEnrolledClass(List<String> enrolledClass) {
		this.enrolledClass = enrolledClass;
	}
	
	
}
