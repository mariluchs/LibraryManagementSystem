package iu.piisj.librarymanagementsystem.services;

import iu.piisj.librarymanagementsystem.usermanagement.PersistenceManager;
import iu.piisj.librarymanagementsystem.usermanagement.User;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.faces.context.FacesContext;
import jakarta.persistence.EntityManager;

import java.util.List;

@ApplicationScoped
public class UserService {

    // ================================
    // Alle User laden
    // ================================
    public List<User> findAll() {
        EntityManager em = PersistenceManager.em();
        try {
            return em.createQuery("SELECT u FROM User u ORDER BY u.id ASC", User.class)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    // ================================
    // User löschen (mit Transaction!)
    // ================================
    public void deleteUser(Long id) {
        EntityManager em = PersistenceManager.em();
        try {
            em.getTransaction().begin();

            User user = em.find(User.class, id);
            if (user != null) {
                em.remove(user);
                System.out.println("[UserService] Deleted user: " + user.getUsername());
            }

            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    // ================================
    // Eingeloggten User aus Session holen
    // ================================
    public User getLoggedInUser() {
        FacesContext fc = FacesContext.getCurrentInstance();
        if (fc == null) return null;

        Object obj = fc.getExternalContext().getSessionMap().get("loggedInUser");
        return (obj instanceof User) ? (User) obj : null;
    }
}
