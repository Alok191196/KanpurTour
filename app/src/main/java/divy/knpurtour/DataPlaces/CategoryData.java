package divy.knpurtour.DataPlaces;

public class CategoryData {
    private String cat_id;
    private String category_name;
    private String category_image;

    public CategoryData(String cat_id, String category_name, String category_image) {
        this.cat_id = cat_id;
        this.category_name = category_name;
        this.category_image = category_image;
    }

    public String getCat_id() {

        return cat_id;
    }

    public void setCat_id(String cat_id) {
        this.cat_id = cat_id;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public String getCategory_image() {
        return category_image;
    }

    public void setCategory_image(String category_image) {
        this.category_image = category_image;
    }

    public CategoryData() {

    }
}
