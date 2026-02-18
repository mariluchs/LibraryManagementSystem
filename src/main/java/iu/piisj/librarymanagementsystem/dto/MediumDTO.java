package iu.piisj.librarymanagementsystem.dto;

import iu.piisj.librarymanagementsystem.library.domain.MediumType;

public class MediumDTO {

    private Long id;
    private MediumType type;
    private String title;
    private String creator;
    private String identifier;

    // Für Catalog:
    private boolean available = true;

    public MediumDTO() {}

    public MediumDTO(Long id, MediumType type, String title, String creator, String identifier) {
        this.id = id;
        this.type = type;
        this.title = title;
        this.creator = creator;
        this.identifier = identifier;
    }

    public MediumDTO(Long id, MediumType type, String title, String creator, String identifier, boolean available) {
        this.id = id;
        this.type = type;
        this.title = title;
        this.creator = creator;
        this.identifier = identifier;
        this.available = available;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public MediumType getType() { return type; }
    public void setType(MediumType type) { this.type = type; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getCreator() { return creator; }
    public void setCreator(String creator) { this.creator = creator; }

    public String getIdentifier() { return identifier; }
    public void setIdentifier(String identifier) { this.identifier = identifier; }

    // JSF: #{m.available}
    public boolean isAvailable() { return available; }
    public boolean getAvailable() { return available; } // damit EL safe ist
    public void setAvailable(boolean available) { this.available = available; }
}
