package ru.dravn.reminder;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by Jeka on 15.03.2018.
 */

public class RemindListFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private RemindAdapter mAdapter;
    private int mUpdatePosition;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_remind_list,
                container,false);

        mRecyclerView = view.findViewById(R.id.remind_recycle_view);
        mRecyclerView.setLayoutManager(
                new LinearLayoutManager(getActivity()));

        updateUI();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    private void updateUI() {
        RemindLab remindLab = RemindLab.getInstance(getActivity());
        List<Remind> reminds = remindLab.getReminds();

        if (mAdapter == null) {
            mAdapter = new RemindAdapter(reminds);
            mRecyclerView.setAdapter(mAdapter);
        }else
        {

            //mAdapter.notifyItemChanged(mUpdatePosition);
            mAdapter.notifyDataSetChanged();
        }
    }

    private class RemindHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener
    {

        private TextView mTitleTextView;
        private TextView mDataTextView;
        private CheckBox mSolvedCheckBox;
        private Remind mRemind;

        public RemindHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            mTitleTextView = itemView.findViewById(R.id.list_item_title_text_view);
            mDataTextView = itemView.findViewById(R.id.list_item_data_text_view);
            mSolvedCheckBox = itemView.findViewById(R.id.list_item_remind_solved_box);
        }

        public void bindRemind(Remind remind)
        {
            mRemind = remind;
            mTitleTextView.setText(mRemind.getTitle());
            mDataTextView.setText(mRemind.getDate().toString());
            mSolvedCheckBox.setChecked(mRemind.isSolved());

        }

        @Override
        public void onClick(View v) {
            mUpdatePosition = getAdapterPosition();
            Intent intent = RemindPagerActivity.newIntent(getActivity(), mRemind.getId());
            startActivity(intent);
        }
    }


    private class RemindAdapter extends RecyclerView.Adapter<RemindHolder>
    {
        private List<Remind> mReminds;

        public RemindAdapter(List<Remind> reminds) {
            mReminds = reminds;
        }



        @Override
        public RemindHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());

            View view = layoutInflater.inflate(
                    R.layout.list_item_remind,
                    parent,false);

            return new RemindHolder(view);
        }

        @Override
        public void onBindViewHolder(RemindHolder holder, int position) {
        Remind remind = mReminds.get(position);
        holder.bindRemind(remind);
        }

        @Override
        public int getItemCount() {
            return mReminds.size();
        }
    }
}
