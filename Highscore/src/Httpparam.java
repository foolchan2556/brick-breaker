import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.http.HttpResponse;

public class Httpparam extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response)	//����get����
			throws ServletException, IOException {
		String cmd = request.getParameter("cmd");								//��ȡ����
		if (cmd.equals("new")) {												//����Ϊ�����³ɼ�
			DBO dbo = null;
			try {
				dbo = new DBO();
				if (dbo.sqlOp("insert into scores values('"						//�������ݿ�
						+ request.getParameter("id") + "', "
						+ request.getParameter("score") + ")") > 0) {
					System.out.println("update successfully!");
					PrintWriter out = response.getWriter();
					out.println("update successfully!");						//���سɹ����
				} else {
					System.out.println("failure");
					PrintWriter out = response.getWriter();
					out.println("failure");										//����ʧ�ܽ��
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else if (cmd.equals("list")) {										//����Ϊ�г����а�
			DBO dbo;
			try {
				dbo = new DBO();
				String sql = "select * from scores order by score desc limit 10";//��˳���ų��÷���ߵ��Ǹ�
				String scores = "";
				ResultSet rs = dbo.Query(sql);
				while (rs.next()) {												//���������ַ���
					scores += rs.getString(1) + " ";
					scores += rs.getString(2) + " ";
				}
				PrintWriter out = response.getWriter();
				out.println(scores);											//���ؽ��
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
