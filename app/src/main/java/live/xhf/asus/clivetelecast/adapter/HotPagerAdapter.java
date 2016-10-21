package live.xhf.asus.clivetelecast.adapter;

import android.content.Context;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.List;

import live.xhf.asus.clivetelecast.R;
import live.xhf.asus.clivetelecast.bean.Home_Hot;

/**
 * Created by asus on 2016/10/9.
 */
public class HotPagerAdapter extends PagerAdapter {

    List<Home_Hot.Content.Banner> bannerList;
    Context context;
    Handler handler;

    public HotPagerAdapter(List<Home_Hot.Content.Banner> bannerList,Context context,Handler handler) {
        super();
        this.bannerList = bannerList;
        this.context = context;
        this.handler = handler;
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        View view = View.inflate(context, R.layout.hot_vp_layout,null);
        ImageView image = (ImageView)view.findViewById(R.id.img);

        image.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        handler.removeCallbacksAndMessages(null);
                        break;
                    case MotionEvent.ACTION_UP:
                        handler.sendEmptyMessageDelayed(1,2000);
                        break;
                    case MotionEvent.ACTION_CANCEL:
                        handler.sendEmptyMessageDelayed(1,2000);
                        break;
                }
                return true;
            }
        });
        Glide.with(context).load(bannerList.get(position%bannerList.size()).img).into(image);

        container.addView(view);
        return view;
    }
}
