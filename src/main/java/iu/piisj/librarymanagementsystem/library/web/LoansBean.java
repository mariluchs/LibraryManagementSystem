package iu.piisj.librarymanagementsystem.library.web;

import iu.piisj.librarymanagementsystem.dto.CheckoutDTO;
import iu.piisj.librarymanagementsystem.dto.LoanDTO;
import iu.piisj.librarymanagementsystem.dto.MediumDTO;
import iu.piisj.librarymanagementsystem.library.services.LoanService;
import iu.piisj.librarymanagementsystem.library.services.MediumService;
import iu.piisj.librarymanagementsystem.library.repository.UserRepository;
import iu.piisj.librarymanagementsystem.usermanagement.User;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serializable;
import java.util.List;

@Named
@ViewScoped
public class LoansBean implements Serializable {

    @Inject private LoanService loanService;
    @Inject private MediumService mediumService;
    @Inject private UserRepository userRepository;

    private CheckoutDTO checkout = new CheckoutDTO();

    public CheckoutDTO getCheckout() { return checkout; }

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public List<MediumDTO> getMedia() {
        return mediumService.getAll();
    }

    public List<LoanDTO> getActiveLoans() {
        return loanService.getAllActiveLoans();
    }

    public String checkoutNow() {
        try {
            loanService.checkout(checkout);
            checkout = new CheckoutDTO();
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Fehler", e.getMessage()));
        }
        return null;
    }

    public String returnLoan(Long loanId) {
        loanService.returnLoan(loanId);
        return null;
    }
}
