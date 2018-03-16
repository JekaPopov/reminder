package ru.dravn.reminder;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

import java.util.List;
import java.util.UUID;

/**
 * Created by Jeka on 16.03.2018.
 */

public class RemindPagerActivity extends FragmentActivity {

    private static final String EXTRA_REMIND_ID = "ru.dravn.reminder.remind_id";

    private ViewPager mViewPager;
    private List<Remind> mReminds;


    public static Intent newIntent(Context context, UUID remindID)
    {
        Intent intent = new Intent(context, RemindPagerActivity.class);
        intent.putExtra(EXTRA_REMIND_ID, remindID);
        return intent;

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remind_pager);

        UUID remindId = (UUID) getIntent().getSerializableExtra(EXTRA_REMIND_ID);

        mViewPager = findViewById(R.id.activity_remind_pager_view_pager);

        mReminds = RemindLab.getInstance(this).getReminds();

        FragmentManager fm = getSupportFragmentManager();

        mViewPager.setAdapter(new FragmentStatePagerAdapter(fm) {
            @Override
            public Fragment getItem(int position) {
                Remind remind = mReminds.get(position);
                return RemindFragment.newInstance(remind.getId());
            }

            @Override
            public int getCount() {
                return mReminds.size();
            }
        });

        for (int i = 0;i < mReminds.size(); i++) {
            if(mReminds.get(i).getId().equals(remindId)) {
                mViewPager.setCurrentItem(i);
                break;
            }

        }

    }


}
