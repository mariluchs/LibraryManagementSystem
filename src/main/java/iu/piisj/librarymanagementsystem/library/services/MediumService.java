package iu.piisj.librarymanagementsystem.library.services;

import iu.piisj.librarymanagementsystem.dto.MediumCatalogDTO;
import iu.piisj.librarymanagementsystem.dto.MediumDTO;
import iu.piisj.librarymanagementsystem.library.domain.Medium;
import iu.piisj.librarymanagementsystem.library.repository.LoanRepository;
import iu.piisj.librarymanagementsystem.library.repository.MediumRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.time.LocalDate;
import java.util.List;

@ApplicationScoped
public class MediumService {

    @Inject
    private MediumRepository mediumRepository;

    @Inject
    private LoanRepository loanRepository;

    public List<MediumDTO> getAll() {
        return mediumRepository.findAll().stream()
                .map(m -> new MediumDTO(m.getId(), m.getType(), m.getTitle(), m.getCreator(), m.getIdentifier()))
                .toList();
    }

    public List<MediumCatalogDTO> getCatalog() {
        List<Medium> media = mediumRepository.findAll();
        return media.stream().map(m -> {
            boolean loaned = loanRepository.isMediumLoaned(m.getId());
            LocalDate due = loaned ? loanRepository.getActiveDueDateForMedium(m.getId()) : null;

            return new MediumCatalogDTO(
                    m.getId(),
                    m.getType(),
                    m.getTitle(),
                    m.getCreator(),
                    m.getIdentifier(),
                    loaned,
                    due
            );
        }).toList();
    }

    public void create(MediumDTO dto) {
        Medium medium = new Medium(dto.getType(), dto.getTitle(), dto.getCreator(), dto.getIdentifier());
        mediumRepository.save(medium);
    }
}
