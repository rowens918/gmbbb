package com.example.android.gmsbigblackbox;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.android.gmsbigblackbox.database.AppDatabase;
import com.example.android.gmsbigblackbox.database.NpcCardEntry;

import java.util.Date;

public class AddNpcCardActivity extends AppCompatActivity {

    public static final String INSTANCE_NPC_ID = "instanceNPCID";
    public static final String EXTRA_NPC_ID = "extraNPCID";
    private static final int DEFAULT_NPC_ID = -1;
    private int mNpcId = DEFAULT_NPC_ID;

    EditText mEditName;
    EditText mEditRole;
    EditText mEditGame;
    EditText mEditStats;
    EditText mEditSkills;
    EditText mEditDescription;
    EditText mEditHistory;
    EditText mEditAttitude;

    Button mButton;
    private AppDatabase mDb;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addnewnpccard);

        initViews();

        mDb = AppDatabase.getsInstance(getApplicationContext());

        if (savedInstanceState != null && savedInstanceState.containsKey(INSTANCE_NPC_ID)) {
            mNpcId = savedInstanceState.getInt(INSTANCE_NPC_ID, DEFAULT_NPC_ID);
        }

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(EXTRA_NPC_ID)) {
            mButton.setText(R.string.button_label_update);
            if (mNpcId == DEFAULT_NPC_ID) {
                //Populate UI
            }
        }
    }

    private void initViews() {
        mEditName = findViewById(R.id.tv_newnpccard_name);
        mEditRole = findViewById(R.id.tv_newnpccard_role);
        mEditGame = findViewById(R.id.tv_newnpccard_game);
        mEditStats = findViewById(R.id.tv_newnpccard_stats);
        mEditSkills = findViewById(R.id.tv_newnpccard_skills);
        mEditDescription = findViewById(R.id.tv_newnpccard_description);
        mEditHistory = findViewById(R.id.tv_newnpccard_history);
        mEditAttitude = findViewById(R.id.tv_newnpccard_attitude);

        mButton = findViewById(R.id.button_save_npc);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSaveButtonClicked();
            }
        });
    }

    private void populateUI(NpcCardEntry npcCard) {
        //Populate UI
    }

    public void onSaveButtonClicked() {
        String name = mEditName.getText().toString();
        String role = mEditRole.getText().toString();
        String game = mEditGame.getText().toString();
        String stats = mEditStats.getText().toString();
        String skills = mEditSkills.getText().toString();
        String description = mEditDescription.getText().toString();
        String history = mEditHistory.getText().toString();
        String attitude = mEditAttitude.getText().toString();

        Date date = new Date();

        final NpcCardEntry newNpc = new NpcCardEntry(name, role, game, stats, skills,
                description, history, attitude);
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                mDb.npcDao().insertNpcCard(newNpc);
                finish();
            }
        });
    }
}
