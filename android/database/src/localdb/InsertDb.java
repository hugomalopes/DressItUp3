package localdb;

/**
 * The Class InsertDb.
 */
public class InsertDb {
	
	/** The db Manager. */
	private DbManager dbManager = null;
	
	public InsertDb (DbManager dbManager) {
		this.dbManager = dbManager;
	}
	
	private DbManager getDbManager() {
		return this.dbManager;
	}

	public void insertClothing() throws Exception {
		String insertIngredient;
		insertIngredient = "INSERT INTO clothing VALUES (null,'top', 'camisa', 'zara', 'cenas', 'branco')";
		// inserts the ingredient
		this.getDbManager().executeStmt(insertIngredient);
	}
}