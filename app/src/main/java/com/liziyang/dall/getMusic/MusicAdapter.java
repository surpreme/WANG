package com.liziyang.dall.getMusic;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.liziyang.dall.R;


import java.text.SimpleDateFormat;
import java.util.List;

/**
 * 自定义适配器
 */

public class MusicAdapter extends BaseAdapter {
    public String nowPlayMusicName;
    private List<MusicResult> oList;
    private Context context;
    private LayoutInflater layoutInflater;
    public MusicAdapter(List<MusicResult> oList,Context context){
        this.oList=oList;
        this.context=context;
        this.layoutInflater=LayoutInflater.from ( context );

    }
    /**
     * How many items are in the data set represented by this Adapter.
     *
     * @return Count of items.
     */
    @Override
    public int getCount() {
        return oList.size ();
    }

    /**
     * Get the data item associated with the specified position in the data set.
     *
     * @param position Position of the item whose data we want within the adapter's
     *                 data set.
     * @return The data at the specified position.
     */
    @Override
    public Object getItem(int position) {
        return oList.get ( position );
    }

    /**
     * Get the row id associated with the specified position in the list.
     *
     * @param position The position of the item within the adapter's data set whose row id we want.
     * @return The id of the item at the specified position.
     */
    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * Get a View that displays the data at the specified position in the data set. You can either
     * create a View manually or inflate it from an XML layout file. When the View is inflated, the
     * parent View (GridView, ListView...) will apply default layout parameters unless you use
     * {@link LayoutInflater#inflate(int , ViewGroup , boolean)}
     * to specify a root view and to prevent attachment to the root.
     *
     * @param position    The position of the item within the adapter's data set of the item whose view
     *                    we want.
     * @param convertView The old view to reuse, if possible. Note: You should check that this view
     *                    is non-null and of an appropriate type before using. If it is not possible to convert
     *                    this view to display the correct data, this method can create a new view.
     *                    Heterogeneous lists can specify their number of view types, so that this View is
     *                    always of the right type (see {@link #getViewTypeCount()} and
     *                    {@link #getItemViewType(int)}).
     * @param parent      The parent that this view will eventually be attached to
     * @return A View corresponding to the data at the specified position.
     */
    @Override
    public View getView(int position , View convertView , ViewGroup parent) {
        ViewHolder viewHolder=null;
        if (convertView==null){
            viewHolder=new ViewHolder ();
            convertView=layoutInflater.inflate ( R.layout.music_item_layout,null );
            viewHolder.img=convertView.findViewById ( R.id.music_item_img_id );
            viewHolder.name=convertView.findViewById ( R.id.music_item_name_id );
            viewHolder.author=convertView.findViewById ( R.id.music_item_author_id );
            viewHolder.duration=convertView.findViewById ( R.id.music_item_time_id );
            convertView.setTag ( viewHolder );

        }else {
            viewHolder= (ViewHolder) convertView.getTag ();

        }
        viewHolder.img.setBackgroundResource (R.color.colorAccent );
        nowPlayMusicName="歌曲名："+oList.get ( position ).getMusic_name_String ();
        viewHolder.name.setText ( nowPlayMusicName );
        viewHolder.author.setText ( "艺术家:"+oList.get ( position ).getMusic_author_String () );
//        viewHolder.duration.setText ( "总时长"+oList.get ( position ).getDuration () );
        viewHolder.duration.setText ( "总时长"+getTime ( oList.get ( position ).getDuration () ) );
        return convertView;
    }

    /**
     * 转换long类型的时间为mm：ss格式
     * @param time
     * @return
     */
    private String getTime(long time){
        SimpleDateFormat formats=new SimpleDateFormat ( "mm:ss" );
        String times=formats.format ( time );

        return times;
    }
    /**
     * 缓存器
     */
    class ViewHolder{
        ImageView img;
        TextView name;
        TextView author;
        TextView duration;

    }
}
