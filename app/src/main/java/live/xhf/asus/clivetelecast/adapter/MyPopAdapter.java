package live.xhf.asus.clivetelecast.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import live.xhf.asus.clivetelecast.R;
import live.xhf.asus.clivetelecast.bean.Pop_gift;

/**
 * Created by asus on 2016/10/20.
 */
public class MyPopAdapter extends BaseAdapter {

    Context context;
    List<Pop_gift.PGift.JUF.PList> plist;

    public MyPopAdapter( Context context,List<Pop_gift.PGift.JUF.PList> plist) {
        this.context = context;
        this.plist = plist;
    }

    @Override
    public int getCount() {
        return plist.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView == null){
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.pop_item,null);
            holder.pop_icon = (ImageView) convertView.findViewById(R.id.pop_icon);
            holder.pop_price = (TextView)convertView.findViewById(R.id.pop_price);
            holder.pop_max = (TextView)convertView.findViewById(R.id.pop_max);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
//        Glide.with(context).load(plist.get(position).gif_url1).into(new GlideDrawableImageViewTarget(holder.pop_icon, 10));
        Glide.with(context).load(plist.get(position).url).into(holder.pop_icon);
        holder.pop_price.setText(plist.get(position).exp+"钻");
        holder.pop_max.setText(plist.get(position).desc);
        return convertView;
    }

    class ViewHolder{
        ImageView pop_icon;
        TextView pop_price,pop_max;
    }
}
