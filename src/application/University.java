package application;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class University {
	
	private SimpleIntegerProperty univeristyId;
	private SimpleStringProperty universityLongName;
	private SimpleStringProperty universityShortName;
	
	
	public University(Integer univeristyId, String universityLongName, String universityShortName) {
		super();
		this.univeristyId = new SimpleIntegerProperty(univeristyId);
		this.universityLongName = new SimpleStringProperty(universityLongName);
		this.universityShortName = new SimpleStringProperty(universityShortName);
	}

	

	public University(String universityLongName, String universityShortName) {
		super();
		this.universityLongName = new SimpleStringProperty(universityLongName);
		this.universityShortName = new SimpleStringProperty(universityShortName);
	}



	public Integer getUniveristyId() {
		return univeristyId.get();
	}



	public String getUniversityLongName() {
		return universityLongName.get();
	}



	public String getUniversityShortName() {
		return universityShortName.get();
	}
	
	

}
