package divy.knpurtour.recycler_view;

public class Dataprovider {
    private int img_res;
    private String res_name;

    public Dataprovider(){}

    public int getImg_res() {
        return img_res;
    }

    public void setImg_res(int img_res) {
        this.img_res = img_res;
    }

    public String getRes_name() {
        return res_name;
    }

    public void setRes_name(String res_name) {
        this.res_name = res_name;
    }

    public Dataprovider(int img_res, String res_name) {

        this.img_res = img_res;
        this.res_name = res_name;
    }
}
