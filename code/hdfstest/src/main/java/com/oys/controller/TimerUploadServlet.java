package com.oys.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.oys.service.HdfsService;
import com.oys.service.impl.HdfsServiceImpl;
import org.apache.hadoop.fs.FileStatus;

@WebServlet("/TimerUploadServlet")
public class TimerUploadServlet extends HttpServlet{

    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");

        HdfsService hdfsService = new HdfsServiceImpl();

        String thisPath = (String) request.getParameter("thisPath");

        hdfsService.timerUpload();
        HttpSession session = request.getSession();
        String username = (String)session.getAttribute("username");

        if(thisPath.equals(username)) {
            FileStatus[] list = hdfsService.showFiles(thisPath);
            request.setAttribute("documentList", list);
            request.getRequestDispatcher("index.jsp").forward(request, response);
        }else {
            response.sendRedirect(request.getHeader("Referer"));
        }
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        this.doGet(request, response);
    }
}