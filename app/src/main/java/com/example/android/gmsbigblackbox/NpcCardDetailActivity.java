package com.example.android.gmsbigblackbox;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;

public class NpcCardDetailActivity extends AppCompatActivity {

    public static final String INSTANCE_NPC_ID = "instanceNPCID";
    public static final String EXTRA_NPC_ID = "extraNPCID";
    public static final int DEFAULT_NPC_ID = -1;

    private int mNpcId = DEFAULT_NPC_ID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_npccard_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        if (savedInstanceState != null && savedInstanceState.containsKey(INSTANCE_NPC_ID)) {
            mNpcId = savedInstanceState.getInt(INSTANCE_NPC_ID, DEFAULT_NPC_ID);
        }

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(EXTRA_NPC_ID)) {
            if (mNpcId == DEFAULT_NPC_ID) {
                // Populate UI
                mNpcId = intent.getIntExtra(EXTRA_NPC_ID, DEFAULT_NPC_ID);
            }
        }

        if (savedInstanceState == null) {
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.
            Bundle arguments = new Bundle();
            arguments.putInt(EXTRA_NPC_ID, mNpcId);
            NpcCardDetailFragment fragment = new NpcCardDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.npccard_detail_container, fragment)
                    .commit();
        }

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(INSTANCE_NPC_ID, mNpcId);
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            navigateUpTo(new Intent(this, NpcCardListActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
