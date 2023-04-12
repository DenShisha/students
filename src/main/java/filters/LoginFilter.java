package filters;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Locale;

public class LoginFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain filterChain) throws IOException, ServletException {
        String url = ((HttpServletRequest) req).getRequestURI().toLowerCase();

        //Пропускаем запрос, если запрошены java функции, стили или шрифты.
        if (url.endsWith(".js") || url.endsWith(".css") || url.endsWith("ttf")) {
            filterChain.doFilter(req, resp);
            return;
        }

        //Пропускаем неавторизованного пользователя на страницу с логином
        Object isAuthorised = ((HttpServletRequest) req).getSession().getAttribute("isAuthorised");

        if (isAuthorised == null && url.endsWith("/login")) {
            filterChain.doFilter(req, resp);
            return;
        }

        //Пропускаем все запросы от авториз-го польз-ля
        if (isAuthorised != null) {
            filterChain.doFilter(req, resp);
            return;
        }

        ((HttpServletResponse) resp).sendRedirect("/login");
    }

    @Override
    public void destroy() {

    }
}
