package me.majiajie.pagerbottomtabstriptest;

import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * Created by mjj on 2018/9/26
 */
public class NavigationComponentPageCFragment extends NavigationComponentPageAFragment {
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mTvText.setText("C");
    }
}