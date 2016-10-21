package live.xhf.asus.clivetelecast.rewrite;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Xfermode;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.ImageView;

/**
 * Created by asus on 2016/10/13.
 */
public class WriteImageV extends ImageView {
    private Rect    rect = new Rect();
    private RectF   rectF = new RectF();
    private float   radius;
    private Bitmap  bitmap;
    private Xfermode xfermode = new PorterDuffXfermode(PorterDuff.Mode.SRC_IN);

    public WriteImageV(Context context)
    {
        this(context, null);
    }

    public WriteImageV(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        radius = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2, getResources().getDisplayMetrics());
        try
        {
            if(android.os.Build.VERSION.SDK_INT >= 11)
            {
                setLayerType(LAYER_TYPE_SOFTWARE, null);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void setImageBitmap(Bitmap bm)
    {
        this.bitmap = bm;
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        if (bitmap == null)
            return;

        rect.set(0,0,getWidth(),getHeight());
        rectF.set(rect);

        Paint paint = new Paint();
        paint.setColor(Color.RED);
        paint.setAntiAlias(true);
        canvas.drawRoundRect(rectF, radius, radius, paint);
        paint.setXfermode(xfermode);
        canvas.drawBitmap(bitmap, null, rect, paint);
    }
}