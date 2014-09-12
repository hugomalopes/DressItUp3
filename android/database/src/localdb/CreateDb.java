package localdb;

/**
 * The Class CreateDb, responsible for creating DressItUp DataBase Tables.
 */
public class CreateDb {
	
	/** The Data Base Manager (connection & utilities). */
	private DbManager dbManager = null;
    
	/** The android metadata table */
	private String androidTable;
	
	/** The android metadata table */
	private String insertAndroidTable;
	
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
    	this.setAndroidTable();
    	this.setUserTable();
    	this.setClothingTable();
    	this.setFutureClothingTable();
    }
    
    private void setAndroidTable() {
    	this.androidTable = "CREATE TABLE android_metadata" + 
    								"(locale TEXT DEFAULT 'en_US')";
    	this.insertAndroidTable = "INSERT INTO android_metadata VALUES ('en_US')";
    }
    
    private String getAndroidTable() {
    	return this.androidTable;
    }
    
    private String getInsertAndroidTable() {
    	return this.insertAndroidTable;
    }
    
    private void setUserTable() {
    	this.userTable = "CREATE TABLE user " +
									"(_id		INTEGER	PRIMARY KEY	AUTOINCREMENT	NOT NULL," +
						            " username  TEXT    NOT NULL," +
									" UNIQUE(_id, username))";
    }
    
    private String getUserTable () {
    	return this.userTable;
    }
    
    private void setClothingTable() {
    	this.clothingTable = "CREATE TABLE clothing " +
									"(_id 		INTEGER	PRIMARY KEY	AUTOINCREMENT	NOT NULL," +
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
    	
    	dbManager.executeStmt(dropTable("android_metadata"));;
    	dbManager.executeStmt(this.getAndroidTable());
    	System.out.println("CreateDb::execute >> Android Metadata Table created");
    	dbManager.executeStmt(this.getInsertAndroidTable());
    	System.out.println("CreateDb::execute >> Inserted data on Android Metadata Table successfully");
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
    	InsertDb idb = new InsertDb(manager);
    	db.execute();
    	idb.insertClothing();
    	System.out.println("DataBase successfully created!");
    }
}