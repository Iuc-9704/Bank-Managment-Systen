import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out =response.getWriter();
		response.setContentType("text/html");
		
		String uname = request.getParameter("uname");
		String pwd = request.getParameter("pwd");
		//String confirm = request.getParameter("cnfm");
		
		String driver = "oracle.jdbc.driver.OracleDriver";
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		try
		{
			Class.forName(driver);
			Connection con = DriverManager.getConnection(url,"training","training");
			
			String query = "select * from register where uname =? and pwd =?";
			PreparedStatement ps = con.prepareStatement(query);
			ps.setString(1, uname);
			ps.setString(2, pwd);
		//	ps.setString(3, confirm);
			
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				//out.println("<h1 style ='color:blue'> Login Success </h1>");
				RequestDispatcher rd = request.getRequestDispatcher("Success.html");
				rd.forward(request, response);
				
			}
			else {
				out.println("<h4 style = color:red'> Login Failed / Try Again </h4>");
				RequestDispatcher rd = request.getRequestDispatcher("Login.html");
				rd.include(request, response);
			}
			
			con.close();
			
		}
		catch(Exception e)
		{
			out.println("<h4 style='color:red'>Exception : " + e.getMessage()+"</h4>");
		}

	}

}