package com.example.android.gmsbigblackbox;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.example.android.gmsbigblackbox.database.AppDatabase;
import com.example.android.gmsbigblackbox.database.NpcCardEntry;

import java.util.List;

public class MainViewModel extends AndroidViewModel {

    private LiveData<List<NpcCardEntry>> npcs;

    public MainViewModel(@NonNull Application application) {
        super(application);

        AppDatabase database = AppDatabase.getsInstance(this.getApplication());
        npcs = database.npcDao().loadAllNpcs();
    }

    public LiveData<List<NpcCardEntry>> getNpcs() {
        return npcs;
    }
}
