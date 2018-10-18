package csye.Assignment.student_info_system.datamodel;



public class Professor {
	private String firstName;
	private String program;
	private long professorId;
	
	public Professor() {
		
	}
	
	public Professor(long professorId, String firstName, String program) {
		this.professorId = professorId;
		this.firstName = firstName;
		this.program = program;
	}
	
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getprogram() {
		return program;
	}
	public void setprogram(String program) {
		this.program = program;
	}

	public long getProfessorId() {
		return professorId;
	}
	public void setProfessorId(long professorId) {
		this.professorId = professorId;
	}

}
