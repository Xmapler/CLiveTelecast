package live.xhf.asus.clivetelecast.base;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import live.xhf.asus.clivetelecast.R;

/**
 * Created by asus on 2016/10/8.
 */
public abstract class BaseActivity extends Activity implements View.OnClickListener {

    private ImageView title_back;
    private TextView title_name;

    @Override
    public void onClick(View v) {

    }
    //写三个方法
    //1:初始化头部控件
    //2:初始化控件
    //3:设置监听


    //初始化头部控件的方法
    public abstract void initHeader();

    //查找控件的方法
    public abstract void initWidget();

    //设置状态的方法
    public abstract void setWidgetState();


    //初始化头部控件
    public void initHeaderWidget() {

        title_back = (ImageView)findViewById(R.id.title_back);
        title_name = (TextView)findViewById(R.id.titleInfo);
    }


}
