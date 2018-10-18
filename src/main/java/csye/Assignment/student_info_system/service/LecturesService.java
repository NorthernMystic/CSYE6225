package csye.Assignment.student_info_system.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import csye.Assignment.student_info_system.datamodel.InMemoryDatabase;
import csye.Assignment.student_info_system.datamodel.Lecture;


public class LecturesService {
	private long nextAvailableId = 0;
	static HashMap<Long, Lecture> Lecture_Map = InMemoryDatabase.getLectureDB();
	
	// Getting a list of all Lectures
	public List<Lecture> getAllLectures() {	
		//Getting the list
		List<Lecture> list = new ArrayList<>();
		for (Lecture Lecture : Lecture_Map.values()) {
			list.add(Lecture);
		}
		return list ;
	}

	// Adding a Lecture
	public void addLecture() {
		//Create a Lecture Object
		Lecture Lecture = new Lecture();
		Lecture_Map.put(nextAvailableId, Lecture);
		
		nextAvailableId++;
	}
	
	public Lecture addLecture(Lecture Lecture) {	
		Lecture_Map.put(nextAvailableId, Lecture);
		nextAvailableId++;
		return Lecture;
	}
	
	// Getting One Lecture
	public Lecture getLecture(Long LectureId) {
		return Lecture_Map.get(LectureId);
	}
	
	// Deleting a Lecture
	public Lecture deleteLecture(Long LectureId) {
		Lecture deletedLectureDetails = Lecture_Map.get(LectureId);
		Lecture_Map.remove(LectureId);
		return deletedLectureDetails;
	}
	
	// Updating Lecture Info
	public Lecture updateLectureInformation(Long LectureId, Lecture Lecture) {
		// Publishing New Values
		Lecture_Map.put(LectureId, Lecture) ;
		
		return Lecture;
	}
	
		
}
