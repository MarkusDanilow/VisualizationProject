package vis.data.sql;

import java.sql.ResultSet;

public interface IResultSetHandler {

	void handleResultSet(ResultSet resultSet) throws Exception;

}
