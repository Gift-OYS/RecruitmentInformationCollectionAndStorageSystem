package com.oys.controller;

import java.io.IOException;
import java.io.InputStream;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import com.oys.service.HdfsService;
import com.oys.service.impl.HdfsServiceImpl;

@WebServlet("/UploadServlet")
@MultipartConfig
public class UploadServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		HdfsService hdfsService = new HdfsServiceImpl();

	    Part part = request.getPart("upFile");
        String disposition = part.getHeader("Content-Disposition");
        String fileName = disposition.substring(disposition.lastIndexOf("=") + 2, disposition.length() - 1);
        String thisPath = (String) request.getParameter("thisPath");
		String upPath = thisPath + "/" + fileName;
        InputStream in = part.getInputStream();

		try {
			hdfsService.upload(upPath, in);
		} catch (Exception e) {
			e.printStackTrace();
		}

		HttpSession session = request.getSession();
		String username=(String)session.getAttribute("username");
		if(thisPath.equals(username)) {
			request.setAttribute("documentList", hdfsService.showFiles(thisPath));
			request.getRequestDispatcher("index.jsp").forward(request, response);
		}else {
			response.sendRedirect(request.getHeader("Referer"));
		}

        in.close();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}
}
