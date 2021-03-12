package application;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

public class EduSettingController implements Initializable{



 
    @FXML
    private Tab tabAmount;
    @FXML
    private TableView <Amount> tbAmount;
    
    @FXML
    private ComboBox<String> cobUniversity;

    @FXML
    private ComboBox<String> cobAcademicYear;

    @FXML
    private TableColumn<Amount, Integer> universityIdForAmount;
    
    @FXML
    private TableColumn<Amount, String> universityName;

    @FXML
    private TableColumn<Amount, String>academicYear;

    @FXML
    private TableColumn<Amount, Integer> amount;

//    @FXML
//    private TextField tfSearch;
    
    @FXML
    private AnchorPane apScholarAmount;
    
    @FXML
    private TextField tfAmount;

    @FXML
    private Label lblUniName;

    @FXML
    private Label lblYearName;

    @FXML
    private Button btnCancelAmount;

    @FXML
    private Button btnUpdatAmount;
    
    
    
    private AmountUtil amountUtil = new AmountUtil();
    int uniId=0;
    int academicYearId= 0;

    @FXML
    void processAdduniveristy(ActionEvent event) {

    }

    @FXML
    void processDeleteUniversity(ActionEvent event) {

    }

    @FXML
    void processUpdateUniversity(ActionEvent event) {

    }
    
    /*--start processes for 'Change amount' anchorPane*/
    @FXML
    void processCancelAmount(ActionEvent event) {
    	apScholarAmount.setVisible(false);

    }
    

    @FXML
    void processUpdatedAmount(ActionEvent event) throws SQLException {

    
    	Amount amountdB = new Amount(uniId,lblUniName.getText(), lblYearName.getText(),Integer.parseInt(tfAmount.getText()));
    	amountUtil.updateAmount(amountdB);    
    	showAmount();
    	apScholarAmount.setVisible(false);

    }
    
    
    /*--end 'Change amount' */
    
    //get data when double click to table row
    private void doubleClickTableRow() {
    	tbAmount.setRowFactory( tv -> {
		    TableRow<Amount> row = new TableRow<>();
		    row.setOnMouseClicked(event -> {
		        if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
		        	Amount rowData = row.getItem();
		        	//set data to amount change form
		        	lblUniName.setText(rowData.getUniversityName());
		        	lblYearName.setText(rowData.getAcademicYear());
		        	tfAmount.clear();
		        	tfAmount.setPromptText(String.valueOf(rowData.getAmount()));
		        	//set visible to amount change form 
		        	apScholarAmount.setVisible(true);
		        	uniId = rowData.getUniversityId();
		            System.out.println(rowData.getUniversityName()+" "+rowData.getAcademicYear()+" "+rowData.getAmount());
		        }
		    });
		    return row ;
		});
    	
    	
    }
    
    
  //connect table and data (there is one overload method)
	private void showAmount() throws SQLException {

		universityIdForAmount.setCellValueFactory(new PropertyValueFactory<Amount, Integer>("universityId"));

		universityName.setCellValueFactory(new PropertyValueFactory<Amount, String>("universityName"));

		academicYear.setCellValueFactory(new PropertyValueFactory<Amount, String>("academicYear"));

		amount.setCellValueFactory(new PropertyValueFactory<Amount, Integer>("amount"));

		
		try {
			tbAmount.setItems(amountUtil.getAmountList());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		doubleClickTableRow();

	}

	
	private void showAmount(Integer universityId, Integer academicYearId) {

		universityIdForAmount.setCellValueFactory(new PropertyValueFactory<Amount, Integer>("universityId"));

		universityName.setCellValueFactory(new PropertyValueFactory<Amount, String>("universityName"));

		academicYear.setCellValueFactory(new PropertyValueFactory<Amount, String>("academicYear"));

		amount.setCellValueFactory(new PropertyValueFactory<Amount, Integer>("amount"));

		
		try {
			tbAmount.setItems(amountUtil.getAmountList(universityId, academicYearId));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		doubleClickTableRow();

	}
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		//initially set Update amount form invisible
		apScholarAmount.setVisible(false);		
		
		
		try {
			showAmount();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		try {
			cobUniversity.setItems(amountUtil.getUniList("select * from universities;"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		cobUniversity.valueProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observeValue, String oldUniversity, String newUniversity) {
				try {
					uniId= amountUtil.getUniId(newUniversity);
				} catch (SQLException e) {
					e.printStackTrace();
				}
				try {
					cobAcademicYear.setItems(amountUtil.getYearList("select * from attendance_years where university_id='"+ uniId+"';"));
				} catch (SQLException e) {
					e.printStackTrace();
				}
				
				cobAcademicYear.valueProperty().addListener(new ChangeListener<String>() {

					@Override
					public void changed(ObservableValue<? extends String> observeYear, String oldAcademicYear, String newAcademicYear) {
						try {
							academicYearId = amountUtil.getAcademicYearId(uniId,newAcademicYear);
						} catch (SQLException e) {
							e.printStackTrace();
						}
						showAmount(uniId, academicYearId);
						
						
						
					}
				});
				
				
			}
		});
		
		
	}
	
	
}
