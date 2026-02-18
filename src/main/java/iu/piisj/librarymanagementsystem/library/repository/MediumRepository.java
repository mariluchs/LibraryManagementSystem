package iu.piisj.librarymanagementsystem.library.repository;

import iu.piisj.librarymanagementsystem.library.domain.Medium;
import iu.piisj.librarymanagementsystem.usermanagement.PersistenceManager;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;

import java.util.List;

@ApplicationScoped
public class MediumRepository {

    public List<Medium> findAll() {
        EntityManager em = PersistenceManager.em();
        try {
            // nach ID aufsteigend (1,2,3,...)
            return em.createQuery("select m from Medium m order by m.id asc", Medium.class)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public Medium findById(Long id) {
        EntityManager em = PersistenceManager.em();
        try {
            return em.find(Medium.class, id);
        } finally {
            em.close();
        }
    }

    public void save(Medium medium) {
        EntityManager em = PersistenceManager.em();
        try {
            em.getTransaction().begin();
            em.persist(medium);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }
}
