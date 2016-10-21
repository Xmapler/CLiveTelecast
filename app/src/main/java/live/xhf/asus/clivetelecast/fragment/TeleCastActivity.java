package live.xhf.asus.clivetelecast.fragment;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import live.xhf.asus.clivetelecast.R;

/**
 * 直播开启页面
 */
public class TeleCastActivity extends AppCompatActivity implements View.OnClickListener{

    private SurfaceView surfaceView;
    private EditText sur_edit;
    private ImageView sur_exit;
    private ImageView fr,fr_click,zome,zome_click,qq,qq_click,wx,wx_click;
    private Button bt_replace,bt_live;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tele_cast);

        initControl();

//        surfaceView.setZOrderOnTop(true);
//        surfaceView.getHolder().setFormat(PixelFormat.TRANSLUCENT);

    }

    private void initControl() {
        surfaceView = (SurfaceView)findViewById(R.id.surface);

        sur_edit = (EditText)findViewById(R.id.sur_edit);
        sur_exit = (ImageView)findViewById(R.id.sur_exit);
            sur_exit.setOnClickListener(this);
        fr = (ImageView)findViewById(R.id.sur_fr);
            fr.setOnClickListener(this);
        fr_click = (ImageView)findViewById(R.id.sur_fr_click);
            fr_click.setOnClickListener(this);
        zome = (ImageView)findViewById(R.id.sur_zome);
            zome.setOnClickListener(this);
        zome_click = (ImageView)findViewById(R.id.sur_zome_click);
            zome_click.setOnClickListener(this);
        qq = (ImageView)findViewById(R.id.sur_qq);
            qq.setOnClickListener(this);
        qq_click = (ImageView)findViewById(R.id.sur_qq_click);
            qq_click.setOnClickListener(this);
        wx = (ImageView)findViewById(R.id.sur_wx);
            wx.setOnClickListener(this);
        wx_click = (ImageView)findViewById(R.id.sur_wx_click);
            wx_click.setOnClickListener(this);

        bt_replace = (Button)findViewById(R.id.sur_replace);
            bt_replace.setOnClickListener(this);
        bt_live = (Button)findViewById(R.id.sur_live);
            bt_live.setOnClickListener(this);

        //默认朋友圈
        fr.setVisibility(View.GONE);
        fr_click.setVisibility(View.VISIBLE);
        zome.setVisibility(View.VISIBLE);
        qq.setVisibility(View.VISIBLE);
        wx.setVisibility(View.VISIBLE);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.sur_exit:
                    finish();
                overridePendingTransition(R.anim.anim_back_in,R.anim.anim_back_out);
                break;
            case R.id.sur_fr:
                fr.setVisibility(View.GONE);
                fr_click.setVisibility(View.VISIBLE);
                zome.setVisibility(View.VISIBLE);
                    zome_click.setVisibility(View.GONE);
                qq.setVisibility(View.VISIBLE);
                    qq_click.setVisibility(View.GONE);
                wx.setVisibility(View.VISIBLE);
                    wx_click.setVisibility(View.GONE);
                break;
            case R.id.sur_zome:
                zome.setVisibility(View.GONE);
                zome_click.setVisibility(View.VISIBLE);
                fr.setVisibility(View.VISIBLE);
                    fr_click.setVisibility(View.GONE);
                qq.setVisibility(View.VISIBLE);
                    qq_click.setVisibility(View.GONE);
                wx.setVisibility(View.VISIBLE);
                    wx_click.setVisibility(View.GONE);
                break;
            case R.id.sur_qq:
                qq.setVisibility(View.GONE);
                qq_click.setVisibility(View.VISIBLE);
                zome.setVisibility(View.VISIBLE);
                    zome_click.setVisibility(View.GONE);
                fr.setVisibility(View.VISIBLE);
                    fr_click.setVisibility(View.GONE);
                wx.setVisibility(View.VISIBLE);
                    wx_click.setVisibility(View.GONE);
                break;
            case R.id.sur_wx:
                wx.setVisibility(View.GONE);
                wx_click.setVisibility(View.VISIBLE);
                zome.setVisibility(View.VISIBLE);
                    zome_click.setVisibility(View.GONE);
                qq.setVisibility(View.VISIBLE);
                    qq_click.setVisibility(View.GONE);
                fr.setVisibility(View.VISIBLE);
                    fr_click.setVisibility(View.GONE);
                break;
            case R.id.sur_replace:

                break;
            case R.id.sur_live:

                break;
        }
    }
}
