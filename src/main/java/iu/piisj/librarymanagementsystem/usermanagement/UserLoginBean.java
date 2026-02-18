package iu.piisj.librarymanagementsystem.usermanagement;

import iu.piisj.librarymanagementsystem.library.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.IOException;
import java.io.Serializable;

@Named
@ViewScoped
public class UserLoginBean implements Serializable {

    @Inject
    private UserRepository userRepository;

    private String username;
    private String password;

    private User loggedInUser;

    // =========================
    // AUTO REDIRECT WENN LOGGED IN
    // =========================
    @PostConstruct
    public void redirectIfLoggedIn() throws IOException {

        Object user = FacesContext.getCurrentInstance()
                .getExternalContext()
                .getSessionMap()
                .get("loggedInUser");

        if (user != null) {
            FacesContext.getCurrentInstance()
                    .getExternalContext()
                    .redirect("index.xhtml");
        }
    }

    // =========================
    // GETTER / SETTER
    // =========================

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public User getLoggedInUser() { return loggedInUser; }

    public boolean isLoggedIn() { return loggedInUser != null; }

    // =========================
    // LOGIN
    // =========================
    public String login() {

        User user = userRepository.findByUsername(username);

        if (user == null || !PWUtil.verifyPassword(password, user.getPassword())) {

            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(
                            FacesMessage.SEVERITY_ERROR,
                            "Login fehlgeschlagen",
                            "Benutzername oder Passwort ist falsch."
                    ));

            return null;
        }

        loggedInUser = user;

        FacesContext.getCurrentInstance()
                .getExternalContext()
                .getSessionMap()
                .put("loggedInUser", loggedInUser);

        return "index.xhtml?faces-redirect=true";
    }

    // =========================
    // LOGOUT
    // =========================
    public String logout() {

        loggedInUser = null;

        FacesContext.getCurrentInstance()
                .getExternalContext()
                .invalidateSession();

        return "login.xhtml?faces-redirect=true";
    }
}
