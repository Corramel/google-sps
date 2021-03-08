package com.google.sps.servlets;

import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/form-handler")
public class FormHandlerServlet extends HttpServlet {
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException{
        String txtValue = request.getParameter("txt-input");
        System.out.println("You submitted: " + txtValue);

        response.getWriter().println("You submitted: " + txtValue);
        response.sendRedirect("/index.html");
    }
}