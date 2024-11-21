/*package org.app.backend;
import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
@WebServlet("/searchReward")
public class SearchRewardServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String code=req.getParameter("refcode");
		resp.getWriter().println("Reward added successfully!"+code);
		resp.sendRedirect("index.html");
		 
	
	}
	
}
*/
package org.app.backend;

//import jakarta.security.auth.message.callback.PrivateKeyCallback.Request;
//import jakarta.security.auth.message.callback.PrivateKeyCallback.Request;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

//import org.apache.catalina.connector.Response;

@WebServlet("/searchReferal")
public class SearchRewardServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String rewardCode123=request.getParameter("rcode");
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        String query = "SELECT * from users where referralCode=?";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/referralDB", "root", "admin");

            pstmt = con.prepareStatement(query);
            pstmt.setString(1, rewardCode123);
            
            rs = pstmt.executeQuery();

            if (rs.next()) {
            		String n = rs.getString("name");
            		String rp=rs.getString("rewardPoints");
            		String email=rs.getString("email");
            	 PrintWriter out = response.getWriter();
            	 response.setContentType("text/html");
            	 out.println("<html><body>");
                 out.println("<h3>User Information</h3>");
                 out.println("<p>Name: "+n+" <br>Points: "+rp+"<br>Email: "+email+"</p>");
                 out.println("</body></html>");
                 
              
            } else {
            	PrintWriter out1 = response.getWriter();
           	 response.setContentType("text/html");
           	 out1.println("<html><body>");
                out1.println("<h3>Error Information</h3>");
               
                out1.println("</body></html>");
                
               
            }

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace(response.getWriter());
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace(response.getWriter());
            }
        }
    }
}
