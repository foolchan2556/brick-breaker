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
	public void doGet(HttpServletRequest request, HttpServletResponse response)	//处理get请求
			throws ServletException, IOException {
		String cmd = request.getParameter("cmd");								//获取参数
		if (cmd.equals("new")) {												//参数为插入新成绩
			DBO dbo = null;
			try {
				dbo = new DBO();
				if (dbo.sqlOp("insert into scores values('"						//插入数据库
						+ request.getParameter("id") + "', "
						+ request.getParameter("score") + ")") > 0) {
					System.out.println("update successfully!");
					PrintWriter out = response.getWriter();
					out.println("update successfully!");						//返回成功结果
				} else {
					System.out.println("failure");
					PrintWriter out = response.getWriter();
					out.println("failure");										//返回失败结果
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else if (cmd.equals("list")) {										//参数为列出排行榜
			DBO dbo;
			try {
				dbo = new DBO();
				String sql = "select * from scores order by score desc limit 10";//按顺序排出得分最高的是个
				String scores = "";
				ResultSet rs = dbo.Query(sql);
				while (rs.next()) {												//将结果组成字符串
					scores += rs.getString(1) + " ";
					scores += rs.getString(2) + " ";
				}
				PrintWriter out = response.getWriter();
				out.println(scores);											//返回结果
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
