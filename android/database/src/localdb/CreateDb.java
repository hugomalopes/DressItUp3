package localdb;

/**
 * The Class CreateDb, responsible for creating DressItUp DataBase Tables.
 */
public class CreateDb {
	
	/** The Data Base Manager (connection & utilities). */
	private DbManager dbManager = null;
    
    /** The user table. */
    private String userTable;
    
    /** The clothing table. */
    private String clothingTable;
    
    /** The future clothing table. */
    private String futureClothingTable;
    
    /**
     * Instantiates a new db creator.
     *
     * @param dbManager the db manager
     */
    public CreateDb(DbManager dbManager) throws Exception {
    	this.dbManager = dbManager;
    	this.setUserTable();
    	this.setClothingTable();
    	this.setFutureClothingTable();
    }
    
    private void setUserTable() {
    	this.userTable = "CREATE TABLE user " +
									"(uid		INTEGER	PRIMARY KEY	AUTOINCREMENT	NOT NULL," +
						            " username  TEXT    NOT NULL," +
									" UNIQUE(uid, username))";
    }
    
    private String getUserTable () {
    	return this.userTable;
    }
    
    private void setClothingTable() {
    	this.clothingTable = "CREATE TABLE clothing " +
									"(uid 		INTEGER	PRIMARY KEY	AUTOINCREMENT	NOT NULL," +
									" type		TEXT    NOT NULL," + 
						            " category 	TEXT 	NOT NULL," + 
									" brand		TEXT	NOT NULL," +
						            " reference TEXT 	NOT NULL," +
									" color		TEXT	NOT NULL" + ")";
    }
    

    private String getClothingTable() {
    	return this.clothingTable;
    }
    
    private void setFutureClothingTable() {
    	this.futureClothingTable = "CREATE TABLE future_clothing " +
    									"(url	TEXT	NOT NULL" + ")";
    }
    
    private String getFutureClothingTable() {
    	return this.futureClothingTable;
    }
    
    private String dropTable(String tableName) {
    	return "DROP TABLE IF EXISTS " + tableName;
    }
    
    /**
     * Execute all the statements for tables creation.
     * @throws Exception 
     */
    public void execute() throws Exception {
    	System.out.println("CreateDb::execute >> Start table creation");
    	dbManager.executeStmt("PRAGMA foreign_keys = ON;");

    	dbManager.executeStmt(dropTable("user"));;
    	dbManager.executeStmt(this.getUserTable());
    	System.out.println("CreateDb::execute >> User Table created");
    	dbManager.executeStmt(dropTable("clothing"));
    	dbManager.executeStmt(this.getClothingTable());
    	System.out.println("CreateDb::execute >> Clothing Table created");
    	dbManager.executeStmt(dropTable("future_clothing"));
    	dbManager.executeStmt(this.getFutureClothingTable());
    	System.out.println("CreateDb::execute >> Future Clothing Table created");
    }
    
    /**
     * The main method.
     */
    public static void main(String[] args) throws Exception {
    	DbManager manager = new DbManager();
    	CreateDb db = new CreateDb(manager);
    	db.execute();
    	System.out.println("DataBase successfully created!");
    }
}