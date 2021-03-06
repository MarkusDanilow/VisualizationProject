package vis.data.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

public class DatabaseAdapter {

	/*
	 * Database credentials
	 */
	private static final String DB_HOST = "rdbms.strato.de", DB_NAME = "DB3162645", DB_USER = "U3162645",
			DB_PASSWORD = "H4Cd84V!5";
	private static final int DB_PORT = 3306;

	/* SSH credentials */
	private static final int SSH_PORT = 22;
	private static final String SSH_HOST = "ssh.strato.de", SSH_USER = "liquidsolution.de", SSH_PASSWORD = "S5!01Tx1c";

	private static Session session = null;
	private static Connection connection = null;
	private static Statement statement = null;
	private static PreparedStatement preparedStatement = null;
	private static ResultSet resultSet = null;

	/* need to move this to the model later on */
	public static final String TABLE_NAME = "HUC_VIS";

	/**
	 * Creates a new SSH connection and returns the corresponding session
	 * reference.
	 * 
	 * @return
	 * @throws JSchException
	 */
	public static Session establishSSHConnection(boolean usePortForwarding) throws JSchException {
		JSch jsch = new JSch();
		Session session = jsch.getSession(SSH_USER, SSH_HOST, SSH_PORT);
		session.setPassword(SSH_PASSWORD);

		Properties config = new Properties();
		config.put("StrictHostKeyChecking", "no");
		session.setConfig(config);

		session.connect();

		if (usePortForwarding) {
			session.setPortForwardingL(DB_PORT, DB_HOST, DB_PORT);
		}
		return session;
	}

	/**
	 * Closes an existing SSH connection
	 * 
	 * @param session
	 */
	public static void closeSSHConnection(Session session) {
		if (session != null) {
			session.disconnect();
		}
	}

	/**
	 * Connects to the database
	 * 
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws JSchException
	 */
	private static void connect() throws ClassNotFoundException, SQLException, JSchException {
		// session = establishSSHConnection(false);
		Class.forName("com.mysql.jdbc.Driver");
		String connectionURL = "jdbc:mysql://" + DB_HOST + ":" + DB_PORT + "/" + DB_NAME + "?user=" + DB_USER
				+ "&password=" + DB_PASSWORD;
		connection = DriverManager.getConnection(connectionURL);
	}

	/**
	 * Closes the database connection
	 */
	private static void close() {
		try {
			if (resultSet != null) {
				resultSet.close();
			}

			if (statement != null) {
				statement.close();
			}

			if (connection != null) {
				connection.close();
			}

			closeSSHConnection(session);
		} catch (Exception e) {

		}
	}

	/**
	 * Executes any SQL query based on prepared statements. The results will be
	 * handled in an {@link IResultSetHandler}, which needs to be specified as a
	 * parameter. Otherwise the data received from the database will be lost.
	 * 
	 * @param handler
	 * @param query
	 * @param data
	 */
	public static void execute(IResultSetHandler handler, String query, Object... data) {
		try {
			connect();
			preparedStatement = connection.prepareStatement(query);
			if (data != null) {
				for (int i = 0; i < data.length; i++) {
					preparedStatement.setObject(i + 1, data[i]);
				}
			}
			resultSet = preparedStatement.executeQuery();
			if (handler != null)
				handler.handleResultSet(resultSet);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
	}

}
