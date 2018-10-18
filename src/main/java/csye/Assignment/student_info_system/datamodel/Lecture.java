package csye.Assignment.student_info_system.datamodel;

public class Lecture {
	private String Notes;
	private String matetial;
	
	public Lecture() {
		
	}
	
	public Lecture(String Notes, String material) {
		this.Notes = Notes;
		this.matetial = material;
	}

	public String getNotes() {
		return Notes;
	}

	public void setNotes(String notes) {
		Notes = notes;
	}

	public String getMatetial() {
		return matetial;
	}

	public void setMatetial(String matetial) {
		this.matetial = matetial;
	}
	
	
}
