import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBO
{
	Connection con = null;		//���ݿ�����
	Statement stmt = null;
	public DBO() throws SQLException	//����������
	{
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch(ClassNotFoundException ce) {
			System.out.println("SQLException:"+ce.getMessage());
		}
		con=DriverManager.getConnection("jdbc:mysql://localhost/highscore", "root", "19901111");
		stmt = con.createStatement();
	}
	public ResultSet Query(String sql) throws SQLException	//��ѯ���ݿ�
	{
		return stmt.executeQuery(sql);
	}
	
	public int sqlOp(String sql) throws SQLException		//�������ݿ�
	{
		return stmt.executeUpdate(sql);
	}
}
