package main;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class Login
 */
@WebServlet(
		urlPatterns = { "/Login" }, 
		initParams = { 
				@WebInitParam(name = "dbdriver", value = "org.mariadb.jdbc.Driver"),
				@WebInitParam(name = "dburl", value = "jdbc:mariadb://localhost:3306/travelexperts"),
				@WebInitParam(name = "dbuser", value = "harv"),
				@WebInitParam(name = "dbpassword", value = "password")
		})
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Login() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String userid = request.getParameter("userid");
		String password = request.getParameter("password");
		if ((userid == null) || (password == null) || (userid.equals("")) || (password.equals("")))
		{
			HttpSession session = request.getSession();
			session.setAttribute("message", "Userid and password cannot be empty");
			response.sendRedirect("login.jsp");
		}
		else
		{
			try {
				Class.forName(getInitParameter("dbdriver"));
				Connection conn = DriverManager.getConnection(getInitParameter("dburl"), getInitParameter("dbuser"), getInitParameter("dbpassword"));
				String sql = "select CustLastName,CustomerId from Customers where CustFirstName=?";
				//get a prepared statement to handle the "?" insert point
				PreparedStatement stmt = conn.prepareStatement(sql);
				stmt.setString(1, userid);
				ResultSet rs = stmt.executeQuery();
				if (rs.next())
				{
					if (password.equals(rs.getString(1)))
					{
						HttpSession session = request.getSession();
						session.setAttribute("logged_in", true);
						session.setAttribute("customerId", rs.getString(2));
						response.sendRedirect("secret.jsp");
					}
					else
					{
						HttpSession session = request.getSession();
						session.setAttribute("message", "Invalid userid or password");
						response.sendRedirect("login.jsp");
					}
				}
				else
				{
					HttpSession session = request.getSession();
					session.setAttribute("message", "Invalid userid or password");
					response.sendRedirect("login.jsp");
				}
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
