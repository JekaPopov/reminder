package ru.dravn.reminder;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import java.text.DateFormat;
import java.text.Format;
import java.util.UUID;
import java.util.zip.DataFormatException;

/**
 * Created by Jeka on 15.03.2018.
 */

public class RemindFragment extends Fragment{

    private static final String ARG_REMIND_ID = "remind_id";

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
        mDataButton.setText(mRemind.getDate().toString());
        mDataButton.setEnabled(false);

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
}
