package iu.piisj.librarymanagementsystem.dto;

import java.time.LocalDate;

public class LoanDTO {
    private Long id;

    private Long userId;
    private String userEmail;

    private Long mediumId;
    private String mediumTitle;

    private LocalDate loanDate;
    private LocalDate dueDate;

    private boolean extendable;

    public LoanDTO() {}

    public LoanDTO(Long id, Long userId, String userEmail, Long mediumId, String mediumTitle,
                   LocalDate loanDate, LocalDate dueDate, boolean extendable) {
        this.id = id;
        this.userId = userId;
        this.userEmail = userEmail;
        this.mediumId = mediumId;
        this.mediumTitle = mediumTitle;
        this.loanDate = loanDate;
        this.dueDate = dueDate;
        this.extendable = extendable;
    }

    public Long getId() { return id; }
    public Long getUserId() { return userId; }
    public String getUserEmail() { return userEmail; }
    public Long getMediumId() { return mediumId; }
    public String getMediumTitle() { return mediumTitle; }
    public LocalDate getLoanDate() { return loanDate; }
    public LocalDate getDueDate() { return dueDate; }
    public boolean isExtendable() { return extendable; }
}
