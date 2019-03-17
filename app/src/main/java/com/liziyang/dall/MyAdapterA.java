package com.liziyang.dall;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class MyAdapterA extends FragmentPagerAdapter {
    private int num;

    More_first more_first;
    More_sencondly more_sencondly;
    More_thirdly more_thirdly;
    public MyAdapterA(FragmentManager fm,int num) {
        super ( fm );
        this.num=num;
    }

    @Override
    public Fragment getItem(int i) {
        switch (i){
            case 0:
                if (more_first==null){
                    return new More_first ();
                }


            case 1:
                if (more_sencondly==null){
                    return new More_sencondly ();
                }

            case 2:
                if (more_thirdly==null){
                    return new More_thirdly ();
                }

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return num;
    }
}
