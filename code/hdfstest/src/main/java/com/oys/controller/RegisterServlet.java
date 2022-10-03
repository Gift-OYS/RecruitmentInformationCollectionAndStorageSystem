package com.oys.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.oys.service.HdfsService;
import com.oys.service.UserService;
import com.oys.service.impl.HdfsServiceImpl;
import com.oys.service.impl.UserServiceImpl;

@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		String username = request.getParameter("username");
		String password = request.getParameter("password1");
		String password2 = request.getParameter("password2");
		if(username.equals("")){
			response.getWriter().write("<script>alert('用户名不能为空！');</script>");
			response.getWriter().flush();
			return;
		}
		if(password.equals("")){
			response.getWriter().write("<script>alert('密码不能为空！');</script>");
			response.getWriter().flush();
			return;
		}
		if(!password.equals(password2)){
			response.getWriter().write("<script>alert('两次输入密码不一致！');</script>");
			response.getWriter().flush();
			return;
		}
		UserService userService = new UserServiceImpl();
		HdfsService hdfsService = new HdfsServiceImpl();
		boolean result = userService.addUser(username,password);
		if(result){
			hdfsService.addDirectory(username);
			response.getWriter().write("<script>alert('注册成功！');</script>");
		}else {
			response.getWriter().write("<script>alert('该用户已存在！');</script>");
		}
		response.getWriter().flush();
	}
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
}