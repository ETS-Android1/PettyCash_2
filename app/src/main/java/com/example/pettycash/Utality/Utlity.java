package com.example.pettycash.Utality;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.example.pettycash.LineModelView;
import com.example.pettycash.databse.AppDatabase;
import com.example.pettycash.databse.AttachmentModelView;
import com.example.pettycash.databse.LineModelViewDB;
import com.example.pettycash.databse.LinesRepository;
import com.example.pettycash.databse.TransRepository;
import com.example.pettycash.databse.TransactionModelView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Utlity {
    public static String databaseName = "first";
    public static int databaseVersion = 1;
    public static String transId = "tran";
    public static int CAMERA_REQUEST_ID = 333;

    public static class TaskRunner {
        private final Executor executor = Executors.newSingleThreadExecutor(); // change according to your requirements
        private final Handler handler = new Handler(Looper.getMainLooper());

        public interface Callback<R> {
            void onComplete(R result);
        }


        public <R> void executeAsync(Callable<R> callable, Callback<R> callback) {
            executor.execute(() -> {
                final R result;
                try {
                    result = callable.call();
                    handler.post(() -> {
                        callback.onComplete(result);
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }

            });
        }
    }
    public static class AddTransCallable implements Callable<Integer> {
        TransRepository repository;
        TransactionModelView trans;

        public AddTransCallable(Application application) {
             repository = new TransRepository(application);
        }

        public AddTransCallable(Application application,TransactionModelView trans) {
            repository = new TransRepository(application);
            this.trans = trans;
        }
        @Override
        public Integer call() {
            // Some long running task
           repository.mtransDao.insertTransaction(trans);
           List<TransactionModelView> list = repository.mtransDao.getAllNotLive();
            Integer id =list.get(list.size()-1).id;
            Integer size = list.size();
            return id;
        }
    }
    public static class GetTransCallable implements Callable<TransactionModelView> {
        TransRepository repository;
        TransactionModelView trans;
        int transID = -1;
        public GetTransCallable(Application application , int transID) {
            repository = new TransRepository(application);
            this.transID = transID;
        }


        @Override
        public TransactionModelView call() {
            // Some long running task
            TransactionModelView transactionModelView =repository.mtransDao.getSingle(transID);

            return transactionModelView;
        }
    }

    public static class AddLinesCallable implements Callable<Integer> {
        private  AppDatabase db;
        LinesRepository linesRepository;
        List<LineModelViewDB> list= new ArrayList<>();
        List<List<AttachmentModelView>> attachList = new ArrayList<>();
        public AddLinesCallable(Application application) {
        }

        public AddLinesCallable(Application application, List<LineModelViewDB> list , List<List<AttachmentModelView>> attachList) {
            linesRepository = new LinesRepository(application);
            db = AppDatabase.getInstance(application);
            this.list.clear();
            this.list.addAll(list);
            this.attachList.clear();
            this.attachList.addAll(attachList);
        }
        @Override
        public Integer call() {
            // Some long running task
            Log.v("lineSizePara",String.valueOf(list.size()));
            Log.v("attachSizePara",String.valueOf(attachList.size()));

            double totalAmount =0;
            int transIdfromLine =-1;
            Integer size = list.size();
            int i =0;
            int currId;
            while (i <= size-1){
                linesRepository.addLine(list.get(i));
                totalAmount += list.get(i).amount;
                transIdfromLine = list.get(i).transactionId;

                Log.v("item "+i+":", String.valueOf(linesRepository.mLinesDAO.getAll().get(linesRepository.mLinesDAO.getAll().size()-1).transactionId));
                currId =linesRepository.mLinesDAO.getAll().get(linesRepository.mLinesDAO.getAll().size()-1).id;
                int currtransId = linesRepository.mLinesDAO.getAll().get(linesRepository.mLinesDAO.getAll().size() - 1).transactionId;
                List<AttachmentModelView> currenAttachList =attachList.get(i);
                int j =0;
//                Log.v("curDocS", String.valueOf(current.docsList.size()));
                while (j <= currenAttachList.size()-1){

                    AttachmentModelView attachVM = currenAttachList.get(j);
                    attachVM.transId = currtransId;
                    attachVM.lineId=currId;
//                    Log.v("curDocItem"+j+"", attachVM.name);

                    db.attachmentDao().insertAttachment(attachVM);


                    j++;
                }

                i++;
            }
        Log.v("total_amount",totalAmount+"");
            db.transDao().updateTotalAmount(transIdfromLine,totalAmount);
            Integer dbSize =linesRepository.mLinesDAO.getAll().size();
//            db.attachmentDao().delelteAll();
//            db.linesDao().delelteAll();
//            db.transDao().delelteAll();
            return dbSize;
        }
    }

    public static class GetLineCallable implements Callable<List<LineModelView>> {
        AppDatabase db;
        TransactionModelView trans;
        int transID = -1;
        List<LineModelView> linesList;
        public GetLineCallable(Application application , int transID) {
            db = AppDatabase.getInstance(application);
            this.transID = transID;
            linesList = new ArrayList<>();
        }


        @Override
        public List<LineModelView> call() {
            // Some long running task
            Log.v("getListTrans ID",transID+"");
            List<LineModelViewDB> listDB =db.linesDao().ListofLines(transID);
            Log.v("getListSize",listDB.size()+"");

            if (listDB.size() > 0 ){
                for (int i = 0 ; i<listDB.size();i++) {
                    LineModelViewDB currentDB = listDB.get(i);
                    LineModelView lineModelView = Utlity.dbToJavaLMV(currentDB);
                    lineModelView.docsList.addAll(db.attachmentDao().getAllbyTransId(transID));
                    linesList.add(lineModelView);
                }

            }

            return linesList;
        }
    }

    private static LineModelView dbToJavaLMV(LineModelViewDB currentDB) {
        LineModelView lineModelView = new LineModelView(currentDB.transactionId,currentDB.category,currentDB.unit,currentDB.item,currentDB.quantity,currentDB.price,currentDB.amount,currentDB.supplierName,currentDB.invoiceNumber,currentDB.invoiceDate,currentDB.billedToCustomer,currentDB.invoiceDate,currentDB.cbsCode,currentDB.expenditureType);
        return lineModelView;

    }

    public static class AddAttachmentCallable implements Callable<Integer> {
        List<AttachmentModelView> list= new ArrayList<>();
        AppDatabase db ;

        public AddAttachmentCallable(Application application) {
        }

        public AddAttachmentCallable(Application application, List<AttachmentModelView> list) {
            db = AppDatabase.getInstance(application);
            this.list.clear();
            this.list.addAll(list);
        }
        @Override
        public Integer call() {
            // Some long running task
            Log.v("attachSizeS",String.valueOf(db.attachmentDao().getAll().size()));
            Log.v("attachSizePara",String.valueOf(list.size()));

            Integer size = list.size();
            int i =0;
            while (i <= size-1){
                db.attachmentDao().insertAttachment(list.get(i));
                Log.v("attachitem "+i+":", String.valueOf(db.attachmentDao().getAll().get(db.attachmentDao().getAll().size()-1).lineId));
                i++;
            }

            Integer dbSize =db.attachmentDao().getAll().size();

            return dbSize;
        }
    }

    public static class GetAttachCallable implements Callable<List<List<AttachmentModelView>>>{
        AppDatabase db ;
        public GetAttachCallable(Application application) {
            db=AppDatabase.getInstance(application);
        }

        @Override
        public List<List<AttachmentModelView>> call() {
            int size= 0;
            List<AttachmentModelView> current = new ArrayList<>();
            List<List<AttachmentModelView>> all = new ArrayList<>();
            int i =0;
            while (i<size){

            }

            return null;
        }
    }
}



