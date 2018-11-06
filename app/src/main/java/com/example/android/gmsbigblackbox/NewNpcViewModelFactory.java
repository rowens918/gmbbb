package com.example.android.gmsbigblackbox;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;

import com.example.android.gmsbigblackbox.database.AppDatabase;

public class NewNpcViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private final AppDatabase mDb;
    private final int mNpcId;

    public NewNpcViewModelFactory (AppDatabase database, int npcId) {
        this.mDb = database;
        this.mNpcId = npcId;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        return (T) new NewNpcViewModel(mDb, mNpcId);
    }
}
