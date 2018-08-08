package com.vaiki.android.warehouse;

import android.support.v4.app.Fragment;

/**
 * Created by E_not on 27.07.2018.
 */

public class Direct_activity_list extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new Direct_fragment_list();
    }
}
