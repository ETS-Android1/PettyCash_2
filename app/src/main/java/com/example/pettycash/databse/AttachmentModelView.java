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
  @ColumnInfo(name = "transId")
    public int transId;
    @ColumnInfo(name = "name")
    public String name;
    @ColumnInfo(name = "path")
    public  String path;


    public AttachmentModelView(int transId ,int lineId, String name, String path) {
        this.transId = transId;
        this.lineId = lineId;
        this.name = name;
        this.path = path;
    }


}


