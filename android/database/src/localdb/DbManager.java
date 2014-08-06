package localdb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
 
/** Database connection class & utilities **/
 
public class DbManager {
	
	private int timeout = 30;
	
	private Connection connection;
	
	private Statement statement;
	
	/** The driver name. */
	private String driverName = "org.sqlite.JDBC";
	
	/** The Le Chef data base name. */
	private String dressItUpDataBase = "dressitup.db";
	
	/** The jdbc. */
	private String jdbc = "jdbc:sqlite"; // JDBC driver
    
    /** The Data Base url. */
    private String dbUrl = jdbc + ":" + dressItUpDataBase; // JDBC connection String
	
	/* DriverManager name and the fully loaded url to handle */
	public DbManager() throws Exception {
		System.out.println("DbManager::init >> Start db Load to:" + dbUrl);
		this.init();
		System.out.println("DbManager::init >> Load successfully established!");
	}
	 
	private void init() throws Exception {
		setConnection();
		setStatement();
	}
	 
	private void setConnection() throws Exception {
		Class.forName(driverName); // Load Driver
		this.connection = DriverManager.getConnection(dbUrl);
		//System.out.println("DbManager::setConnection()");
	}
	 
	private void setStatement() throws Exception {
		if (this.connection == null) {
			System.out.println("DbManager::setConnection >> Connection Initiated");
			setConnection();
			System.out.println("DbManager::setConnection >> Connection successfully established!");
		}
		
		this.statement = this.connection.createStatement();
		this.statement.setQueryTimeout(this.timeout);  // set timeout.
	}
	 
	public void executeStmt(String instruction) throws Exception {
		this.setConnection();
		this.statement.executeUpdate(instruction);
		this.closeConnection();
	}
	 
	public void executeStmt(ArrayList<String> statementList) throws Exception {
		for (String statement : statementList) {
			executeStmt(statement);
		}
	}
	 
	public ResultSet executeQuery(String instruction) throws SQLException {
		return this.statement.executeQuery(instruction);
	}
	 
	public void closeConnection() {
		try { 
			this.connection.close();
			//System.out.println("DbManager::closeConnection()");
		} catch (Exception ignore) {}
	} 
}