package com.oys.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.oys.service.HdfsService;
import com.oys.service.impl.HdfsServiceImpl;
import org.apache.hadoop.fs.FileStatus;

@WebServlet("/ShowChildDirServlet")
public class ShowChildDirServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		HdfsService hdfsService = new HdfsServiceImpl();

		String filePath = request.getParameter("filePath");
		request.setAttribute("thisPath", filePath);
		System.out.println("看看这" + filePath);
		FileStatus[] documentList = hdfsService.showFiles(filePath + "/");
		request.setAttribute("documentList", documentList);
		request.getRequestDispatcher("index.jsp").forward(request, response);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}
}