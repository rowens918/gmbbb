package com.example.android.gmsbigblackbox.database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "npcs")
public class NpcCardEntry {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String NpcName;
    private String NpcRole;
    private String NpcGame;
    private String NpcStats;
    private String NpcSkills;
    private String NpcDescription;
    private String NpcHistory;
    private String NpcAttitude;
    @ColumnInfo(name = "last_updated")
    private Date LastUpdated;

    @Ignore
    public NpcCardEntry(String NpcName, String NpcRole, String NpcGame,
                        String NpcStats, String NpcSkills, String NpcDescription,
                        String NpcHistory, String NpcAttitude) {
        this.NpcName = NpcName;
        this.NpcRole = NpcRole;
        this.NpcGame = NpcGame;
        this.NpcStats = NpcStats;
        this.NpcSkills = NpcSkills;
        this.NpcDescription = NpcDescription;
        this.NpcHistory = NpcHistory;
        this.NpcAttitude = NpcAttitude;
    }

    public NpcCardEntry(int id, String NpcName, String NpcRole, String NpcGame,
                        String NpcStats, String NpcSkills, String NpcDescription,
                        String NpcHistory, String NpcAttitude, Date LastUpdated) {
        this.id = id;
        this.NpcName = NpcName;
        this.NpcRole = NpcRole;
        this.NpcGame = NpcGame;
        this.NpcStats = NpcStats;
        this.NpcSkills = NpcSkills;
        this.NpcDescription = NpcDescription;
        this.NpcHistory = NpcHistory;
        this.NpcAttitude = NpcAttitude;
        this.LastUpdated = LastUpdated;
    }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getNpcName() { return NpcName; }

    public void setNpcName(String newNpcName) { this.NpcName = newNpcName; }

    public String getNpcRole() { return NpcRole; }

    public void setNpcRole(String newNpcRole) { this.NpcRole = newNpcRole; }

    public String getNpcGame() { return NpcGame; }

    public void setNpcGame(String newNpcGame) { this.NpcGame = newNpcGame; }

    public String getNpcStats() { return NpcStats; }

    public void setNpcStats(String newNpcStats) { this.NpcStats = newNpcStats; }

    public String getNpcSkills() { return NpcSkills; }

    public void setNpcSkills(String newNpcSkills) { this.NpcSkills = newNpcSkills; }

    public String getNpcDescription() { return NpcDescription; }

    public void setNpcDescription(String newNpcDescription) { this.NpcDescription = newNpcDescription; }

    public String getNpcHistory() { return NpcHistory; }

    public void setNpcHistory(String newNpcHistory) { this.NpcHistory = newNpcHistory; }

    public String getNpcAttitude() { return NpcAttitude; }

    public void setNpcAttitude(String newNpcAttitude) { this.NpcAttitude = newNpcAttitude; }

    public Date getLastUpdated() { return LastUpdated; }

    public void setLastUpdated(Date newLastUpdated) { this.LastUpdated = newLastUpdated; }
}
