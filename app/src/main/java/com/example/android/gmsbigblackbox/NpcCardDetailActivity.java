package com.example.android.gmsbigblackbox;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.transition.Slide;
import android.util.Log;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;
import android.view.Window;
import android.widget.ImageView;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import java.io.InputStream;

import static android.content.ContentValues.TAG;

public class NpcCardDetailActivity extends AppCompatActivity {

    public static final String INSTANCE_NPC_ID = "instanceNPCID";
    public static final String EXTRA_NPC_ID = "extraNPCID";
    public static final int DEFAULT_NPC_ID = -1;

    private int mNpcId = DEFAULT_NPC_ID;

    private Toolbar mToolbar;
    private static ImageView mToolbarImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

         getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        getWindow().setExitTransition(new Slide());
        getWindow().setEnterTransition(new Slide());

        setContentView(R.layout.activity_npccard_detail);

        mToolbar = (Toolbar) findViewById(R.id.detail_toolbar);
        setSupportActionBar(mToolbar);

        String imgUrl = getString(R.string.toolbar_image_url);
        mToolbarImage = findViewById(R.id.iv_toolbar_image);
        //Picasso.get().load(imgUrl).placeholder(R.drawable.ic_launcher_background).into(mToolbarImage);

        new DownloadImageTask(mToolbarImage).execute(imgUrl);

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

    //Stock Overflow: https://stackoverflow.com/questions/34047120/collapsing-toolbar-with-image-from-url
    //Was having trouble getting images on the collapsable toolbar, only to find out it was
    //my emulator not downloading images.
    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return mIcon;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }
}
