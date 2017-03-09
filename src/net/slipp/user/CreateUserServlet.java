package net.slipp.user;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/users/create")
public class CreateUserServlet extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String userId = request.getParameter("userId");	//4자 이상 ~ 12자 이하. 영문사/숫자만 허용
		String password = request.getParameter("password");
		String name = request.getParameter("name");		//2자 이상 ! 10자 이하
		String email = request.getParameter("email");

		User user = new User(userId, password, name, email);
		UserDAO userDAO = new UserDAO();
		try {
			userDAO.addUser(user);
		} catch (SQLException e) {
		}
		
		response.sendRedirect("/");
	}
}
