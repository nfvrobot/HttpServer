package com.chernyavsky.servlet;

import com.chernyavsky.dto.User;
import com.chernyavsky.service.UserService;
import com.chernyavsky.util.JspHelper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/user-info")
public class CustomerServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        if (id!=null) {
            User user = UserService.INSTANCE.getByIdIn(Long.parseLong(id));
            req.setAttribute("user", user);
            getServletContext()
                    .getRequestDispatcher(JspHelper.getPath("user-info"))
                    .forward(req, resp);
        }
    }
}
