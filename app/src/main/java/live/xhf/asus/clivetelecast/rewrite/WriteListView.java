package live.xhf.asus.clivetelecast.rewrite;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * Created by asus on 2016/10/8.
 */
public class WriteListView extends ListView {


    public WriteListView(Context context) {
        super(context);
    }

    public WriteListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public WriteListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }

}
