package vis.data.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseAdapter {

	/*
	 * TODO: Need to change database name, user and password
	 */
	private static final String HOST = "localhost", DB_NAME = "myria-db", USER = "root", PASSWORD = "";
	private static Connection connection = null;
	private static Statement statement = null;
	private static PreparedStatement preparedStatement = null;
	private static ResultSet resultSet = null;

	private static void connect() throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.jdbc.Driver");
		connection = DriverManager
				.getConnection("jdbc:mysql://" + HOST + "/" + DB_NAME + "?user=" + USER + "&password=" + PASSWORD);
	}

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
		} catch (Exception e) {

		}
	}

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
