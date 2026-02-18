package iu.piisj.librarymanagementsystem.library.bootstrap;

import iu.piisj.librarymanagementsystem.library.domain.Loan;
import iu.piisj.librarymanagementsystem.library.domain.Medium;
import iu.piisj.librarymanagementsystem.library.domain.MediumType;
import iu.piisj.librarymanagementsystem.usermanagement.PersistenceManager;
import iu.piisj.librarymanagementsystem.usermanagement.PWUtil;
import iu.piisj.librarymanagementsystem.usermanagement.User;
import iu.piisj.librarymanagementsystem.usermanagement.UserRole;
import jakarta.persistence.EntityManager;

import java.time.LocalDate;

public class LibraryDataSeeder {

    public void seed() {

        System.out.println(">>> [Seeder] START");

        EntityManager em = null;

        try {
            em = PersistenceManager.em();
            em.getTransaction().begin();

            Long mediumCount = em.createQuery("select count(m) from Medium m", Long.class).getSingleResult();
            Long userCount = em.createQuery("select count(u) from User u", Long.class).getSingleResult();

            if (mediumCount > 0 && userCount > 0) {
                System.out.println(">>> [Seeder] SKIP (data already exists)");
                em.getTransaction().commit();
                return;
            }

            System.out.println(">>> [Seeder] INSERT demo data");

            User librarian = new User(
                    "librarian",
                    "Bibliothek",
                    "Admin",
                    "librarian@city.de",
                    "Musterstadt",
                    "DE",
                    PWUtil.hashPassword("test"),
                    UserRole.LIBRARIAN
            );

            User customer1 = new User(
                    "mila",
                    "Nguyen",
                    "Mila",
                    "mila@demo.de",
                    "Berlin",
                    "DE",
                    PWUtil.hashPassword("test"),
                    UserRole.CUSTOMER
            );

            User customer2 = new User(
                    "jonas",
                    "Weber",
                    "Jonas",
                    "jonas@demo.de",
                    "Hamburg",
                    "DE",
                    PWUtil.hashPassword("test"),
                    UserRole.CUSTOMER
            );

            em.persist(librarian);
            em.persist(customer1);
            em.persist(customer2);

            Medium m1 = new Medium(MediumType.BOOK, "Der Prozess", "Franz Kafka", "ISBN 978-3-XX");
            Medium m2 = new Medium(MediumType.BOOK, "Clean Code", "Robert C. Martin", "ISBN 978-0-13-235088-4");
            Medium m3 = new Medium(MediumType.MAGAZINE, "National Geographic 02/2026", "National Geographic", "ISSN 0027-9358");
            Medium m4 = new Medium(MediumType.DVD, "Interstellar", "Christopher Nolan", "EAN 505189030");

            em.persist(m1);
            em.persist(m2);
            em.persist(m3);
            em.persist(m4);

            Loan demoLoan = new Loan(customer1, m1, LocalDate.now(), LocalDate.now().plusDays(14));
            em.persist(demoLoan);

            em.getTransaction().commit();

            System.out.println(">>> [Seeder] DONE");

        } catch (Exception e) {
            System.out.println(">>> [Seeder] ERROR");
            e.printStackTrace();

            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
        } finally {
            if (em != null) em.close();
        }
    }
}
