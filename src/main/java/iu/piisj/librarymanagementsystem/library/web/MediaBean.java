package iu.piisj.librarymanagementsystem.library.web;

import iu.piisj.librarymanagementsystem.dto.MediumDTO;
import iu.piisj.librarymanagementsystem.library.domain.MediumType;
import iu.piisj.librarymanagementsystem.library.services.MediumService;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serializable;
import java.util.List;

@Named
@ViewScoped
public class MediaBean implements Serializable {

    @Inject
    private MediumService mediumService;

    private MediumDTO form = new MediumDTO();

    public List<MediumDTO> getMedia() {
        return mediumService.getAll();
    }

    public MediumDTO getForm() { return form; }

    public MediumType[] getTypes() {
        return MediumType.values();
    }

    public String create() {
        mediumService.create(form);
        form = new MediumDTO(); // reset
        return null;
    }
}
