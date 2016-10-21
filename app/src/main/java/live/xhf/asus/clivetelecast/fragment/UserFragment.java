package live.xhf.asus.clivetelecast.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import live.xhf.asus.clivetelecast.R;
import live.xhf.asus.clivetelecast.SettActivity;

/**
 * 个人中心
 */
public class UserFragment extends Fragment implements View.OnClickListener{

    private View view;
    private ImageView user_edit,user_msg;// 编辑按钮、 消息按钮
    private ImageView user_icon;// 头像
    private TextView user_name;// 昵称
    private LinearLayout l_sett,l_con; // 设置 / 关注模块
    private TextView con_nums; //关注人数

    private SharedPreferences spf;
    private String q_icon;
    private String q_name;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = View.inflate(getActivity(),R.layout.fragment_user,null);

        //初始化控件
        initControl();


        return view;
    }

    private void initControl() {
        user_edit = (ImageView)view.findViewById(R.id.user_edit);
            user_edit.setOnClickListener(this);
        user_msg = (ImageView)view.findViewById(R.id.user_msg);
            user_msg.setOnClickListener(this);
        user_icon = (ImageView)view.findViewById(R.id.user_icon);
        user_name = (TextView)view.findViewById(R.id.user_name);
        l_sett = (LinearLayout)view.findViewById(R.id.l_sett);
            l_sett.setOnClickListener(this);
        l_con = (LinearLayout)view.findViewById(R.id.l_con);
            l_con.setOnClickListener(this);
        con_nums = (TextView)view.findViewById(R.id.con_nums);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.l_sett:
                    startActivity(new Intent(getActivity(), SettActivity.class));
                break;
            case R.id.l_con:


                break;
        }
    }

    @Override
    public void onStart() {
        spf = getActivity().getSharedPreferences("LoginInfo", getActivity().MODE_PRIVATE);
        q_icon = spf.getString("QQicon",null);
        q_name = spf.getString("QQname",null);
        if(q_icon !=null || q_name !=null){
            Glide.with(getActivity()).load(q_icon).into(user_icon);
            user_name.setText(q_name);
        }
        super.onStart();
    }
}
