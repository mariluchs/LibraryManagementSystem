package iu.piisj.librarymanagementsystem.library.web;

import iu.piisj.librarymanagementsystem.dto.LoanDTO;
import iu.piisj.librarymanagementsystem.library.services.LoanService;
import iu.piisj.librarymanagementsystem.library.repository.UserRepository;
import iu.piisj.librarymanagementsystem.usermanagement.User;
import iu.piisj.librarymanagementsystem.usermanagement.UserRole;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Named
@ViewScoped
public class MyLoansBean implements Serializable {

    @Inject private LoanService loanService;
    @Inject private UserRepository userRepository;

    // Nur für Librarian/Admin relevant (Dropdown)
    private Long selectedUserId;

    public Long getSelectedUserId() { return selectedUserId; }
    public void setSelectedUserId(Long selectedUserId) { this.selectedUserId = selectedUserId; }

    public User getLoggedInUser() {
        return (User) FacesContext.getCurrentInstance()
                .getExternalContext()
                .getSessionMap()
                .get("loggedInUser");
    }

    public boolean isLibrarianOrAdmin() {
        User u = getLoggedInUser();
        return u != null && u.isLibrarianOrAdmin();
    }

    /**
     * Für Librarian/Admin: Liste der Users, die auswählbar sind.
     * Du wolltest: Librarian soll da nicht drin sein -> wir zeigen nur CUSTOMER.
     */
    public List<User> getSelectableUsers() {
        if (!isLibrarianOrAdmin()) return Collections.emptyList();

        return userRepository.findAll().stream()
                .filter(Objects::nonNull)
                .filter(u -> u.getRole() == UserRole.CUSTOMER)
                .toList();
    }

    /**
     * Für Customer: automatisch eigene Loans
     * Für Librarian/Admin: Loans des ausgewählten Customers
     */
    public List<LoanDTO> getMyLoans() {
        User current = getLoggedInUser();
        if (current == null) return Collections.emptyList();

        if (!isLibrarianOrAdmin()) {
            // CUSTOMER: immer eigene Loans
            return loanService.getActiveLoansForUser(current.getId());
        }

        // Librarian/Admin: nur wenn ein Customer ausgewählt wurde
        if (selectedUserId == null) return Collections.emptyList();
        return loanService.getActiveLoansForUser(selectedUserId);
    }

    public String extend(Long loanId) {
        loanService.extendOnce(loanId);
        return null;
    }
}
