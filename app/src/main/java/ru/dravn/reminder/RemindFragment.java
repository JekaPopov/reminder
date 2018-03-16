package ru.dravn.reminder;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import java.util.Date;
import java.util.UUID;

/**
 * Created by Jeka on 15.03.2018.
 */

public class RemindFragment extends Fragment{

    private static final String ARG_REMIND_ID = "remind_id";
    private static final String DIALOG_DATA = "DialogData";
    private static final int REQUEST_DATA  = 0;

    private Remind mRemind;
    private EditText mTitleField;
    private Button mDataButton;
    private CheckBox mSolvedCheckBox;

     public static RemindFragment newInstance(UUID remindID)
     {
         Bundle args = new Bundle();
         args.putSerializable(ARG_REMIND_ID, remindID);

         RemindFragment fragment = new RemindFragment();
         fragment.setArguments(args);
         return fragment;
     }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode != Activity.RESULT_OK)
        {
            return;
        }

        if(requestCode == REQUEST_DATA)
        {
            Date date = (Date) data
                    .getSerializableExtra(DatePickerFragment.EXTRA_DATA);
            mRemind.setDate(date);
            updateData();
        }
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UUID remindId = (UUID)getArguments().getSerializable(ARG_REMIND_ID);
        mRemind = RemindLab.getInstance(getActivity()).getRemind(remindId);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_remind, container,false);

        mTitleField = view.findViewById(R.id.remind_title);
        mTitleField.setText(mRemind.getTitle());

        mTitleField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mRemind.setTitle(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        mDataButton = view.findViewById(R.id.remind_data);
        updateData();
        mDataButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getFragmentManager();
                DatePickerFragment dialog = DatePickerFragment.newInstance(mRemind.getDate());
                dialog.setTargetFragment(RemindFragment.this,REQUEST_DATA);
                dialog.show(fm, DIALOG_DATA);
            }
        });

        mSolvedCheckBox = view.findViewById(R.id.remind_solved);
        mSolvedCheckBox.setChecked(mRemind.isSolved());
        mSolvedCheckBox.setOnCheckedChangeListener
                (new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged
                    (CompoundButton buttonView, boolean isChecked) {
                mRemind.setSolved(isChecked);
            }
        });

        return view;
    }


    private void updateData() {
        mDataButton.setText(mRemind.getDate().toString());
    }
}
