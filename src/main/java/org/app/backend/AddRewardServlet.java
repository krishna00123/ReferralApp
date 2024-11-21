package org.app.backend;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;

@WebServlet("/addReward")
public class AddRewardServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String rewardCode = request.getParameter("rewardCode");
        // Process the reward code
        Connection con = null;
        PreparedStatement pstmt = null;	

        String query = "UPDATE Users SET rewardPoints = rewardPoints + 50 WHERE referralCode = ?";  // Example logic

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/referralDB", "root", "admin");

            pstmt = con.prepareStatement(query);
            pstmt.setString(1, rewardCode);
            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                response.getWriter().println("Reward added successfully!");
            } else {
              response.sendRedirect("home.html");
              
            }

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace(response.getWriter());
        } finally {
            try {
                if (pstmt != null) pstmt.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace(response.getWriter());
            }
        }
    }
}
