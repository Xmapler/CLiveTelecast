package live.xhf.asus.clivetelecast.fragment.home_frag;


import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;

import java.util.List;

import live.xhf.asus.clivetelecast.R;
import live.xhf.asus.clivetelecast.adapter.MyFindGvAdapter;
import live.xhf.asus.clivetelecast.bean.Home_find;
import live.xhf.asus.clivetelecast.play.VideoViewPlayingActivity;

/**
 * 最新页面
 * A simple {@link Fragment} subclass.
 */
public class FindFragment extends Fragment {

    private String path = "http://live.jufan.tv/cgi/hall/get?sign=A2A031A7CEFAAACDBE108413B6FE98C9FC2A311E&r=ofkh&page=0&type=new&userid=500056507";
    private PullToRefreshGridView pullToRefreshGridView;
    private View view;
    private List<Home_find.FindBean.FBean> findlist;
    private MyFindGvAdapter adapter;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            adapter = new MyFindGvAdapter(findlist,getActivity());
            pullToRefreshGridView.setAdapter(adapter);
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       view = View.inflate(getActivity(),R.layout.fragment_find,null);


        initControl();
        new Thread(){
            @Override
            public void run() {
                super.run();
                getData();
            }
        }.start();

       return view;
    }

    private void initControl() {
        pullToRefreshGridView = (PullToRefreshGridView)view.findViewById(R.id.my_gridview);

        pullToRefreshGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(),VideoViewPlayingActivity.class);
                intent.setData(Uri.parse(findlist.get(position).video));
                intent.putExtra("play_name",findlist.get(position).name);
                intent.putExtra("play_icon",findlist.get(position).smallheadimg);
                intent.putExtra("play_num",findlist.get(position).online);
                intent.putExtra("play_room",findlist.get(position).uid);
                startActivity(intent);
            }
        });

        pullToRefreshGridView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<GridView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<GridView> refreshView) {
                String str = DateUtils.formatDateTime(getActivity(), System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
                refreshView.getLoadingLayoutProxy().setLastUpdatedLabel("上次刷新时间 "+str);
                getData();
                new GetDataTask().execute();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<GridView> refreshView) {
                String str = DateUtils.formatDateTime(getActivity(), System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
                refreshView.getLoadingLayoutProxy().setLastUpdatedLabel("上次刷新时间 "+str);
                getData();
                new GetDataTask().execute();
//                adapter.notifyDataSetChanged();
            }
        });

    }

    private class GetDataTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            try{
                Thread.sleep(2000);
            } catch (InterruptedException e) {
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            pullToRefreshGridView.onRefreshComplete();
            super.onPostExecute(aVoid);
        }
    }

    private void getData() {
        RequestQueue rQueue = Volley.newRequestQueue(getActivity());
        StringRequest request = new StringRequest(Request.Method.GET,path, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {

                Gson gson = new Gson();
                Home_find find_home = gson.fromJson(s,Home_find.class);
                findlist = find_home.content.list;

                handler.sendEmptyMessage(0x123);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        });
        rQueue.add(request);
        rQueue.start();
   }


}
