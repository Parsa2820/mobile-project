package ir.takhfifat.takhfifat.model;

public class Discount {
    private String title;
    private String description;
    private String imageUrl;
    private String link;
    private String category;
    private String endDate;
    private String code;

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getLink() {
        return link;
    }

    public String getCategory() {
        return category;
    }

    public String getEndDate() {
        return endDate;
    }

    public String getCode() {
        return code;
    }

    public boolean matches(String query) {
        return title.toLowerCase().contains(query.toLowerCase()) || description.toLowerCase().contains(query.toLowerCase());
    }
}
