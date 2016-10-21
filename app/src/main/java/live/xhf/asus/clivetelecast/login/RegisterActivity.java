package live.xhf.asus.clivetelecast.login;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import live.xhf.asus.clivetelecast.R;
import live.xhf.asus.clivetelecast.base.BaseActivity;

public class RegisterActivity extends BaseActivity {

    private ImageView title_back;
    private TextView titleInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initHeader();
        initControl();
    }

    private void initControl() {
        title_back = (ImageView)findViewById(R.id.title_back);
            title_back.setOnClickListener(this);
        titleInfo = (TextView)findViewById(R.id.titleInfo);
        titleInfo.setText("用户注册");
    }

    @Override
    public void initHeader() {
        initHeaderWidget();

    }

    @Override
    public void initWidget() {

    }

    @Override
    public void setWidgetState() {

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.title_back:
                finish();
                overridePendingTransition(R.anim.anim_back_in, R.anim.anim_back_out);
                break;
        }
    }
}
