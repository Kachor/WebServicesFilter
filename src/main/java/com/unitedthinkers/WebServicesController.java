package com.unitedthinkers;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/WebServices")
public class WebServicesController extends HttpServlet {

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String user = "";
        if (request != null && request.getParameter("name") != null ){
            user = request.getParameter("name");
        }
        response.setContentType("text/html");
        response.setCharacterEncoding("utf-8");
        try {
            response.getWriter().write("<!DOCTYPE html>\n" +
                    "<html>\n" +
                    "<head><title>Filter</title></head>\n" +
                    "<p style=\"text-align: center\"><img src=\"image/success.png\" alt=\"Success\"></p>\n"+
                    "<body bgcolor=\"#fdf5e6\">\n" +
                    "<p style=\"text-align: center\">Congratulations " + user +
                    "! Test completed successfully! <a href=\"index.jsp\">Home</a></p>\n" +
                    "</body></html>");
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        response.setCharacterEncoding("utf-8");
        try {
            response.getWriter().write("<!DOCTYPE html>\n" +
                    "<html>\n" +
                    "<head><title>Filter</title></head>\n" +
                    "<p style=\"text-align: center\"><img src=\"image/success.png\" alt=\"Success\"></p>\n"+
                    "<body bgcolor=\"#fdf5e6\">\n" +
                    "<p style=\"text-align: center\">Test completed successfully! <a href=\"index.jsp\">Home</a></p>\n" +
                    "</body></html>");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
