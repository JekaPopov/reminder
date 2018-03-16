package ru.dravn.reminder;

import android.support.v4.app.Fragment;

/**
 * Created by Jeka on 15.03.2018.
 */

public class RemindListActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return new RemindListFragment();
    }
}
