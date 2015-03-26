import java.io.*;
import java.net.*;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Soc extends Thread {
	int port;//�˿ں�
	ServerSocket ssocket;//�������׽���
	Socket socket;//�׽�������

	public Soc(int port) {
		this.port = port;
	}

	public void run() {//�¿�һ���̣߳�Ϊ�������ͻ���
		try {
			ssocket = new ServerSocket(port);
			socket = ssocket.accept();//���֣���������
			System.out.println("connection accepted " + socket);
			ObjectOutputStream oos = new ObjectOutputStream(//����������
					socket.getOutputStream());
			ObjectInputStream ois = new ObjectInputStream(
					socket.getInputStream());
			String param = null;
			Boolean flag = true;
			while (flag) {
				param = (String) ois.readObject();//�Ӷ������ж�ȡ�������ַ���
				if (param.equals("new")) {//����ǲ���÷ֵ�����
					this.update(ois);
					this.list(oos);
					flag = false;
				} else if (param.equals("list")) {//������г��÷ֵ�����
					this.list(oos);
					flag = false;
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			System.out.println("disconnected");
			try {
				ssocket.close();//�Ͽ�����
				socket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private void list(ObjectOutputStream oos) throws SQLException, IOException {//�г��÷ְ�
		// TODO Auto-generated method stub
		DBO dbo = new DBO();
		String sql = "select * from scores order by score desc limit 10;";
		String scores = null;
		ResultSet rs = dbo.Query(sql);
		while (rs.next()) {
			scores += rs.getString(1) + " ";
			scores += rs.getString(2) + " ";
		}
		oos.writeObject(scores);
	}

	private void update(ObjectInputStream ois) throws IOException,//���µ÷ְ�
			ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		DBO dbo = new DBO();
		String score = (String) ois.readObject();
		String[] item = score.split(" ");
		if (dbo.sqlOp("insert into scores values('" + item[0] + "', " + item[1]
				+ ")") > 0)
			System.out.println("update successfully!");
		else
			System.out.println("failure");
	}

	public void finalize() throws IOException {
		ssocket.close();
		socket.close();
	}

	public static void main(String[] args) {
		new Soc(1024).start();
		// new Soc(1025).start();
		// new Soc(1026).start();
	}
}
