package iu.piisj.librarymanagementsystem.library.repository;

import iu.piisj.librarymanagementsystem.library.domain.Loan;
import iu.piisj.librarymanagementsystem.usermanagement.PersistenceManager;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;

import java.time.LocalDate;
import java.util.List;

@ApplicationScoped
public class LoanRepository {

    public Loan findById(Long id) {
        EntityManager em = PersistenceManager.em();
        try {
            return em.find(Loan.class, id);
        } finally {
            em.close();
        }
    }

    public void save(Loan loan) {
        EntityManager em = PersistenceManager.em();
        try {
            em.getTransaction().begin();
            em.persist(loan);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public List<Loan> findAllActive() {
        EntityManager em = PersistenceManager.em();
        try {
            return em.createQuery(
                            "select l from Loan l " +
                                    "join fetch l.user " +
                                    "join fetch l.medium " +
                                    "where l.returnedAt is null " +
                                    "order by l.dueDate asc",
                            Loan.class)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public List<Loan> findActiveByUser(Long userId) {
        EntityManager em = PersistenceManager.em();
        try {
            return em.createQuery(
                            "select l from Loan l " +
                                    "join fetch l.user " +
                                    "join fetch l.medium " +
                                    "where l.returnedAt is null and l.user.id = :uid " +
                                    "order by l.dueDate asc",
                            Loan.class)
                    .setParameter("uid", userId)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public boolean isMediumLoaned(Long mediumId) {
        EntityManager em = PersistenceManager.em();
        try {
            Long cnt = em.createQuery(
                            "select count(l) from Loan l where l.returnedAt is null and l.medium.id = :mid",
                            Long.class)
                    .setParameter("mid", mediumId)
                    .getSingleResult();
            return cnt != null && cnt > 0;
        } finally {
            em.close();
        }
    }

    public LocalDate getActiveDueDateForMedium(Long mediumId) {
        EntityManager em = PersistenceManager.em();
        try {
            List<LocalDate> dates = em.createQuery(
                            "select l.dueDate from Loan l where l.returnedAt is null and l.medium.id = :mid",
                            LocalDate.class)
                    .setParameter("mid", mediumId)
                    .setMaxResults(1)
                    .getResultList();
            return dates.isEmpty() ? null : dates.get(0);
        } finally {
            em.close();
        }
    }

    public void extendOnce(Long loanId, int days) {
        EntityManager em = PersistenceManager.em();
        try {
            em.getTransaction().begin();
            Loan loan = em.find(Loan.class, loanId);
            loan.extendDays(days);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public void markReturned(Long loanId) {
        EntityManager em = PersistenceManager.em();
        try {
            em.getTransaction().begin();
            Loan loan = em.find(Loan.class, loanId);
            loan.markReturned(LocalDate.now());
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }
}
