package com.example.android.gmsbigblackbox;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.example.android.gmsbigblackbox.database.AppDatabase;
import com.example.android.gmsbigblackbox.database.NpcCardEntry;

public class NewNpcViewModel extends ViewModel {

    private NpcCardEntry mNpcCard;

    public NewNpcViewModel(AppDatabase database, int mNpcId) {
        mNpcCard = database.npcDao().loadNpcById(mNpcId);
    }
    public NpcCardEntry getNpcCard() {
        return mNpcCard;
    }
}
