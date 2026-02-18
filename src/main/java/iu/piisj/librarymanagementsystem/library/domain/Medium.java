package iu.piisj.librarymanagementsystem.library.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "medium")
public class Medium {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private MediumType type;

    @Column(nullable = false, length = 200)
    private String title;

    @Column(length = 200)
    private String creator; // Autor:in / Herausgeber:in / Regie

    @Column(length = 60)
    private String identifier; // ISBN/ISSN/EAN etc.

    protected Medium() {}

    public Medium(MediumType type, String title, String creator, String identifier) {
        this.type = type;
        this.title = title;
        this.creator = creator;
        this.identifier = identifier;
    }

    public Long getId() { return id; }
    public MediumType getType() { return type; }
    public void setType(MediumType type) { this.type = type; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getCreator() { return creator; }
    public void setCreator(String creator) { this.creator = creator; }

    public String getIdentifier() { return identifier; }
    public void setIdentifier(String identifier) { this.identifier = identifier; }
}
