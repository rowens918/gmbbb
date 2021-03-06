package com.example.android.gmsbigblackbox;

import android.app.Activity;
import android.app.Application;
import android.arch.lifecycle.ViewModelProviders;
import android.content.pm.ApplicationInfo;
import android.support.design.widget.CollapsingToolbarLayout;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.gmsbigblackbox.database.AppDatabase;
import com.example.android.gmsbigblackbox.database.NpcCardEntry;
import com.squareup.picasso.Picasso;

import java.util.List;


public class NpcCardDetailFragment extends Fragment {
    NpcCardEntry mItem;
    private AppDatabase mDb;
    CollapsingToolbarLayout appBarLayout;
    ImageView gameImageView;
    TextView gameTextView;

    public NpcCardDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle(R.string.app_name_abbreviated);
        mDb = AppDatabase.getsInstance(getContext());

        try {
            if (getArguments().containsKey(NpcCardDetailActivity.EXTRA_NPC_ID)) {
                int position = getArguments().getInt(NpcCardDetailActivity.EXTRA_NPC_ID, NpcCardDetailActivity.DEFAULT_NPC_ID);
                Log.d("POSITION: ", "position = " + position);
                loadNpc(position);
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.npccard_detail, container, false);
        ((TextView) rootView.findViewById(R.id.tv_detail_name)).setText(mItem.getNpcName());
        //((TextView) rootView.findViewById(R.id.tv_detail_game)).setText(mItem.getNpcGame());

        ((TextView) rootView.findViewById(R.id.tv_detail_role)).setText(mItem.getNpcRole());
        ((TextView) rootView.findViewById(R.id.tv_detail_stats)).setText(mItem.getNpcStats());
        ((TextView) rootView.findViewById(R.id.tv_detail_skills)).setText(mItem.getNpcSkills());
        ((TextView) rootView.findViewById(R.id.tv_detail_description)).setText(mItem.getNpcDescription());
        ((TextView) rootView.findViewById(R.id.tv_detail_history)).setText(mItem.getNpcHistory());
        ((TextView) rootView.findViewById(R.id.tv_detail_attitude)).setText(mItem.getNpcAttitude());
        gameImageView = rootView.findViewById(R.id.iv_detail_game);
        gameTextView = rootView.findViewById(R.id.tv_detail_game);
        gameTextView.setText(mItem.getNpcGame());

        final View frontCard = rootView.findViewById(R.id.cl_detail_front);
        final View backCard = rootView.findViewById(R.id.cl_detail_back);

        setGameUrl(mItem.getNpcGame());

        frontCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backCard.setVisibility(View.VISIBLE);
                frontCard.setVisibility(View.GONE);
            }
        });
        backCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                frontCard.setVisibility(View.VISIBLE);
                backCard.setVisibility(View.GONE);
            }
        });
        int wPosition = getArguments().getInt(NpcCardDetailActivity.EXTRA_NPC_ID, NpcCardDetailActivity.DEFAULT_NPC_ID);
        NpcWidget.setNpcWidgetText(getContext(), wPosition, mItem.getNpcName(), mItem.getNpcGame(), mItem.getNpcRole());
        setTitle();
        return rootView;
    }

    private void setGameUrl(String gameId) {
        String gameUrl = "";
        switch (gameId.toLowerCase()) {
            case ("d&d"):
            case ("dnd"):
            case ("dnd5e"):
                gameUrl = getString(R.string.dnd_image_url);
                break;
            case ("sr"):
            case ("sr4"):
            case ("sr5"):
            case ("shadowrun"):
                gameUrl = getString(R.string.sr_image_url);
                break;
        }
        if (!gameUrl.equals("")) {
            Picasso.get().load(gameUrl).into(gameImageView);
            gameTextView.setVisibility(View.GONE);
            gameImageView.setVisibility(View.VISIBLE);
        }
    }

    private void setTitle() {
        try {
            Activity activity = this.getActivity();
            appBarLayout = activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
                appBarLayout.setTitle(getString(R.string.app_name_abbreviated) + ": " + mItem.getNpcName());
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    private void loadNpc(final int NpcId) {
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run () {
                mItem = mDb.npcDao().loadNpcById(NpcId);
                Log.d("NPC Name: ", mItem.getNpcName());
            }
        });
    }
}
