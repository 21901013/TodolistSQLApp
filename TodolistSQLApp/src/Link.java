import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Link {

	public static void main(String[] args) {
		Connection con = null;
		try {	
			String dbFile = "todolist.db";
			con = DriverManager.getConnection("jdbc:sqlite:" + dbFile);
				
			Statement state = con.createStatement();
			String sql1 = "select * from DoList";
			ResultSet rs1 = state.executeQuery(sql1);
			
			while(rs1.next()) {
				String id = rs1.getString("id");
				String name = rs1.getString("name");
				String regdate = rs1.getString("regdate");
				System.out.println(id + " " + name + " " + regdate);
			}
			state.close();
				
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			if(con != null) {
				try {
					con.close();
				} catch (Exception e) {}
			}
		}
	}
}
