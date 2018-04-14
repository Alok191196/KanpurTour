package divy.knpurtour;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.rubensousa.gravitysnaphelper.GravitySnapHelper;

import java.util.ArrayList;

import divy.knpurtour.recycler_view.Recycler_product_viewer_item;

public class Place_viewer extends AppCompatActivity {
    String destinationLatitude;
    String destinationLongitude;

    Button navigate;
    ImageView place_imag;
    TextView place_title,place_address,place_phone,place_website,place_facility,place_desc;
    RecyclerView recyclerView;
    Recycler_product_viewer_item adapter;

    ArrayList<String> url_list = new ArrayList<>();

    String Image_url = "https://city-app.000webhostapp.com";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_viewer);

      //  place_imag = findViewById(R.id.place_image);
        place_title = findViewById(R.id.title_place);
        place_address = findViewById(R.id.address_place);
        place_phone = findViewById(R.id.phone_place);
        place_website = findViewById(R.id.website_place);
        place_facility = findViewById(R.id.facility_place);
        place_desc = findViewById(R.id.description_place);
        navigate = findViewById(R.id.get_direction);

        String title = getIntent().getStringExtra("title");
        String address = getIntent().getStringExtra("address");
        String phone = getIntent().getStringExtra("phone");
        String website = getIntent().getStringExtra("website");
        String facility = getIntent().getStringExtra("facility");
        String desc = getIntent().getStringExtra("desc");
        String image = getIntent().getStringExtra("image");
        destinationLatitude = getIntent().getStringExtra("lat");
        destinationLongitude = getIntent().getStringExtra("long");

        final String image_final[] = image.split(",");
        for(int i = 0; i < image_final.length;i++){
            url_list.add(Image_url + image_final[i]);
        }

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_single);

        adapter = new Recycler_product_viewer_item(url_list);
        recyclerView.setHasFixedSize(true);

        SnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.HORIZONTAL, false));

        SnapHelper snapHelperStart = new GravitySnapHelper(Gravity.START);
        snapHelperStart.attachToRecyclerView(recyclerView);
        recyclerView.setAdapter(adapter);
        adapter.go(url_list);



        try {
            Glide.with(getApplicationContext()).load(Image_url + image_final[0]).into(place_imag);
        } catch (Exception e) {
            e.printStackTrace();
        }

        place_title.setText(title);
        place_address.setText(address);
        place_desc.setText(desc);
        place_facility.setText(facility);
        place_phone.setText(phone);
        place_website.setText(website);

    }

    public void open_map(View view) {
        String uri = "http://maps.google.com/maps?daddr=" + destinationLatitude + "," + destinationLongitude;
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
        startActivity(intent);
    }
}
