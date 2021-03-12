package application;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Amount {
	
	private SimpleIntegerProperty universityId;
	private SimpleStringProperty universityName;
	private SimpleStringProperty academicYearName;
	private SimpleIntegerProperty amount;
	
	
	
	public Amount(Integer universityId, String universityName, String academicYear, Integer amount) {
		super();
		this.universityId = new SimpleIntegerProperty(universityId);
		this.universityName = new SimpleStringProperty(universityName);
		this.academicYearName = new SimpleStringProperty(academicYear);
		this.amount = new SimpleIntegerProperty(amount);
	}



	public Integer getUniversityId() {
		return universityId.get();
	}
	
	

	public String getUniversityName() {
		return universityName.get();
	}



	public String getAcademicYear() {
		return academicYearName.get();
	}



	public Integer getAmount() {
		return amount.get();
	}
	
	
	
	
	
	

}
