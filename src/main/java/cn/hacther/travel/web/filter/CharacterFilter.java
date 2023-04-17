package cn.hacther.travel.web.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebFilter(filterName = "CharacterFilter", value = "/*")
public class CharacterFilter implements Filter {
    public void init(FilterConfig config) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        //将父接口转为子接口
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse rep = (HttpServletResponse) response;

        //获取请求方法
        String method = req.getMethod();

        //解决Post请求中文数据乱码问题
        if(method.equalsIgnoreCase("post")){
            req.setCharacterEncoding("UTF-8");
        }
        //处理响应乱码
        String s = req.getRequestURL().toString();
        if(!(s.contains("css") || s.contains("js"))){
            rep.setContentType("text/html;charset=UTF-8");
        }
        chain.doFilter(req,rep);
    }

    public void destroy() {

    }
}
