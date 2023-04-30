import java.sql.*;

public class App {
    public static void main(String[] args) throws Exception {

        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/java","root","my_sql");
            System.out.println("connection successful");

            // Connection conn = null;
            Statement stmt = con.createStatement();
            String sql = "insert into test values (1,'ruby')";
            stmt.executeUpdate(sql);
            System.out.println("selected");

            con.close();

        } catch(Exception e){
            e.printStackTrace();
        }
    }
}