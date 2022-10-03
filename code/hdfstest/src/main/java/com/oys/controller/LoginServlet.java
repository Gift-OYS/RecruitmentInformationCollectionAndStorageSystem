package com.oys.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.oys.service.HdfsService;
import com.oys.service.UserService;
import com.oys.service.impl.HdfsServiceImpl;
import com.oys.service.impl.UserServiceImpl;
import com.oys.pojo.User;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
 
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		String username = request.getParameter("user");
		String password = request.getParameter("password");
		request.setAttribute("user", username);
		request.setAttribute("password", password);

		UserService userService = new UserServiceImpl();
		HdfsService hdfsService = new HdfsServiceImpl();

		User user = userService.findUser(username);

		if (user != null) {
			if (user.getPassword().equals(password)) {
				HttpSession session = request.getSession();
				session.setAttribute("username", username);
				request.setAttribute("documentList", hdfsService.showFiles(username));
				request.getRequestDispatcher("index.jsp").forward(request, response);
			} else {
				response.getWriter().write("<script>alert('密码错误！');window.location='login.jsp'; window.close();</script></script>");
				response.getWriter().flush();
			}
		} else {
			response.getWriter().write("<script>alert('用户不存在！');window.location='login.jsp'; window.close();</script></script>");
			response.getWriter().flush();
		}
	}
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
}
