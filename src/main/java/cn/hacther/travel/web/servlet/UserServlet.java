package cn.hacther.travel.web.servlet;

import cn.hacther.travel.domain.ResultInfo;
import cn.hacther.travel.domain.User;
import cn.hacther.travel.service.UserService;
import cn.hacther.travel.service.impl.UserServiceImpl;
import cn.hacther.travel.util.MailUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.apache.commons.beanutils.BeanUtils;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

@WebServlet(name = "UserServlet", value = "/user/*")
public class  UserServlet extends BaseServlet {
    private UserService service = new UserServiceImpl();

    /**
     * 账户激活功能
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void activeUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String code = request.getParameter("code");
        if(code != null){
            if(service.active(code)){
                response.sendRedirect(request.getContextPath() + "/activeSuccess.html");
            }else{
                response.sendRedirect(request.getContextPath() + "/activeFail.html");
            }
        }
    }

    /**
     * 登录功能
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ResultInfo info = new ResultInfo();
        HttpSession session = request.getSession();

        String check = request.getParameter("check");
        String checkcode_server = (String)session.getAttribute("CHECKCODE_SERVER");
        session.removeAttribute("CHECKCODE_SERVER");

        if(checkcode_server == null || !(checkcode_server.equalsIgnoreCase(check))){
            info.setFlag(false);
            if(checkcode_server == null){
                info.setErrorMsg("验证码失效！请刷新！");
            }else {
                info.setErrorMsg("验证码错误！");
            }
            writeValue(info,response);
            return;
        }

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        User user = service.login(username, password);

        if(user != null){
            if(user.getStatus().equalsIgnoreCase("y")){
                try {
                    session.setAttribute("user",user);
                    info.setFlag(true);
                    writeValue(info,response);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }else {
                info.setFlag(false);
                info.setErrorMsg("用户未激活！");
                writeValue(info,response);
            }
        }else {
            info.setFlag(false);
            info.setErrorMsg("用户名或密码错误！");
            writeValue(info,response);
        }
    }

    /**
     * 登录状态查询功能
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void loginStatus(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        ResultInfo info = new ResultInfo();

        User user = (User) session.getAttribute("user");
        if(user != null){
            info.setFlag(true);
            info.setData(user);
            writeValue(info,response);
        }else {
            info.setFlag(false);
            writeValue(info,response);
        }
    }

    /**
     * 账号登出功能
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void logout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getSession().invalidate();
        response.sendRedirect(request.getContextPath() + "/login.html");
    }

    /**
     * 注册功能
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void registerUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String check = request.getParameter("check");
        HttpSession session = request.getSession();
        String checkcode_server = (String)session.getAttribute("CHECKCODE_SERVER");
        session.removeAttribute("CHECKCODE_SERVER");

        if(checkcode_server == null || !(checkcode_server.equalsIgnoreCase(check))){
            ResultInfo info = new ResultInfo();
            info.setFlag(false);
            if(checkcode_server == null){
                info.setErrorMsg("验证码失效！请刷新！");
            }else {
                info.setErrorMsg("验证码错误！");
            }
            writeValue(info,response);
            return;
        }

        Map<String, String[]> map = request.getParameterMap();
        User user = new User();

        try {
            BeanUtils.populate(user,map);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        }

        int register = 0;
        try {
            register = service.register(user);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        ResultInfo info = new ResultInfo();
        if(register == 1){
            info.setFlag(true);
            MailUtils.sendMail(user.getEmail(),MailUtils.msgBuilder(user.getCode(), user.getUsername(), user.getEmail()),"激活您的账户以登录");
        }else{
            info.setFlag(false);
            info.setErrorMsg("用户已存在！");
        }

        writeValue(info,response);
    }
}
