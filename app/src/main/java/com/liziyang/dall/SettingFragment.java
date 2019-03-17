package com.liziyang.dall;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.com.com.greendao.GreenDaoActivity;
import com.com.topbar.TopBarActivity;
import com.setting.more.LiXianDownActivity;

public class SettingFragment extends Fragment {
    private LinearLayout xaizai_layout;
    private LinearLayout greendao_layout,tongzhilan_layout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater , @Nullable ViewGroup container , @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate ( R.layout.setting_layout , container , false );
        xaizai_layout = view.findViewById ( R.id.xiazai_layout_id );
        greendao_layout = view.findViewById ( R.id.greendao_layout_id );
        tongzhilan_layout=view.findViewById ( R.id.tongzhilan_layout_id );
        setUI ();
        return view;
    }

    private void setUI() {
        tongzhilan_layout.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent ( getActivity (),TopBarActivity.class );
                startActivity ( intent );
            }
        } );
        greendao_layout.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent ( getActivity () , GreenDaoActivity.class );
                startActivity ( intent );
            }
        } );
        xaizai_layout.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
//                Toast.makeText ( getActivity (),"即将进入",Toast.LENGTH_SHORT ).show ();
                Intent intent = new Intent ( getActivity () , LiXianDownActivity.class );
                startActivity ( intent );
            }
        } );
    }
}
