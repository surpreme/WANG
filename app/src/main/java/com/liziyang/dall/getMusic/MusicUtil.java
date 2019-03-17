package com.liziyang.dall.getMusic;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;

import java.util.ArrayList;
import java.util.List;

public class MusicUtil {
    public static List<MusicResult> getMusicDate(Context context){
        List<MusicResult> oList=new ArrayList<MusicResult> (  );
        ContentResolver resolver=context.getContentResolver ();
        /**
         * 查询手机中的音乐对象 返回一个游标对象
         * 第一个为uri参数为外部存储
         * 最后一个排序
         */
        Cursor cursor=resolver.query ( MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                null,
                null,
                null,
                MediaStore.Audio.Media.DEFAULT_SORT_ORDER );
        if (cursor != null) {
            while (cursor.moveToNext ()){
                MusicResult music=new MusicResult ();
                /**
                 * 获得歌曲名
                 */
                String name=cursor.getString ( cursor.getColumnIndex ( MediaStore.Audio.Media.TITLE ) );
                /**
                 * 获得歌曲艺术家名字
                 */
                String author=cursor.getString ( cursor.getColumnIndex ( MediaStore.Audio.Media.ARTIST ) );
                /**
                 * 路径
                 */
                String path=cursor.getString ( cursor.getColumnIndex ( MediaStore.Audio.Media.DATA ) );
                /**
                 * 总时间
                 */
                long duration=cursor.getLong ( cursor.getColumnIndex ( MediaStore.Audio.Media.DURATION ) );
                /**
                 * 如果音乐文件无演唱者姓名
                 */
                if (author.equals ( "<unknown>" )){
                    author="未知艺术家";
                }
                /**
                 * 筛选非音乐音频文件
                 * 时长大于20秒
                 */
                if (duration>30000){
                    music.setMusic_name_String ( name );
                    music.setMusic_author_String ( author );
                    music.setMusic_path_String ( path );
                    music.setDuration ( duration );
                    oList.add ( music );
                }



            }
        }

        return oList;

    }
}
