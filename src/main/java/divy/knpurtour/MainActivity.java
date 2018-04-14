package divy.knpurtour;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;


import butterknife.ButterKnife;
import divy.knpurtour.DataPlaces.PlacesData;
import divy.knpurtour.recycler_view.RecyclerAdapter;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity


        implements NavigationView.OnNavigationItemSelectedListener {

    private Context mContext;
    private ArrayList<PlacesData> placeList;
    RecyclerView recyclerView;
    RecyclerAdapter adapter;

    private ProgressBar mProgressBar;
    private PlacesData mPlacesdata;

    private ArrayList<String> cat_menu = new ArrayList<>();
    private ArrayList<Integer> cat_id = new ArrayList<>();
    private ArrayList<String> cat_image = new ArrayList<>();

    ImageView backdrop;



    public static final String TAG = MainActivity.class.getSimpleName();

    ArrayList<PlacesData> arrayList = new ArrayList<PlacesData>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initCollapsingToolbar();
        mContext = getBaseContext();
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        backdrop = (ImageView) findViewById(R.id.backdrop);

        adapter = new RecyclerAdapter(arrayList);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);


        prepareData_categrory();
        prepareData_first();

        try {
            Glide.with(this).load(R.drawable.s3).into((ImageView) findViewById(R.id.backdrop));
        } catch (Exception e) {
            e.printStackTrace();
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


    }

    private void prepareData_first() {

        final ArrayList<PlacesData> arrayList_view = new ArrayList<PlacesData>();

        String url = "https://city-app.000webhostapp.com/index.php/Api/place.json";
            OkHttpClient client = new OkHttpClient();
            ButterKnife.bind(this);

            mProgressBar.setVisibility(View.INVISIBLE);

            if (isNetworkAvailabe()) {
                toggleRefresh();

                Request request = new Request.Builder().url(url).build();

                Call call = client.newCall(request);
                call.enqueue(new Callback() {

                    @Override
                    public void onFailure(Call call, IOException e) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                toggleRefresh();
                            }
                        });

                        alertUserAboutError();

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                toggleRefresh();
                            }
                        });
                        try {
                            String jsonData = response.body().string();
                            Log.e(TAG,jsonData);
                            JSONArray jsonArray = new JSONArray(jsonData);

                            for(int i =0; i < jsonArray.length(); i++){
                                String Image_url = "https://city-app.000webhostapp.com";
                                JSONObject obj = jsonArray.getJSONObject(i);
                                String id_places = obj.getString("id");
                                String id_title = obj.getString("title");
                                String id_address = obj.getString("address");
                                String id_phone = obj.getString("phone");
                                String id_website = obj.getString("website");
                                String id_facility = obj.getString("facility");
                                String id_image = obj.getString("image");
                                String id_description = obj.getString("description");
                                String id_latitude = obj.getString("latitude");
                                String id_longitude = obj.getString("longitude");
                                String id_date = obj.getString("date");
// places url = https://city-app.000webhostapp.com/index.php/Api/place.json
// placesby category url = https://city-app.000webhostapp.com/index.php/Api/3{id category}/cat.json
// category usl = https://city-app.000webhostapp.com/index.php/Api/cat.json
                                //String arr = id_image.split(",")[0];
                                String arr = id_image.split(",")[0];
                                String final_image_url = Image_url + arr;
                                String final_image_url_all = id_image ;

                               // String final_image_url = Image_url + id_image;

                                Log.e(TAG,final_image_url);
                                PlacesData b = new PlacesData(id_places,id_title,id_address,id_phone,id_website,id_facility
                                ,final_image_url_all,id_description,id_latitude,id_longitude,id_date);
                                arrayList_view.add(b);
                                PlacesData a = new PlacesData(id_title,final_image_url);
                                arrayList.add(a);
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {

                                        adapter.notifyDataSetChanged();

                                    }});
                            }

                            adapter.go(arrayList_view);

                            if (response.isSuccessful()) {
                                mPlacesdata = parsePlacesDetails(jsonData);
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        //          updateDisplay();
                                    }
                                });


                            } else {
                                alertUserAboutError();
                            }
                        } catch (IOException e) {
                            Log.e(TAG, "Exception Caught ", e);
                        } catch (JSONException j) {
                            Log.e(TAG, "Exception Caught ", j);

                        }
                    }
                });
            }
            else{
                Toast.makeText(this,getString(R.string.network_unavailable),Toast.LENGTH_LONG).show();
            }

            arrayList.clear();

        }


    private void prepareData_categrory() {

        String url = "https://city-app.000webhostapp.com/index.php/Api/cat.json";
        OkHttpClient client = new OkHttpClient();
        ButterKnife.bind(this);

        ArrayList<String> category_name = new ArrayList<String >();


        if (isNetworkAvailabe()) {
            toggleRefresh();

            Request request = new Request.Builder().url(url).build();

            Call call = client.newCall(request);
            call.enqueue(new Callback() {

                @Override
                public void onFailure(Call call, IOException e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            toggleRefresh();
                        }
                    });

                    alertUserAboutError();

                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            toggleRefresh();
                        }
                    });
                    try {
                        String jsonData = response.body().string();
                        Log.e(TAG,jsonData);
                        JSONArray jsonArray = new JSONArray(jsonData);

                        for(int i=0;i<jsonArray.length();i++){
                            JSONObject obj = jsonArray.getJSONObject(i);

                           // String cat_id = obj.getString("cat_id");
//                            String cat_name = obj.getString("category_name");
//                            String cat_image = obj.getString("category_image");

                            cat_menu.add(obj.getString("category_name"));
                            cat_id.add(Integer.parseInt(obj.getString("cat_id")));
                            cat_image.add(obj.getString("category_image"));

                        }

                        setBackdrop(cat_image);
                        addMenuItemInNavMenuDrawer(cat_menu,cat_id);

                        if (response.isSuccessful()) {
                            mPlacesdata = parsePlacesDetails(jsonData);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    //          updateDisplay();
                                }
                            });


                        } else {
                            alertUserAboutError();
                        }
                    } catch (IOException e) {
                        Log.e(TAG, "Exception Caught ", e);
                    } catch (JSONException j) {
                        Log.e(TAG, "Exception Caught ", j);

                    }
                }
            });
        }
        else{
            Toast.makeText(this,getString(R.string.network_unavailable),Toast.LENGTH_LONG).show();
        }
    }

    private void setBackdrop(ArrayList<String> category_image) {
        try {
            Glide.with(mContext).load(category_image).into((ImageView) findViewById(R.id.backdrop));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addMenuItemInNavMenuDrawer(final ArrayList<String> cat_menu, final ArrayList<Integer> cat_id) {
        final NavigationView navView = (NavigationView) findViewById(R.id.nav_view);

       final Menu menu = navView.getMenu();
       //final Menu submenu = menu.addSubMenu("New Super SubMenu");
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

            for(int i=0;i<cat_menu.size();i++){

               // MenuItem item = menu.add(groupId, menuItemId, Order, "Menu Item 1 Title");
                menu.add(i,cat_id.get(i),i,cat_menu.get(i)).setIcon(R.drawable.entertainment);
               // menu.add(i, Menu.FIRST,Menu.FIRST , cat_menu.get(i)).setIcon(R.drawable.entertainment);
            }
                navView.invalidate();
            }
        });
    }

    private void prepareData(final int id_da) {
       final  ArrayList<PlacesData> arrayList_view = new ArrayList<PlacesData>();


        String url = "https://city-app.000webhostapp.com/index.php/Api/"+id_da+"/cat.json";
        OkHttpClient client = new OkHttpClient();
        ButterKnife.bind(this);

        mProgressBar.setVisibility(View.INVISIBLE);
        final String[] final_image_url = new String[1];
        if (isNetworkAvailabe()) {
            toggleRefresh();

            Request request = new Request.Builder().url(url).build();

            Call call = client.newCall(request);
            call.enqueue(new Callback() {

                @Override
                public void onFailure(Call call, IOException e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            toggleRefresh();
                        }
                    });

                    alertUserAboutError();

                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            toggleRefresh();
                        }
                    });
                    try {
                        String jsonData = response.body().string();
                        Log.e(TAG,jsonData);
                        JSONArray jsonArray = new JSONArray(jsonData);

                        for(int i =0; i < jsonArray.length(); i++){
                           // String Image_url = "https://city-app.000webhostapp.com";
                            JSONObject obj = jsonArray.getJSONObject(i);
                                String id_places = obj.getString("id");
                                String id_title = obj.getString("title");
                                String id_address = obj.getString("address");
                                String id_phone = obj.getString("phone");
                                String id_website = obj.getString("website");
                                String id_facility = obj.getString("facility");
                                String id_image = obj.getString("image");
                                String id_description = obj.getString("description");
                                String id_latitude = obj.getString("latitude");
                                String id_longitude = obj.getString("longitude");
                                String id_date = obj.getString("date");
// places url = https://city-app.000webhostapp.com/index.php/Api/place.json
// placesby category url = https://city-app.000webhostapp.com/index.php/Api/3{id category}/cat.json
// category usl = https://city-app.000webhostapp.com/index.php/Api/cat.json
                            String arr = id_image.split(",")[0];
                            final_image_url[0] = arr;

                            String final_image_url_all = id_image ;
                            PlacesData b = new PlacesData(id_places,id_title,id_address,id_phone,id_website,id_facility
                                    ,final_image_url_all,id_description,id_latitude,id_longitude,id_date);
                            arrayList_view.add(b);


                                Log.e(TAG, final_image_url[0]);
                                PlacesData a = new PlacesData(id_title,"https://city-app.000webhostapp.com" + final_image_url[0]);
                                arrayList.add(a);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    adapter.notifyDataSetChanged();

                                }});
                            }

                        adapter.go(arrayList_view);
                        try {
                            Glide.with(mContext).load("https://city-app.000webhostapp.com" + final_image_url[0]).into(backdrop);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        if (response.isSuccessful()) {
                            mPlacesdata = parsePlacesDetails(jsonData);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                          //          updateDisplay();
                                }
                            });


                        } else {
                            alertUserAboutError();
                        }
                    } catch (IOException e) {
                        Log.e(TAG, "Exception Caught ", e);
                    } catch (JSONException j) {
                        Log.e(TAG, "Exception Caught ", j);

                    }
                }
            });
        }
        else{
            Toast.makeText(this,getString(R.string.network_unavailable),Toast.LENGTH_LONG).show();
        }

        arrayList.clear();

    }

    private PlacesData parsePlacesDetails(String jsonData) throws  JSONException{
        PlacesData placesData = new PlacesData();
        //String add = placesData.getAddress(jsonData);

       // Log.e(TAG,add);
        return  placesData;
    }

    private void toggleRefresh() {
        if(mProgressBar.getVisibility() == View.INVISIBLE){
            mProgressBar.setVisibility(View.VISIBLE);
           // mImageView.setVisibility(View.INVISIBLE);
        }
        else{
            mProgressBar.setVisibility(View.INVISIBLE);
          //  mImageView.setVisibility(View.VISIBLE);
        }
    }

    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

    private void initCollapsingToolbar() {
        final CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(" ");
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
        appBarLayout.setExpanded(true);

        // hiding & showing the title when toolbar expanded & collapsed
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbar.setTitle(getString(R.string.app_name));
                    isShow = true;
                } else if (isShow) {
                    collapsingToolbar.setTitle(" ");
                    isShow = false;
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        prepareData(id);
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        prepareData(id);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

    private boolean isNetworkAvailabe() {
        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo  = manager.getActiveNetworkInfo();
        boolean isAvailable = false;

        if(networkInfo != null && networkInfo.isConnected()){
            isAvailable = true;
        }
        return isAvailable;
    }

    private void alertUserAboutError() {
        AlertDialogFragment dialog = new AlertDialogFragment();

        dialog.show(getFragmentManager(),"error_dialog");

    }



}
