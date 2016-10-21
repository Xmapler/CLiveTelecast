package live.xhf.asus.clivetelecast;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import live.xhf.asus.clivetelecast.fragment.HomeFragment;
import live.xhf.asus.clivetelecast.fragment.TeleCastActivity;
import live.xhf.asus.clivetelecast.fragment.UserFragment;
import live.xhf.asus.clivetelecast.rewrite.CustomProgressDialog;

/**
 *  首页
 */
public class CLiveActivity extends FragmentActivity implements View.OnClickListener {

    private ImageView home,home_click;// 首页
    private ImageView live;             //直播
    private ImageView user,user_click;//个人中心
    private FrameLayout framlayout;
    private LinearLayout l_home,l_user;

    private long mExitTime ;
    private HomeFragment home_fra;
    private UserFragment user_fra;
    private FragmentManager manager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clive);

        CustomProgressDialog dialog =new CustomProgressDialog(this, "正在加载中",R.drawable.animation_load);
        dialog.show();

        initControl();

        home.setVisibility(View.GONE);
        home_click.setVisibility(View.VISIBLE);
        user_click.setVisibility(View.GONE);

        home_fra = new HomeFragment();
        user_fra = new UserFragment();

        manager = getSupportFragmentManager();
        FragmentTransaction transcation = getSupportFragmentManager().beginTransaction();
        transcation.add(R.id.f1,home_fra,"shouye");
        transcation.add(R.id.f1,user_fra,"geren");

        switchTag("shouye");
        transcation.commit();
    }

    private void switchTag(String str) {
        FragmentTransaction transcation = manager.beginTransaction();
        if(str.equals("shouye")){

            transcation.show(home_fra);
            transcation.hide(user_fra);

        }else if(str.equals("geren")){

            transcation.show(user_fra);
            transcation.hide(home_fra);

        }
        transcation.commit();
    }

    private void initControl() {
        framlayout = (FrameLayout)findViewById(R.id.f1);
        home = (ImageView)findViewById(R.id.clive_home);
            home.setOnClickListener(this);
        home_click = (ImageView)findViewById(R.id.clive_home_click);
            home_click.setOnClickListener(this);
        live = (ImageView)findViewById(R.id.clive_live);
            live.setOnClickListener(this);
        user = (ImageView)findViewById(R.id.clive_user);
            user.setOnClickListener(this);
        user_click = (ImageView)findViewById(R.id.clive_user_click);
            user_click.setOnClickListener(this);

        l_home = (LinearLayout)findViewById(R.id.l_home);
            l_home.setOnClickListener(this);
        l_user = (LinearLayout)findViewById(R.id.l_user);
            l_user.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.clive_home:
                home.setVisibility(View.GONE);
                home_click.setVisibility(View.VISIBLE);
                user_click.setVisibility(View.GONE);
                user.setVisibility(View.VISIBLE);
                switchTag("shouye");
            break;
            case R.id.l_home:
                home.setVisibility(View.GONE);
                home_click.setVisibility(View.VISIBLE);
                user_click.setVisibility(View.GONE);
                user.setVisibility(View.VISIBLE);
                switchTag("shouye");
                break;

            case R.id.clive_live:
//                Toast.makeText(this,"暂未开发",Toast.LENGTH_LONG).show();
                startActivity(new Intent(CLiveActivity.this, TeleCastActivity.class));

                break;

            case R.id.clive_user:
                user.setVisibility(View.GONE);
                user_click.setVisibility(View.VISIBLE);
                home_click.setVisibility(View.GONE);
                home.setVisibility(View.VISIBLE);
                switchTag("geren");
                break;
            case R.id.l_user:
                user.setVisibility(View.GONE);
                user_click.setVisibility(View.VISIBLE);
                home_click.setVisibility(View.GONE);
                home.setVisibility(View.VISIBLE);
                switchTag("geren");
                break;
        }
    }

    /**
     * an两次返回
     */
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - mExitTime) > 2000) {

                Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_LONG).show();
                mExitTime = System.currentTimeMillis();
            } else {
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
    @Override
    public void onBackPressed() {
        moveTaskToBack(false);
        super.onBackPressed();
    }
}
