//package divy.knpurtour;
//
//import android.content.Context;
//import android.support.v4.view.PagerAdapter;
//import android.support.v4.view.ViewPager;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//
///**
// * Created by Acer-PC on 06-03-2018.
// */
//
//public class ViewPageAdapter extends PagerAdapter {
//
//    private Context context;
//    private LayoutInflater layoutInflater;
//    private Integer images[]={R.drawable.s1,R.drawable.s2,R.drawable.s3};
//
//    public ViewPageAdapter(Context context) {
//        this.context = context;
//    }
//
//    @Override
//    public int getCount() {
//        return images.length;
//    }
//
//    @Override
//    public boolean isViewFromObject(View view, Object object) {
//        return view==object;
//    }
//
//    public Object instantiateItem(ViewGroup container, int position)
//    {
//        layoutInflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        View view=layoutInflater.inflate(R.layout.custom_pager,null);
//        ImageView imageview=(ImageView) view.findViewById(R.id.place_image);
//        imageview.setImageResource(images[position]);
//
//        ViewPager viewpager=(ViewPager) container;
//        viewpager.addView(view,0);
//        return view;
//    }
//
//    @Override
//    public void destroyItem(ViewGroup container, int position, Object object) {
//        //super.destroyItem(container, position, object);
//
//        ViewPager viewPager=(ViewPager) container;
//        View view=(View) object;
//        viewPager.removeView(view);
//
//    }
//
//
//}
