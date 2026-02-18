package iu.piisj.librarymanagementsystem.library.web;

import iu.piisj.librarymanagementsystem.dto.MediumCatalogDTO;
import iu.piisj.librarymanagementsystem.library.services.MediumService;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serializable;
import java.util.List;

@Named
@ViewScoped
public class MediaCatalogBean implements Serializable {

    @Inject
    private MediumService mediumService;

    public List<MediumCatalogDTO> getMedia() {
        return mediumService.getCatalog();
    }
}
