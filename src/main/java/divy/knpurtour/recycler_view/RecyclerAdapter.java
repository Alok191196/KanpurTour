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

public class RecyclerAdapter extends  RecyclerView.Adapter<RecyclerAdapter.RecyclerViewHolder>{

    private Context mContext;
    private ArrayList<PlacesData> arrayList ;
    private  ArrayList<PlacesData> arrayList_view;
    public ImageView overflow;
//    Cont

    public RecyclerAdapter(ArrayList<PlacesData> arrayList){
        this.arrayList = arrayList;
    }


    public void go(ArrayList<PlacesData> arrayList_view){

        this.arrayList_view= arrayList_view;

    }
    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout,parent,false);

        RecyclerViewHolder recyclerViewHolder = new RecyclerViewHolder(view);
        return recyclerViewHolder;
    }

    @Override
    public void onBindViewHolder(final RecyclerViewHolder holder, int position) {

        final PlacesData placesData = arrayList.get(position);
        final PlacesData placesData_view = arrayList_view.get(position);

        try {
            Glide.with(mContext).load(placesData.getImage()).into(holder.thumbnail);
        } catch (Exception e) {
            e.printStackTrace();
        }
        holder.f_name.setText(placesData.getTitle());


       // Glide.with(mContext).load(dataprovider.getImg_res()).into(holder.thumbnail);

        holder.thumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(mContext, Place_viewer.class);
                i.putExtra("title",placesData_view.getTitle());
                i.putExtra("address",placesData_view.getAddress());
                i.putExtra("phone",placesData_view.getPhone());
                i.putExtra("image",placesData_view.getImage());
                i.putExtra("website",placesData_view.getWebsite());
                i.putExtra("facility",placesData_view.getFacility());
                i.putExtra("desc",placesData_view.getDescription());
                i.putExtra("lat",placesData_view.getLatitude());
                i.putExtra("long",placesData_view.getLongitude());
                mContext.startActivity(i);
                }
        });
        holder.overflow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupMenu(holder.overflow);
            }
        });
    }


    private void showPopupMenu(View view) {
        // inflate menu
        PopupMenu popup = new PopupMenu(mContext, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_album, popup.getMenu());
        popup.setOnMenuItemClickListener(new MyMenuItemClickListener());
        popup.show();
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }


    public static class  RecyclerViewHolder extends RecyclerView.ViewHolder{
        public ImageView thumbnail,overflow;
        public TextView f_name;

        public RecyclerViewHolder(View view){
            super(view);
            thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
            f_name = (TextView) view.findViewById(R.id.title);
            overflow = (ImageView) view.findViewById(R.id.overflow);

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

    public RecyclerAdapter(Context mContext, ArrayList<PlacesData> arrayList, ArrayList<PlacesData> arrayList_view) {
        this.mContext = mContext;
        this.arrayList = arrayList;
        this.arrayList_view = arrayList_view;
    }
}
