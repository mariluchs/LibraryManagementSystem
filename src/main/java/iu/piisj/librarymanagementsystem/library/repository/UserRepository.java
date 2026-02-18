package iu.piisj.librarymanagementsystem.library.repository;

import iu.piisj.librarymanagementsystem.usermanagement.PersistenceManager;
import iu.piisj.librarymanagementsystem.usermanagement.User;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;

import java.util.List;

@ApplicationScoped
public class UserRepository {

    public List<User> findAll() {
        EntityManager em = PersistenceManager.em();
        try {
            return em.createQuery("select u from User u order by u.email asc", User.class)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public User findById(Long id) {
        EntityManager em = PersistenceManager.em();
        try {
            return em.find(User.class, id);
        } finally {
            em.close();
        }
    }

    public boolean emailExists(String email) {
        EntityManager em = PersistenceManager.em();
        try {
            Long cnt = em.createQuery("select count(u) from User u where u.email = :email", Long.class)
                    .setParameter("email", email)
                    .getSingleResult();
            return cnt != null && cnt > 0;
        } finally {
            em.close();
        }
    }

    public User findByEmail(String email) {
        EntityManager em = PersistenceManager.em();
        try {
            List<User> result = em.createQuery("select u from User u where u.email = :email", User.class)
                    .setParameter("email", email)
                    .getResultList();
            return result.isEmpty() ? null : result.get(0);
        } finally {
            em.close();
        }
    }

    public User findByUsername(String username) {
        var em = PersistenceManager.em();
        try {
            return em.createQuery(
                            "SELECT u FROM User u WHERE u.username = :username", User.class)
                    .setParameter("username", username)
                    .getResultStream()
                    .findFirst()
                    .orElse(null);
        } finally {
            em.close();
        }
    }


    public void save(User user) {
        EntityManager em = PersistenceManager.em();
        try {
            em.getTransaction().begin();
            em.persist(user);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }
}
