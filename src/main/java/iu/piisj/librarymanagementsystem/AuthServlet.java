package iu.piisj.librarymanagementsystem.usermanagement;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebFilter("/*")
public class AuthServlet implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        String context = req.getContextPath();
        String uri = req.getRequestURI();
        String path = uri.substring(context.length());

        // ;jsessionid entfernen
        int semi = path.indexOf(';');
        if (semi >= 0) path = path.substring(0, semi);

        // DEBUG
        User sessionUser = (User) req.getSession().getAttribute("loggedInUser");
        String info = (sessionUser == null) ? "user=null"
                : ("user=" + sessionUser.getUsername() + ", role=" + sessionUser.getRole());
        System.out.println("[AuthFilter] path=" + path + " | " + info);

        // JSF / static resources erlauben
        if (path.startsWith("/jakarta.faces.resource/") || path.startsWith("/resources/")) {
            chain.doFilter(request, response);
            return;
        }

        // Root -> login
        if (path.equals("") || path.equals("/")) {
            resp.sendRedirect(context + "/login.xhtml");
            return;
        }

        // Public pages (immer erreichbar)
        if (isPublic(path)) {
            chain.doFilter(request, response);
            return;
        }

        // Ab hier: Login erforderlich
        User user = (User) req.getSession().getAttribute("loggedInUser");
        if (user == null) {
            System.out.println("[AuthFilter] redirect -> /login.xhtml (not logged in)");
            resp.sendRedirect(context + "/login.xhtml");
            return;
        }

        // Seiten für ALLE eingeloggten (Customer + Librarian/Admin)
        if (endsWithPage(path, "media-catalog.xhtml") || endsWithPage(path, "index.xhtml")) {
            chain.doFilter(request, response);
            return;
        }

        // Librarian/Admin-only
        if ((endsWithPage(path, "media.xhtml") || endsWithPage(path, "loans.xhtml"))
                && !user.isLibrarianOrAdmin()) {
            System.out.println("[AuthFilter] redirect -> /error.xhtml (needs librarian/admin)");
            resp.sendRedirect(context + "/error.xhtml");
            return;
        }

        // Customer-only
        if (endsWithPage(path, "my-loans.xhtml") && user.isLibrarianOrAdmin()) {
            System.out.println("[AuthFilter] redirect -> /error.xhtml (customer only)");
            resp.sendRedirect(context + "/error.xhtml");
            return;
        }

        // Default: allow
        chain.doFilter(request, response);
    }

    private boolean isPublic(String path) {
        return endsWithPage(path, "login.xhtml")
                || endsWithPage(path, "register.xhtml")
                || endsWithPage(path, "index.xhtml")
                || endsWithPage(path, "error.xhtml");
    }

    private boolean endsWithPage(String path, String page) {
        return path.equals("/" + page) || path.endsWith("/" + page);
    }
}
