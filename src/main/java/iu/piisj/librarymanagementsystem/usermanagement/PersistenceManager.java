package iu.piisj.librarymanagementsystem.usermanagement;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public final class PersistenceManager {

    private static final String PU_NAME = "librarymanagementsystemPU";
    private static final EntityManagerFactory EMF =
            Persistence.createEntityManagerFactory(PU_NAME);

    private PersistenceManager() {}

    public static EntityManager em() {
        return EMF.createEntityManager();
    }
}
