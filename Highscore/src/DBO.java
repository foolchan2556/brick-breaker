import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBO
{
	Connection con = null;		//数据库连接
	Statement stmt = null;
	public DBO() throws SQLException	//创建新连接
	{
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch(ClassNotFoundException ce) {
			System.out.println("SQLException:"+ce.getMessage());
		}
		con=DriverManager.getConnection("jdbc:mysql://localhost/highscore", "root", "19901111");
		stmt = con.createStatement();
	}
	public ResultSet Query(String sql) throws SQLException	//查询数据库
	{
		return stmt.executeQuery(sql);
	}
	
	public int sqlOp(String sql) throws SQLException		//更新数据库
	{
		return stmt.executeUpdate(sql);
	}
}
