package iu.piisj.librarymanagementsystem.accessmanagement;

import iu.piisj.librarymanagementsystem.usermanagement.User;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import jakarta.interceptor.AroundInvoke;
import jakarta.interceptor.Interceptor;
import jakarta.interceptor.InvocationContext;

@LibrarianOnly
@Interceptor
public class LibrarianOnlyInterceptor {

    @AroundInvoke
    public Object check(InvocationContext ctx) throws Exception {

        FacesContext fc = FacesContext.getCurrentInstance();
        ExternalContext ec = fc.getExternalContext();

        // User direkt aus Session holen
        User user = (User) ec.getSessionMap().get("loggedInUser");

        // Nicht eingeloggt → Login
        if (user == null) {
            ec.redirect(ec.getRequestContextPath() + "/login.xhtml");
            return null;
        }

        // Keine Librarian Rechte → Error
        if (!user.isLibrarianOrAdmin()) {
            ec.redirect(ec.getRequestContextPath() + "/error.xhtml");
            return null;
        }

        return ctx.proceed();
    }
}
