package com.example.pettycash.databse;

import androidx.room.Embedded;
import androidx.room.Relation;

public class linesInTransaction {
    @Embedded
    public TransactionModelView tran;
    @Relation(
            parentColumn = "id",
            entityColumn = "transId"
    )
    public LineModelView lineModelView;


}
