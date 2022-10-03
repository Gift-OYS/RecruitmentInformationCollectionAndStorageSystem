package com.oys.controller;

import java.io.IOException;
import java.io.InputStream;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.oys.service.HdfsService;
import com.oys.service.impl.HdfsServiceImpl;

@WebServlet("/DownloadServlet")
public class DownloadServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		HdfsService hdfsService = new HdfsServiceImpl();

		String result = request.getParameter("result");
		String fileName = request.getParameter("fileName");

		String filePath = result + "/" + fileName;
		try {
			InputStream in = hdfsService.getInputStream(filePath);
			byte[] b = new byte[in.available()];
			in.read(b);
			response.setCharacterEncoding("utf-8");
			response.setHeader("Content-Disposition","attachment; filename=" + fileName + "");
			ServletOutputStream out = response.getOutputStream();
			out.write(b);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}
}
