package application;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Major {
	
	private SimpleIntegerProperty majorId;
	private SimpleStringProperty majorLongName;
	private SimpleStringProperty majorShortName;
	private SimpleIntegerProperty universityId;
	
	
	public Major(Integer majorId, String majorLongName, String majorShortName, Integer universityId) {
		super();
		this.majorId = new SimpleIntegerProperty(majorId);
		this.majorLongName = new SimpleStringProperty(majorLongName);
		this.majorShortName = new SimpleStringProperty(majorShortName);
		this.universityId = new SimpleIntegerProperty(universityId);
	}
	
	
	public Major(String majorLongName, String majorShortName, Integer universityId) {
		super();
		this.majorLongName = new SimpleStringProperty(majorLongName);
		this.majorShortName = new SimpleStringProperty(majorShortName);
		this.universityId = new SimpleIntegerProperty(universityId);
	}


	public Integer getMajorId() {
		return majorId.get();
	}


	public String getMajorLongName() {
		return majorLongName.get();
	}


	public String getMajorShortName() {
		return majorShortName.get();
	}


	public Integer getUniversityId() {
		return universityId.get();
	}
	
	
	
	

}
