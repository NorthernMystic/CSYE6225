package csye.Assignment.student_info_system.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import csye.Assignment.student_info_system.datamodel.InMemoryDatabase;
import csye.Assignment.student_info_system.datamodel.Professor;


public class ProfessorsService {
	private long nextAvailableId = 0;
	static HashMap<Long, Professor> prof_Map = InMemoryDatabase.getProfessorDB();
	
	// Getting a list of all professor
	public List<Professor> getAllProfessors() {	
		//Getting the list
		List<Professor> list = new ArrayList<>();
		for (Professor prof : prof_Map.values()) {
			list.add(prof);
		}
		return list ;
	}

	// Adding a professor
	public void addProfessor(String name, String program) {
		//Create a Professor Object
		Professor prof = new Professor(nextAvailableId, name , 
				program);
		prof_Map.put(nextAvailableId, prof);
		
		nextAvailableId++;
	}
	
	public Professor addProfessor(Professor prof) {	
		prof.setProfessorId(nextAvailableId);
		prof_Map.put(nextAvailableId, prof);
		
		nextAvailableId++;
		return prof_Map.get(nextAvailableId - 1);
	}
	
	// Getting One Professor
	public Professor getProfessor(Long profId) {
		return prof_Map.get(profId);
	}
	
	// Deleting a professor
	public Professor deleteProfessor(Long profId) {
		if (!prof_Map.containsKey(profId)) return null;
		Professor deletedProfDetails = prof_Map.get(profId);
		prof_Map.remove(profId);
		return deletedProfDetails;
	}
	
	// Updating Professor Info
	public Professor updateProfessorInformation(Long profId, Professor prof) {
		prof.setProfessorId(profId);
		// Publishing New Values
		prof_Map.put(profId, prof) ;
		
		return prof;
	}
	
	// Get professors in a department 
	public List<Professor> getProfessorsByProgram(String program) {	
		//Getting the list
		List<Professor> list = new ArrayList<>();
		for (Professor prof : prof_Map.values()) {
			if (prof.getprogram().equals(program)) {
				list.add(prof);
			}
		}
		return list ;
	}
	
	
}
