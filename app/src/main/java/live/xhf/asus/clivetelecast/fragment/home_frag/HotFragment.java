package live.xhf.asus.clivetelecast.fragment.home_frag;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;

import java.util.ArrayList;
import java.util.List;

import live.xhf.asus.clivetelecast.R;
import live.xhf.asus.clivetelecast.adapter.HotPagerAdapter;
import live.xhf.asus.clivetelecast.adapter.MyHotLvAdapter;
import live.xhf.asus.clivetelecast.application.LIMSApplication;
import live.xhf.asus.clivetelecast.bean.Home_Hot;
import live.xhf.asus.clivetelecast.play.VideoViewPlayingActivity;
import live.xhf.asus.clivetelecast.utils.NetUtils;

/**
 * 热门页面
 */
public class HotFragment extends Fragment {

    private View view;
    private ViewPager vp;
    private ListView lv;
    private LinearLayout ll_dot,layout;
    private String path = "http://live.jufan.tv/cgi/hall/get?sign=3E1C02CDE8AE682136B658A73F63406D700846EC&r=xjjf&page=0&type=hot&userid=500056449";
    private List<Home_Hot.Content.Banner> bannerList;
    private List<ImageView> dotList = new ArrayList<ImageView>();
    private List<Home_Hot.Content.LList> llist;
    private MyHotLvAdapter hotadapter;
    private PullToRefreshScrollView pullToRefreshScrollView;
    ScrollView mScrollView;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int num = msg.what;
            switch (num){
                case 0:
                    bannerList = (List<Home_Hot.Content.Banner>) msg.obj;
                    vp.setAdapter(new HotPagerAdapter(bannerList,getActivity(),handler));

                    initDot();
                    //设置初始化索引
                    vp.setCurrentItem(100000);
                    //发送延时消息，无限轮播
                    sendDelayMessage();
                    ViewPagerPagerListener();
                    break;
                case 1:
                    int position = vp.getCurrentItem();
                    position++;
                    vp.setCurrentItem(position);
                    sendDelayMessage();
                    break;
                case 2:

                    hotadapter = new MyHotLvAdapter(llist,getActivity());
                    lv.setAdapter(hotadapter);

                break;

            }
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = View.inflate(getActivity(),R.layout.fragment_hot,null);
        initControl();
        //网络判断
        CheckNetCase();
        new Thread(){
            @Override
            public void run() {
                getData();
            }
        }.start();
        return view;
    }

    private void getData() {
        RequestQueue rQueue = Volley.newRequestQueue(getActivity());
        StringRequest request = new StringRequest(Request.Method.GET,path, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {

                System.out.println("返回的数据"+s);
                Gson gson = new Gson();
                Home_Hot hot = gson.fromJson(s,Home_Hot.class);
                bannerList = hot.content.banner;

                Message msg = Message.obtain();
                msg.obj = bannerList;
                msg.what = 0;
                handler.sendMessage(msg);

                llist = hot.content.list;
                Message msg1 = new Message();
                msg1.obj = llist;
                msg1.what = 2;
                handler.sendMessage(msg1);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        });
        rQueue.add(request);
        rQueue.start();
    }
    private void initDot() {
        if(dotList!=null){
            dotList.clear();
        }
        //移除小点
        ll_dot.removeAllViews();
        for (int i = 0; i < bannerList.size(); i++) {
            ImageView iv = new ImageView(getActivity());
            if(i==0){
                iv.setImageResource(R.drawable.dot_fouce);
            }else{
                iv.setImageResource(R.drawable.dot_normal);
            }
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(10,10);
            params.setMargins(5,0,5,0);
            dotList.add(iv);
            ll_dot.addView(iv,params);
        }
    }
    /**
     * 发送延时消息
     */
    private void sendDelayMessage() {
        handler.sendEmptyMessageDelayed(1, 2000);
    }
    private void ViewPagerPagerListener() {
        vp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                //遍历小点的集合
                for (int i = 0; i < dotList.size(); i++) {
                    if(position%bannerList.size()==i){
                        dotList.get(i).setImageResource(R.drawable.dot_fouce);
                    }else{
                        dotList.get(i).setImageResource(R.drawable.dot_normal);
                    }
                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
    private void initControl() {
        ll_dot = (LinearLayout)view.findViewById(R.id.ll_dot);
        layout = (LinearLayout)view.findViewById(R.id.layout);
        //设置当前焦点，防止打开Activity出现在ListView位置问题
        layout.setFocusable(true);
        layout.setFocusableInTouchMode(true);
        layout.requestFocus();
        vp = (ViewPager)view.findViewById(R.id.viewpager);

        lv = (ListView)view.findViewById(R.id.listview);
        //取消每个item下的下划线
        lv.setDividerHeight(0);

        //listViewItem的点击事件
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(getActivity(),VideoViewPlayingActivity.class);
                    intent.setData(Uri.parse(llist.get(position).video));
                    intent.putExtra("play_name",llist.get(position).name);
                    intent.putExtra("play_icon",llist.get(position).smallheadimg);
                    intent.putExtra("play_num",llist.get(position).online);
                    intent.putExtra("play_room",llist.get(position).uid);
                    startActivity(intent);

            }
        });


        pullToRefreshScrollView = (PullToRefreshScrollView)view.findViewById(R.id.hot_scrollview);
        String str = DateUtils.formatDateTime(getActivity(), System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
        pullToRefreshScrollView.getLoadingLayoutProxy().setLastUpdatedLabel("上次刷新时间 "+str);
          /*
          * 设置PullToRefresh刷新模式
          * BOTH:上拉刷新和下拉刷新都支持
          * DISABLED：禁用上拉下拉刷新
          * PULL_FROM_START:仅支持下拉刷新（默认）
          * PULL_FROM_END：仅支持上拉刷新
          * MANUAL_REFRESH_ONLY：只允许手动触发
          * */
        pullToRefreshScrollView.setMode(PullToRefreshBase.Mode.BOTH);


        pullToRefreshScrollView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ScrollView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ScrollView> refreshView) {

                getData();
                new GetDataTask().execute();
//                hotadapter.notifyDataSetChanged();
            }
        });
        mScrollView = pullToRefreshScrollView.getRefreshableView();
    }

    private class GetDataTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            try {
                Thread.sleep(2000);

            } catch (InterruptedException e) {
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {

            pullToRefreshScrollView.onRefreshComplete();
            super.onPostExecute(aVoid);

        }
    }

    // 判断网络状态
    public void CheckNetCase() {
        try {
            if (NetUtils.isMobileDataEnable(getActivity())) {
                Toast.makeText(LIMSApplication.getContext(),
                        "当前处于2G/3G/4G状态", Toast.LENGTH_LONG).show();
            } else if (NetUtils.isWifiDataEnable(getActivity())) {
                Toast.makeText(LIMSApplication.getContext(),
                        "当前处于WiFi状态", Toast.LENGTH_LONG).show();
            } else if (NetUtils.isNetworkRoaming(getActivity())) {
                Toast.makeText(LIMSApplication.getContext(),
                        "当前处于漫游状态", Toast.LENGTH_LONG).show();
            } else if (NetUtils.isNetworkAvailable(getActivity())) {
                Toast.makeText(LIMSApplication.getContext(),
                        "当前网络可用", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getActivity(), "当前网络不可用", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getActivity(), "判断网络失败", Toast.LENGTH_LONG).show();
        }
    }
}

