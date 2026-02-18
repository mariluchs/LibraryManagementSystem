package iu.piisj.librarymanagementsystem.dto;

public class CheckoutDTO {
    private Long userId;
    private Long mediumId;

    public CheckoutDTO() {}

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public Long getMediumId() { return mediumId; }
    public void setMediumId(Long mediumId) { this.mediumId = mediumId; }
}
