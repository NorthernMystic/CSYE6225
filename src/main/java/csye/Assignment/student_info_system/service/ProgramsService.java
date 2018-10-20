package csye.Assignment.student_info_system.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import csye.Assignment.student_info_system.datamodel.InMemoryDatabase;
import csye.Assignment.student_info_system.datamodel.Program;


public class ProgramsService {
	private long nextAvailableId = 0;
	static HashMap<Long, Program> program_Map = InMemoryDatabase.getProgramDB();
	
	// Getting a list of all Programs
	public List<Program> getAllPrograms() {	
		//Getting the list
		List<Program> list = new ArrayList<>();
		for (Program Program : program_Map.values()) {
			list.add(Program);
		}
		return list ;
	}

	// Adding a Program
	public void addProgram() {
		//Create a Program Object
		Program Program = new Program();
		program_Map.put(nextAvailableId, Program);
		
		nextAvailableId++;
	}
	
	public Program addProgram(Program Program) {	
		program_Map.put(nextAvailableId, Program);
		nextAvailableId++;
		return Program;
	}
	
	// Getting One Program
	public Program getProgram(Long ProgramId) {
		return program_Map.get(ProgramId);
	}
	
	// Deleting a Program
	public Program deleteProgram(Long programId) {
		if (!program_Map.containsKey(programId)) return null;
		Program deletedProgramDetails = program_Map.get(programId);
		program_Map.remove(programId);
		return deletedProgramDetails;
	}
	
	// Updating Program Info
	public Program updateProgramInformation(Long ProgramId, Program Program) {
		// Publishing New Values
		program_Map.put(ProgramId, Program) ;
		
		return Program;
	}
	
		
}
