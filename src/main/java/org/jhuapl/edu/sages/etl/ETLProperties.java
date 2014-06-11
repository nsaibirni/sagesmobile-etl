/**
 * 
 */
package org.jhuapl.edu.sages.etl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * A properties loader specifically for loading the required ETL properties files
 * @author POKUAM1
 * @created Oct 28, 2011
 */
public class ETLProperties implements PropertiesLoader {
	/** constants for activated database specific features */
	public static final String dbid_mysql = "mysql";
	public static final String dbid_postgresql = "postgresql";
	public static final String dbid_derby = "derby";
	public static final String dbid_msaccess = "msaccess";

	/** properties holders */
	private Properties props_etlconfig;
	private Properties props_mappings;
	private Properties props_dateformats;
	private Properties props_customsql_cleanse;
	private Properties props_customsql_staging;
	private Properties props_customsql_final_to_prod;
	
	/** target database connection settings*/
	private String dbms;
	private int portNumber;
	private String serverName;
	private String dbName;
	private String userName;
	private String password;
	
	/**
	 * {@link ETLProperties} Constructor
	 */
	public ETLProperties(){
	}


	/* (non-Javadoc)
	 * @see org.jhuapl.edu.sages.etl.PropertiesLoader#loadEtlProperties()
	 */
	@Override
	public void loadEtlProperties() throws SagesEtlException {
		try {
			this.props_etlconfig = new Properties();
			this.props_mappings = new Properties();
			this.props_dateformats = new Properties();
			this.props_customsql_cleanse = new Properties();
			this.props_customsql_staging = new Properties();
			this.props_customsql_final_to_prod = new Properties();
			
			
			this.props_etlconfig.load(new FileInputStream("etlconfig.properties"));
			this.props_mappings.load(new FileInputStream("src-to-dst-column-mappings.properties"));
			this.props_dateformats.load(new FileInputStream("dateformats.properties"));

            //TODO: AK - These filenames and paths need to be specified in an external property file that can be passed
            //           in.  For the time being, it is sufficient to remove the hard coded path separators and replace
            //           with File.separator
			this.props_customsql_cleanse.load(new FileInputStream("customsql" + File.separator + "cleanse_table" + File.separator + "cleanse_sql.properties"));
			this.props_customsql_staging.load(new FileInputStream("customsql" + File.separator + "staging_table" + File.separator + "staging_sql.properties"));
			this.props_customsql_final_to_prod.load(new FileInputStream("customsql" + File.separator + "staging-to-final_loader" + File.separator + "staging-to-final_loader_sql.properties"));
		} catch (IOException e){
			//TODO: LOG THIS ERROR: LIST OUT THE VALID PROPERTY FILES NAMES
			e.printStackTrace();
			throw new SagesEtlException("Problem occurred loading properties. Check that properties files exist", e);
		} 
		
		this.dbms = props_etlconfig.getProperty("dbms").trim();
		this.portNumber = Integer.valueOf(props_etlconfig.getProperty("portNumber")).intValue();
		this.userName = props_etlconfig.getProperty("userName").trim();
		this.password = props_etlconfig.getProperty("password").trim();
		this.serverName = props_etlconfig.getProperty("serverName").trim();
		this.dbName = props_etlconfig.getProperty("dbName").trim();
	}


	public Properties getProps_etlconfig() {
		return props_etlconfig;
	}


	public Properties getProps_mappings() {
		return props_mappings;
	}


	public Properties getProps_dateformats() {
		return props_dateformats;
	}


	public Properties getProps_customsql_cleanse() {
		return props_customsql_cleanse;
	}


	public Properties getProps_customsql_staging() {
		return props_customsql_staging;
	}


	public Properties getProps_customsql_final_to_prod() {
		return props_customsql_final_to_prod;
	}


	public String getDbms() {
		return dbms;
	}


	public int getPortNumber() {
		return portNumber;
	}


	public String getServerName() {
		return serverName;
	}


	public String getDbName() {
		return dbName;
	}


	public String getUserName() {
		return userName;
	}


	public String getPassword() {
		return password;
	}
	
}
