package divy.knpurtour.DataPlaces;

public class PlacesData {
    private String id;
    private String title;
    private String address;
    private String phone;
    private String website;
    private String facility;
    private String image;
    private String description;
    private String latitude;
    private String longitude;
    private String date;
    public PlacesData() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getFacility() {
        return facility;
    }

    public void setFacility(String facility) {
        this.facility = facility;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public PlacesData(String id, String title, String address, String phone, String website, String facility, String image, String description, String latitude, String longitude, String date) {
        this.id = id;
        this.title = title;
        this.address = address;
        this.phone = phone;
        this.website = website;
        this.facility = facility;
        this.image = image;
        this.description = description;
        this.latitude = latitude;
        this.longitude = longitude;
        this.date = date;
    }
    public PlacesData(String title,String image) {
        this.title = title;
        this.image = image;

    }
}
