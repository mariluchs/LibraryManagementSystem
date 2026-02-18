package iu.piisj.librarymanagementsystem.usermanagement;

import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;

import java.io.Serializable;

@Named
@SessionScoped
public class AuthBean implements Serializable {

    public User getUser() {
        return (User) FacesContext.getCurrentInstance()
                .getExternalContext()
                .getSessionMap()
                .get("loggedInUser");
    }

    public boolean isLoggedIn() {
        return getUser() != null;
    }

    public boolean isLibrarianOrAdmin() {
        User u = getUser();
        return u != null && u.isLibrarianOrAdmin();
    }

    // ✅ damit layout.xhtml rendered="#{authBean.customer}" funktioniert
    public boolean isCustomer() {
        User u = getUser();
        return u != null && u.getRole() == UserRole.CUSTOMER;
    }

    public String logout() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "/login.xhtml?faces-redirect=true";
    }
}
