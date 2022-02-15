package hr.fer.oprpp1.hw04.db;

public class StudentRecord {
	//jmbag, lastName, firstName, finalGrade
	private String jmbag;
	private String lastName;
	private String firstName;
	private int finalGrade;
	
	public StudentRecord(String jmbag, String lastName, String firstName, int finalGrade) {
		if(jmbag == null || lastName == null || firstName == null) throw new NullPointerException("Cant pass null!");
		if (finalGrade > 5 || finalGrade < 1) throw new IllegalArgumentException("Grade must be {1, 5}");
		this.jmbag = jmbag;
		this.finalGrade = finalGrade;
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public String getJmbag() {
		return jmbag;
	}

	public String getLastName() {
		return lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public int getFinalGrade() {
		return finalGrade;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((jmbag == null) ? 0 : jmbag.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof StudentRecord))
			return false;
		StudentRecord other = (StudentRecord) obj;
		if (jmbag == null) {
			if (other.jmbag != null)
				return false;
		} else if (!jmbag.equals(other.jmbag))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "StudentRecord [jmbag=" + jmbag + ", lastName=" + lastName + ", firstName=" + firstName + ", finalGrade="
				+ finalGrade + "]";
	}
	
}
