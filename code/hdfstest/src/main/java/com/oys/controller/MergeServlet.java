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

@WebServlet("/MergeServlet")
public class MergeServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        HdfsService hdfsService = new HdfsServiceImpl();

        String thisPath = request.getParameter("thisPath");
        String dirName = request.getParameter("dirName");
        String filePath1 = thisPath + "/" + dirName;
        String newMergeName = request.getParameter("newMergePath");
        String filePath2 = thisPath + "/" + newMergeName;

        hdfsService.merge(filePath1, filePath2);

        HttpSession session = request.getSession();
        String username=(String)session.getAttribute("username");
        if(thisPath.equals(username)) {
            request.setAttribute("documentList", hdfsService.showFiles(thisPath));
            request.getRequestDispatcher("index.jsp").forward(request, response);
        }else {
            response.sendRedirect(request.getHeader("Referer"));
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
