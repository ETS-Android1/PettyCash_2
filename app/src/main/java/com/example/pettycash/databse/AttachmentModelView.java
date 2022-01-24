package com.example.pettycash.databse;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
@Entity
public class AttachmentModelView {
    @PrimaryKey(autoGenerate = true)
    public int id;
    @ColumnInfo(name = "lineId")
    public int lineId;
    @ColumnInfo(name = "name")
    public String name;
    @ColumnInfo(name = "path")
    public  String path;


    public AttachmentModelView(int lineId, String name, String path) {
        this.lineId = lineId;
        this.name = name;
        this.path = path;
    }


}


