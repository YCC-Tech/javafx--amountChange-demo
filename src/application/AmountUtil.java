package application;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import database.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class AmountUtil {

	private Connection connection;
	private PreparedStatement pStmt;
	private Statement stmt;
	private ResultSet rs;

	// get university list from db to university combo box
	public ObservableList<String> getUniList(String sql) throws SQLException {
		ObservableList<String> uniList = FXCollections.observableArrayList();
		connection = DBConnection.getConnection();
		stmt = connection.createStatement();
		rs = stmt.executeQuery(sql);

		while (rs.next()) {
			uniList.add(rs.getString("name"));
		}
		System.out.println(uniList);
		return uniList;

	}

	// get academic year list from db to year combo box filtered by univeristy
	public ObservableList<String> getYearList(String sql) throws SQLException {
		ObservableList<String> yearList = FXCollections.observableArrayList();
		connection = DBConnection.getConnection();
		stmt = connection.createStatement();
		rs = stmt.executeQuery(sql);

		while (rs.next()) {
			yearList.add(rs.getString("name"));
		}
		System.out.println(yearList);
		return yearList;

	}

	// get university id from db according to university name
	public int getUniId(String uniName) throws SQLException {
		int uniId = 0;
		connection = DBConnection.getConnection();
		stmt = connection.createStatement();
		rs = stmt.executeQuery("select university_id from universities where name='" + uniName + "';");
		while (rs.next()) {
			uniId = rs.getInt("university_id");

		}
		return uniId;
	}

	// get academic year id from db according to academic year name and university id
	public int getAcademicYearId(Integer uniId,String attendanceYearsName) throws SQLException {
		int attendanceYearId = 0;
		connection = DBConnection.getConnection();
		stmt = connection.createStatement();
		rs = stmt.executeQuery(
				"select attendance_year_id from attendance_years where name='" + attendanceYearsName + "' and university_id='"+uniId+"';");
		while (rs.next()) {
			attendanceYearId = rs.getInt("attendance_year_id");

		}
		return attendanceYearId;
	}

	// amount of all universities and its academic year
	public ObservableList<Amount> getAmountList() throws SQLException {
		ObservableList<Amount> amountList = FXCollections.observableArrayList();
		connection = DBConnection.getConnection();
		stmt = connection.createStatement();

		// get number of rows in scholarship_amounts
		rs = stmt.executeQuery("select count(*) as Number from scholarship_amounts;");
		rs.next();
		System.out.println("Total in amount" + rs.getInt("Number"));
		Integer[][] uniId_yearId = new Integer[rs.getInt("Number")][3];

		// get valid university id and respective attendance year id
		rs = stmt.executeQuery(
				"select  university_id, attendance_year_id,amount from scholarship_amounts order by university_id;");
		int i = 0;
		while (rs.next()) {
			uniId_yearId[i][0] = rs.getInt("university_id");
			uniId_yearId[i][1] = rs.getInt("attendance_year_id");
			uniId_yearId[i][2] = rs.getInt("amount");
			System.out.println(i++);
			;
		}

		// get uniId, uniName, uniAttendanceYearName, respective amount
		for (int j = 0; j < uniId_yearId.length; j++) {
			rs = stmt.executeQuery(
					"SELECT u.university_id,u.name as universityName, a.name as attendanceYearName FROM universities u,attendance_years a where u.university_id= a.university_id and u.university_id='"
							+ uniId_yearId[j][0] + "' and a.attendance_year_id='" + uniId_yearId[j][1] + "';");
			if (rs.next()) {
				amountList.add(new Amount(rs.getInt("university_id"), rs.getString("universityName"),
						rs.getString("attendanceYearName"), uniId_yearId[j][2]));
			}
			System.out.println(uniId_yearId[j]);
		}

		System.out.println(amountList);
		return amountList;

	}

	// amount of all universities and its academic year
	public ObservableList<Amount> getAmountList(Integer uniId, Integer yearId) throws SQLException {

		System.out.println(uniId + "  " + yearId);
		ObservableList<Amount> amountList = FXCollections.observableArrayList();
		connection = DBConnection.getConnection();
		stmt = connection.createStatement();
		int confirmUniId = 0;
		int confirmYearId = 0;
		int amount = 0;

		// get valid university id and respective attendance year id
		rs = stmt.executeQuery(
				"select  university_id, attendance_year_id,amount from scholarship_amounts where university_id='"
						+ uniId + "' and attendance_year_id='" + yearId + "';");
		int i = 0;
		while (rs.next()) {
			confirmUniId = rs.getInt("university_id");
			confirmYearId = rs.getInt("attendance_year_id");
			amount = rs.getInt("amount");
		}
		System.out.println(confirmUniId + "   " + confirmYearId + "   " + amount);

		// get uniId, uniName, uniAttendanceYearName, respective amount

		rs = stmt.executeQuery(
				"SELECT u.university_id,u.name as universityName, a.name as attendanceYearName FROM universities u,attendance_years a where u.university_id= a.university_id and u.university_id='"
						+ confirmUniId + "' and a.attendance_year_id='" + confirmYearId + "';");
		if (rs.next()) {
			amountList.add(new Amount(rs.getInt("university_id"), rs.getString("universityName"),
					rs.getString("attendanceYearName"), amount));
		}
		System.out.println(amountList);
		return amountList;

	}
	
	
	public int updateAmount(Amount amount) throws SQLException {
		connection = DBConnection.getConnection();
		
		var rowUpdated = 0;
		rowUpdated = stmt.executeUpdate("update scholarship_amounts set amount='"+amount.getAmount()+"' where university_id='"+amount.getUniversityId()+"' and attendance_year_id='"+getAcademicYearId(amount.getUniversityId(), amount.getAcademicYear())+"';"	);
		return rowUpdated;
	}

}
