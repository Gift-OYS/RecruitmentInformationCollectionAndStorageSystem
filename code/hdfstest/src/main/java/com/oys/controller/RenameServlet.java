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

@WebServlet("/RenameServlet")
public class RenameServlet extends HttpServlet{

    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        HdfsService hdfsService = new HdfsServiceImpl();

        String fileName = request.getParameter("fileName");
        String thisPath = (String) request.getParameter("thisPath");
        String filePath1 = thisPath + "/" + fileName;
        String newName = request.getParameter("newName");
        String filePath2 = thisPath + "/" + newName;

        hdfsService.reName(filePath1, filePath2);

        HttpSession session = request.getSession();
        String username=(String)session.getAttribute("username");
        if(thisPath.equals(username)) {
            request.setAttribute("documentList", hdfsService.showFiles(thisPath));
            request.getRequestDispatcher("index.jsp").forward(request, response);
        }else {
            response.sendRedirect(request.getHeader("Referer"));
        }
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        this.doGet(request, response);
    }
}