import java.sql.*;

public class Database {
	public Database(String user, String pass, String name) // CONSTRUCTOR WITH PARAMETERS --> USER(USERNAME FROM REGISTER GUI), PASS(PASSWORD FROM REGISTER GUI), NAME(NAME FROM REGISTER GUI)
	{
		try {
			Class.forName("org.h2.Driver"); 
			Connection con = DriverManager.getConnection("jdbc:h2:~/test","sa","sa"); // CONNECTION TO DATABASE WITH PASSWORD AND USERNAME 'SA'
			Statement state = con.createStatement();
			
			System.out.println("Creating table called 'SAVEDUSERS'....");
			
				String table = "CREATE TABLE IF NOT EXISTS SAVEDUSERS (USERNAME VARCHAR(255) PRIMARY KEY, PASSWORD VARCHAR(255), NAME VARCHAR(255));"; // CREATES TABLE IN DATABASE IF IT DOESNT ALREADY EXISTS
				int n = state.executeUpdate(table);
				System.out.println("	Update count: " + n);

			System.out.println(" ");
				String insertQ = "INSERT INTO SAVEDUSERS VALUES('" + user + "', '" + pass + "', '" + name + "');"; // CREATES A NEW ROW IN THE TABLE ON THE DATABASE WITH THE VALUES PASSED THROUGH TO THE CLASS
				int n1 = state.executeUpdate(insertQ);
				System.out.println("	Update count: " + n1);

			state.close();
			con.close();
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
