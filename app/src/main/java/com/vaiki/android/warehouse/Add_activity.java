package com.vaiki.android.warehouse;

import android.support.v4.app.Fragment;

/**
 * Created by E_not on 27.07.2018.
 */

public class Add_activity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return new Add_fragment();
    }
}
