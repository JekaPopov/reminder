package ru.dravn.reminder;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Jeka on 15.03.2018.
 */

public class RemindLab {

    private static RemindLab ourInstance;

    private List<Remind> mReminds;


    public static RemindLab getInstance(Context context)
    {
        if(ourInstance == null)
        {
            ourInstance = new RemindLab(context);
        }
        return ourInstance;
    }

    private RemindLab(Context context)
    {
        mReminds = new ArrayList<>();

        for (int i = 0; i <100 ; i++) {
            Remind remind = new Remind();
            remind.setTitle("Заметка "+i);
            remind.setSolved(i%2==0);
            mReminds.add(remind);
        }
    }

    public List<Remind> getReminds() {
        return mReminds;
    }

    public Remind getRemind(UUID id)
    {
        for (Remind remind:mReminds) {
            if(remind.getId().equals(id)) {
                return remind;
            }
        }
        return null;
    }
}
