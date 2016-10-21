package live.xhf.asus.clivetelecast.fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import live.xhf.asus.clivetelecast.R;
import live.xhf.asus.clivetelecast.application.LIMSApplication;
import live.xhf.asus.clivetelecast.fragment.home_frag.ConcernFragment;
import live.xhf.asus.clivetelecast.fragment.home_frag.FindFragment;
import live.xhf.asus.clivetelecast.fragment.home_frag.HotFragment;
import live.xhf.asus.clivetelecast.utils.NetUtils;

/**
 * 首页
 */
public class HomeFragment extends Fragment implements View.OnClickListener{

    private View view;
    private TabLayout tablelayout;
    private ViewPager vp;
    private Button sou;
    private Button bei;
    private String[] str;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.shouye_fragment,container,false);
        CheckNetCase();
        tablelayout = (TabLayout) view.findViewById(R.id.tablelayout);
        vp = (ViewPager) view.findViewById(R.id.vp);
        sou = (Button) view.findViewById(R.id.sousuo);
        bei = (Button) view.findViewById(R.id.paihang);
        sou.setOnClickListener(this);
        bei.setOnClickListener(this);
        //对集合添加数据
        initData();
        for (int i = 0; i < str.length; i++) {
            //给tablelayout添加导航条目
            tablelayout.addTab(tablelayout.newTab().setText(str[i]));
        }
        vp.setAdapter(new FragmentPagerAdapter(getActivity().getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                Fragment fragment = null;
                switch (position){
                    case 0:
                        fragment = new ConcernFragment();
                        break;
                    case 1:
                        fragment = new HotFragment();
                        break;
                    case 2:
                        fragment = new FindFragment();
                        break;
                }
                return fragment;
            }

            @Override
            public int getCount() {
                return str.length;
            }
        });

        vp.setCurrentItem(1);

        tablelayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                vp.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        vp.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tablelayout));
        vp.setOffscreenPageLimit(3);

        return view;
    }

    private void initData() {
        str = new String[]{"关注", "热门", "最新"};

    }

    @Override
    public void onClick(View v) {

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
                /*home_scroll.setVisibility(View.GONE);
                homepager_inscollview_linearlayout.setVisibility(View.VISIBLE);
                View not_new_face = CommonUtils.inflate(R.layout.not_net_face);
                homepager_inscollview_linearlayout.addView(not_new_face);
                Button loading_face = (Button) not_new_face.findViewById(R.id.loading_face);
                loading_face.setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(android.provider.Settings.ACTION_DATA_ROAMING_SETTINGS));
                    }
                });*/
            }
//					mLocationClient.start();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getActivity(), "判断网络失败", Toast.LENGTH_LONG).show();
        }
    }

}
