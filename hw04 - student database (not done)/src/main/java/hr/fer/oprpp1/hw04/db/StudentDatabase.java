package hr.fer.oprpp1.hw04.db;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StudentDatabase {
	private List<StudentRecord> students;
	private Map<String, StudentRecord> index;
	
	public StudentDatabase(List<String> studentList) {
		if(studentList == null) throw new NullPointerException("Null cant be passed!");
		this.students = new ArrayList<>(studentList.size());
		this.index = new HashMap<> (studentList.size());
		
		for(String student : studentList) {
			StudentRecord parsed = parseStudentRecord(student);
			if(index.containsKey(parsed.getJmbag())) throw new IllegalArgumentException("students with same jbmag exists!");
			
			students.add(parsed);
			index.put(parsed.getJmbag(), parsed);
		}
	}

	private StudentRecord parseStudentRecord(String student) throws NumberFormatException {
		String [] splitted = student.split("\\t+"); //changed from \\s+
		if(splitted.length != 4) throw new IllegalArgumentException("Record must have 4 components!");
		return new StudentRecord(splitted[0], splitted[1], splitted[2], Integer.parseInt(splitted[3]));
	}
	
	public StudentRecord forJMBAG(String jmbag) {
		return index.get(jmbag);
	}
	
	public List<StudentRecord> filter(IFilter filter){
		//TODO
		return students;
		
	}
}
