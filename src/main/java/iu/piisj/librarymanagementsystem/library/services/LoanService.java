package iu.piisj.librarymanagementsystem.library.services;

import iu.piisj.librarymanagementsystem.dto.CheckoutDTO;
import iu.piisj.librarymanagementsystem.dto.LoanDTO;
import iu.piisj.librarymanagementsystem.library.domain.Loan;
import iu.piisj.librarymanagementsystem.library.domain.Medium;
import iu.piisj.librarymanagementsystem.library.repository.LoanRepository;
import iu.piisj.librarymanagementsystem.library.repository.MediumRepository;
import iu.piisj.librarymanagementsystem.library.repository.UserRepository;
import iu.piisj.librarymanagementsystem.usermanagement.PersistenceManager;
import iu.piisj.librarymanagementsystem.usermanagement.User;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

import java.time.LocalDate;
import java.util.List;

@ApplicationScoped
public class LoanService {

    @Inject private LoanRepository loanRepository;
    @Inject private MediumRepository mediumRepository;
    @Inject private UserRepository userRepository;

    public List<LoanDTO> getAllActiveLoans() {
        return loanRepository.findAllActive().stream().map(this::toDto).toList();
    }

    public List<LoanDTO> getActiveLoansForUser(Long userId) {
        return loanRepository.findActiveByUser(userId).stream().map(this::toDto).toList();
    }

    public void checkout(CheckoutDTO dto) {
        if (dto.getUserId() == null || dto.getMediumId() == null) {
            throw new IllegalArgumentException("User and Medium must be selected");
        }
        if (loanRepository.isMediumLoaned(dto.getMediumId())) {
            throw new IllegalStateException("Medium is already loaned");
        }

        // Wir brauchen user+medium attached in einem EM-Context:
        EntityManager em = PersistenceManager.em();
        try {
            em.getTransaction().begin();

            User user = em.find(User.class, dto.getUserId());
            Medium medium = em.find(Medium.class, dto.getMediumId());

            LocalDate now = LocalDate.now();
            Loan loan = new Loan(user, medium, now, now.plusDays(14));

            em.persist(loan);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public void returnLoan(Long loanId) {
        loanRepository.markReturned(loanId);
    }

    public void extendOnce(Long loanId) {
        loanRepository.extendOnce(loanId, 7); // 7 Tage
    }

    private LoanDTO toDto(Loan l) {
        // Achtung: LAZY -> wir greifen nur auf IDs + String-Felder zu, die i.d.R. safe sind.
        Long userId = l.getUser().getId();
        String userEmail = l.getUser().getEmail();
        Long mediumId = l.getMedium().getId();
        String mediumTitle = l.getMedium().getTitle();

        boolean extendable = l.canExtendOnce();

        return new LoanDTO(
                l.getId(),
                userId, userEmail,
                mediumId, mediumTitle,
                l.getLoanDate(), l.getDueDate(),
                extendable
        );
    }
}
