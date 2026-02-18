package iu.piisj.librarymanagementsystem.library.domain;

import iu.piisj.librarymanagementsystem.usermanagement.User;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "loan",
        indexes = {
                @Index(name = "idx_loan_user", columnList = "user_id"),
                @Index(name = "idx_loan_medium", columnList = "medium_id")
        })
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Wir verwenden bestehende User-Entity
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "medium_id")
    private Medium medium;

    @Column(nullable = false)
    private LocalDate loanDate;

    @Column(nullable = false)
    private LocalDate dueDate;

    private LocalDate returnedAt; // null = aktiv

    @Column(nullable = false)
    private int extensionCount;

    protected Loan() {}

    public Loan(User user, Medium medium, LocalDate loanDate, LocalDate dueDate) {
        this.user = user;
        this.medium = medium;
        this.loanDate = loanDate;
        this.dueDate = dueDate;
        this.extensionCount = 0;
    }

    public boolean isActive() { return returnedAt == null; }
    public boolean canExtendOnce() { return isActive() && extensionCount < 1; }

    public void extendDays(int days) {
        if (!canExtendOnce()) throw new IllegalStateException("Extension not allowed");
        this.dueDate = this.dueDate.plusDays(days);
        this.extensionCount++;
    }

    public void markReturned(LocalDate date) {
        this.returnedAt = date;
    }

    public Long getId() { return id; }

    public User getUser() { return user; }
    public Medium getMedium() { return medium; }

    public LocalDate getLoanDate() { return loanDate; }
    public LocalDate getDueDate() { return dueDate; }
    public LocalDate getReturnedAt() { return returnedAt; }
    public int getExtensionCount() { return extensionCount; }
}
