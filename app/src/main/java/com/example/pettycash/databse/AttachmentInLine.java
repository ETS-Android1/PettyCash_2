package com.example.pettycash.databse;

import androidx.room.Embedded;
import androidx.room.Relation;

public class AttachmentInLine {
    @Embedded
    public LineModelViewDB line;
    @Relation(
            parentColumn = "id",
            entityColumn = "lineId"
    )
    public AttachmentModelView attachmentModelView;
}


