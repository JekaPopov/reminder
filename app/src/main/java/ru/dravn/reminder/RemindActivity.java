package ru.dravn.reminder;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.Menu;
import android.view.MenuItem;

import java.util.UUID;

public class RemindActivity extends SingleFragmentActivity {

    private static final String EXTRA_REMIND_ID = "ru.dravn.reminder.remind_id";


    @Override
    protected Fragment createFragment() {
        UUID remindId =(UUID) getIntent()
                .getSerializableExtra(EXTRA_REMIND_ID);

        return RemindFragment.newInstance(remindId);
    }


    public static Intent newIntent(Context context, UUID remindId) {
        Intent intent = new Intent(context, RemindActivity.class);
        intent.putExtra(EXTRA_REMIND_ID, remindId);
        return intent;
    }
}
