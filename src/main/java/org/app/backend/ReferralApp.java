package org.app.backend;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.SecureRandom;
import java.sql.*;

/**
 * Servlet implementation class ReferralApp
 */
@WebServlet("/login")
public class ReferralApp extends HttpServlet {
	private static final long serialVersionUID = 1L;

	
	    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
	    private static final int CODE_LENGTH = 8;

	    // Method to generate a random referral code
	    private String generateReferralCode() {
	        SecureRandom random = new SecureRandom();
	        StringBuilder code = new StringBuilder(CODE_LENGTH);
	        for (int i = 0; i < CODE_LENGTH; i++) {
	            code.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
	        }
	        return code.toString();
	    }
	 
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		
		
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        Connection con = null;
        PreparedStatement checkEmailStmt = null;
        PreparedStatement insertUserStmt = null;
        ResultSet rs = null;
       String referralCode = generateReferralCode();
        String checkEmailQuery = "SELECT email FROM Users WHERE email = ?";
        String insertUserQuery = "INSERT INTO Users (name, email, password, referralCode) VALUES (?, ?, ?, ?)";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/referralDB", "root", "admin");

            // Check if email already exists
            checkEmailStmt = con.prepareStatement(checkEmailQuery);
            checkEmailStmt.setString(1, email);
            rs = checkEmailStmt.executeQuery();

            if (rs.next()) {
            	  response.setContentType("text/html");
                  PrintWriter out = response.getWriter();
                  out.println("<html>");
                  out.println("<head>");
                  out.println("<script type='text/javascript'>");
                  out.println("alert('Email already exists! Please try another email.');");
                  out.println("location='index.html';");
                  // Redirect back to the registration page
                    out.println("</script>");
                  out.println("</head>");
                  out.println("<body></body>");
                  out.println("</html>");
            	 
            } else {
                // Insert new user
                insertUserStmt = con.prepareStatement(insertUserQuery);
                insertUserStmt.setString(1, name);
                insertUserStmt.setString(2, email);
                insertUserStmt.setString(3, password);
                insertUserStmt.setString(4, referralCode);
                insertUserStmt.executeUpdate();
                
             // Example snippet within your servlet
                 referralCode = generateReferralCode();
                int rewardPoints = calculateRewardPoints(); // Assuming a method to calculate points

                response.sendRedirect("home.html?referralCode=" + referralCode + "&rewardPoints=" + rewardPoints);

                
                

            }
			
			 
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        finally {
            try {
                if (rs != null) rs.close();
                if (checkEmailStmt != null) checkEmailStmt.close();
                if (insertUserStmt != null) insertUserStmt.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace(response.getWriter());
            }
        }

	}
	private int calculateRewardPoints() {
		// TODO Auto-generated method stub
		return 0;
	}

}
