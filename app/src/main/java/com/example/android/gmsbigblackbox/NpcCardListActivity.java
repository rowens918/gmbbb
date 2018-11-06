package com.example.android.gmsbigblackbox;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import com.example.android.gmsbigblackbox.database.AppDatabase;
import com.example.android.gmsbigblackbox.database.NpcCardEntry;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.util.List;

import static android.support.v7.widget.RecyclerView.VERTICAL;

/**
 * An activity representing a list of NpcCards. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link NpcCardDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class NpcCardListActivity extends AppCompatActivity implements NpcAdapter.ItemClickListener {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;
    private NpcAdapter mAdapter;
    private RecyclerView recyclerView;
    private AdView mAdView;

    private AppDatabase mDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_npccard_list);

        MobileAds.initialize(this, "ca-app-pub-3940256099942544~3347511713");
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        mAdView.loadAd(adRequest);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        if (findViewById(R.id.npccard_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }



        recyclerView = findViewById(R.id.npccard_list);
        assert recyclerView != null;
        mAdapter = new NpcAdapter(this, this);
        recyclerView.setAdapter(mAdapter);

        DividerItemDecoration decoration = new DividerItemDecoration(getApplicationContext(), VERTICAL);
        ((RecyclerView) recyclerView).addItemDecoration(decoration);


        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(final RecyclerView.ViewHolder viewHolder, int direction) {
                AppExecutors.getInstance().diskIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        int position = viewHolder.getAdapterPosition();
                        List<NpcCardEntry> npcs = mAdapter.getNpcs();
                        mDb.npcDao().deleteNpcCard(npcs.get(position));
                    }
                });
            }
        }).attachToRecyclerView((RecyclerView) recyclerView);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addNpcIntent = new Intent(NpcCardListActivity.this, AddNpcCardActivity.class);
                startActivity(addNpcIntent);
            }
        });

        mDb = AppDatabase.getsInstance(getApplicationContext());

        setViewModel();

    }

    void setViewModel() {
        MainViewModel viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        viewModel.getNpcs().observe(this, new Observer<List<NpcCardEntry>>() {
            @Override
            public void onChanged(@Nullable List<NpcCardEntry> npcCardEntries) {
                mAdapter.setNpcs(npcCardEntries);
            }
        });
    }

    @Override
    public void onItemClickListener(int itemId) {
        if (mTwoPane) {
            Bundle arguments = new Bundle();
            arguments.putInt(NpcCardDetailActivity.EXTRA_NPC_ID, itemId);
            NpcCardDetailFragment fragment = new NpcCardDetailFragment();
            fragment.setArguments(arguments);
            NpcCardListActivity mParentActivity = this;
            mParentActivity.getSupportFragmentManager().beginTransaction()
                    .replace(R.id.npccard_detail_container, fragment)
                    .commit();
        } else {
            Intent intent = new Intent(NpcCardListActivity.this, NpcCardDetailActivity.class);
            intent.putExtra(NpcCardDetailActivity.EXTRA_NPC_ID, itemId);
            startActivity(intent);
        }
    }
}
