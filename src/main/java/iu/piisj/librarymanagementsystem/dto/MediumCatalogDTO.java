package iu.piisj.librarymanagementsystem.dto;

import iu.piisj.librarymanagementsystem.library.domain.MediumType;

import java.time.LocalDate;

public class MediumCatalogDTO {
    private Long id;
    private MediumType type;
    private String title;
    private String creator;
    private String identifier;

    private boolean loaned;
    private LocalDate dueDate; // null wenn verfügbar

    public MediumCatalogDTO() {}

    public MediumCatalogDTO(Long id, MediumType type, String title, String creator, String identifier,
                            boolean loaned, LocalDate dueDate) {
        this.id = id;
        this.type = type;
        this.title = title;
        this.creator = creator;
        this.identifier = identifier;
        this.loaned = loaned;
        this.dueDate = dueDate;
    }

    public Long getId() { return id; }
    public MediumType getType() { return type; }
    public String getTitle() { return title; }
    public String getCreator() { return creator; }
    public String getIdentifier() { return identifier; }

    public boolean isLoaned() { return loaned; }
    public LocalDate getDueDate() { return dueDate; }
}
