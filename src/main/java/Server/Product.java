package Server;

public class Product {
    private String title;
    private String category;

    public Product(String title, String category) {
        this.title = title;
        this.category = category;

    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCategory() {
        return category;
    }

    public String getTitle() {
        return title;
    }
}
