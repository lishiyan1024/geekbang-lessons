package org.geektimes.projects.user.web.controller;

import org.geektimes.projects.user.domain.User;
import org.geektimes.projects.user.repository.DatabaseUserRepository;
import org.geektimes.projects.user.service.UserService;
import org.geektimes.projects.user.service.UserServiceImpl;
import org.geektimes.projects.user.sql.DBConnectionManager;
import org.geektimes.web.mvc.controller.PageController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

@Path("/user")
public class UserController implements PageController {

    private UserService userService = new UserServiceImpl(new DatabaseUserRepository(new DBConnectionManager()));

    @POST
    @Path("/register")
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Throwable {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        User user = new User();
        user.setName(email);
        user.setEmail(email);
        user.setPassword(password);
        user.setPhoneNumber("135********");

        if (userService.queryUserByNameAndPassword(email,password).isNotExisted()){
            if (userService.register(user)){
                return "register-successful.jsp";
            }
        } else {
            return "login-successful.jsp";
        }
        return "index.jsp";
    }
}
