package divy.knpurtour.recycler_view;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import divy.knpurtour.DataPlaces.PlacesData;
import divy.knpurtour.Place_viewer;
import divy.knpurtour.R;

public class Recycler_product_viewer_item extends  RecyclerView.Adapter<Recycler_product_viewer_item.RecyclerViewHolder> {
    private Context mContext;
    private ArrayList<String> url_list ;
    public ImageView overflow;
//    Cont

    public Recycler_product_viewer_item(ArrayList<String> arrayList){
        this.url_list = url_list;
    }

    public  void go(ArrayList<String> url_list){
        this.url_list= url_list;
    }

    @Override
    public Recycler_product_viewer_item.RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.product_viewer_item,parent,false);

        Recycler_product_viewer_item.RecyclerViewHolder recyclerViewHolder = new Recycler_product_viewer_item.RecyclerViewHolder(view);
        return recyclerViewHolder;
    }

    @Override
    public void onBindViewHolder(final Recycler_product_viewer_item.RecyclerViewHolder holder, int position) {

        final String url = url_list.get(position);

        try {
            Glide.with(mContext).load(url).into(holder.thumbnail);
        } catch (Exception e) {
            e.printStackTrace();
        }
//        holder.f_name.setText(placesData.getTitle());


        // Glide.with(mContext).load(dataprovider.getImg_res()).into(holder.thumbnail);

    }


    private void showPopupMenu(View view) {
        // inflate menu
        PopupMenu popup = new PopupMenu(mContext, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_album, popup.getMenu());
        popup.setOnMenuItemClickListener(new Recycler_product_viewer_item.MyMenuItemClickListener());
        popup.show();
    }

    @Override
    public int getItemCount() {
        return url_list.size();
    }


    public static class  RecyclerViewHolder extends RecyclerView.ViewHolder{
        public ImageView thumbnail;

        public RecyclerViewHolder(View view){
            super(view);
            thumbnail = (ImageView) view.findViewById(R.id.place_image);

        }
    }

    /**
     * Click listener for popup menu items
     */
    class MyMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {

        public MyMenuItemClickListener() {
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.action_add_favourite:
                    Toast.makeText(mContext, "Add to favourite", Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.action_play_next:
                    Toast.makeText(mContext, "Play next", Toast.LENGTH_SHORT).show();
                    return true;
                default:
            }
            return false;
        }
    }

    public Recycler_product_viewer_item(Context mContext, ArrayList<PlacesData> arrayList, ArrayList<PlacesData> arrayList_view) {
        this.mContext = mContext;
        this.url_list = url_list;
    }

}
