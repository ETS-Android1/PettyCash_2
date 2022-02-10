package com.example.pettycash;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.PermissionChecker;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.example.pettycash.Utality.Utlity;
import com.example.pettycash.databse.AttachmentModelView;
import com.example.pettycash.databse.LineModelViewDB;
import com.google.common.collect.Range;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import static com.basgeekball.awesomevalidation.ValidationStyle.COLORATION;

public class AddLine extends AppCompatActivity implements View.OnClickListener, ActivityCompat.OnRequestPermissionsResultCallback {

    public LineModelView current;
    public AttachmentAdapter.ViewHolder currentItemView;
    RecyclerView lines_recyclerView;
    AddLineFragmentListAdapter adapter;
    RelativeLayout categoryLayout;

    ImageButton cancelBtn,addLineBtn , continueBtn,saveAndCloseBtn;


    FragmentManager fragmentManager;
      View selectFragment,typeFragment,imageFragment;
      List<LineModelView> lineList = new ArrayList<>();
    ActivityResultLauncher<Intent> someActivityResultLauncher;
    int attachPos = -1;
    boolean camere;
    Uri photoURI;
    int currentTransID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_line);

        Log.v("transIdforTrans", String.valueOf(getIntent().getIntExtra(Utlity.transId,-1)));
        currentTransID = getIntent().getIntExtra(Utlity.transId,-1);


        cancelBtn = findViewById(R.id.add_line_cancel_btn);
        addLineBtn = findViewById(R.id.add_line_add_more_btn);
     continueBtn = findViewById(R.id.add_line_continue);
        saveAndCloseBtn = findViewById(R.id.add_line_save_and_close_btn);


       addLineBtn.setOnClickListener(this);
        cancelBtn.setOnClickListener(this);
         continueBtn.setOnClickListener(this);
        saveAndCloseBtn.setOnClickListener(this);

        selectFragment = findViewById(R.id.add_line_select_fragment);
        typeFragment = findViewById(R.id.add_line_type_fragment);
        typeFragment = findViewById(R.id.add_line_type_fragment);
        imageFragment = findViewById(R.id.add_line_Image_fragment);

        fragmentManager =getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .add(new SelectFragment(),"empty")
                .commit();
        selectFragment.setVisibility(View.GONE);
        typeFragment.setVisibility(View.GONE);
        imageFragment.setVisibility(View.GONE);

        lineList.add(new LineModelView(0));
        adapter = new AddLineFragmentListAdapter(this,this,lineList);

        lines_recyclerView = findViewById(R.id.add_line_list_view);
        lines_recyclerView.setLayoutManager(new LinearLayoutManager(this));
        lines_recyclerView.setAdapter(adapter);

        enableValidate();

        someActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            // There are no request codes
                            Intent data = result.getData();

                            Log.v("attachPos", String.valueOf(attachPos));
                            if (attachPos >-1) {
                                if (camere) {
//                                    Bitmap imageBitmap = (Bitmap) result.getData().getExtras().get("data");
//                                    cancelBtn.setImageBitmap(imageBitmap);
//                                    galleryAddPic(getContentResolver(),(Bitmap) result.getData().getExtras().get("data"),"1.1","des");
                                    LineModelView current = adapter.lineModelViews.get(attachPos);
                                    String name = String.valueOf(attachPos+1+"."+(current.docsList.size()+1));
                                    AttachmentModelView attachmentModelView = new AttachmentModelView(-1,-1,name,photoURI.toString());
                                    current.docsList.add(attachmentModelView);

//                                    adapter.notifyDataSetChanged();
//                                    LineModelView current = adapter.lineModelViews.get(attachPos);
//                                    Log.v("imagePath", (String) result.getData().getExtras().get("data"));
//
//                                    current.docsList.add((Uri) result.getData().getExtras().get("data"));
                                    adapter.notifyDataSetChanged();

                                }else {


                                    Log.v("imagePath", data.getData().toString());


                                    LineModelView current = adapter.lineModelViews.get(attachPos);
                                    String name = String.valueOf(attachPos+1+"."+(current.docsList.size()+1));

                                    AttachmentModelView attachmentModelView = new AttachmentModelView(-1,-1,name,data.getData().toString());

                                    current.docsList.add(attachmentModelView);
                                    adapter.notifyDataSetChanged();
                                }
                                adapter.updateDocs();

                            }
                        }
                    }
                });




    }

    private void enableValidate() {
//        AwesomeValidation mAwesomeValidation = new AwesomeValidation(COLORATION);
//        mAwesomeValidation.setColor(Color.RED);
//        AwesomeValidation.disableAutoFocusOnFirstFailure();
//
//        mAwesomeValidation.addValidation(this, R.id.line_recycle_category_choose_text, Range.closed(0.01f, 2.72f), R.string.err_choose);
    }


    private void galleryAddPic() {

        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File("null");
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        this.sendBroadcast(mediaScanIntent);


    }
    private void setPic(String path) {
        // Get the dimensions of the View
        int targetW = cancelBtn.getWidth();
        int targetH = cancelBtn.getHeight();

        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;

        BitmapFactory.decodeFile(path, bmOptions);

        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        // Determine how much to scale down the image
        int scaleFactor = Math.max(1, Math.min(photoW/targetW, photoH/targetH));

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;

        Bitmap bitmap = BitmapFactory.decodeFile(path, bmOptions);
        cancelBtn.setImageBitmap(bitmap);
    }

    public  final String insertImage(ContentResolver cr,
                                           Bitmap source,
                                           String title,
                                           String description) {

        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, title);
        values.put(MediaStore.Images.Media.DISPLAY_NAME, title);
        values.put(MediaStore.Images.Media.DESCRIPTION, description);
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/png");
        // Add the date meta data to ensure the image is added at the front of the gallery
        values.put(MediaStore.Images.Media.DATE_ADDED, System.currentTimeMillis());
        values.put(MediaStore.Images.Media.DATE_TAKEN, System.currentTimeMillis());

        Uri url = null;
        String stringUrl = null;    /* value to be returned */

        try {
            url = cr.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

            if (source != null) {
                OutputStream imageOut = cr.openOutputStream(url);
                try {
                    Bitmap image = MediaStore.Images.Media.getBitmap(cr,url);

                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inPreferredConfig = Bitmap.Config.ARGB_8888;

                    image.compress(Bitmap.CompressFormat.PNG, 100, imageOut);
                } finally {
                    imageOut.close();
                }

                long id = ContentUris.parseId(url);
                // Wait until MINI_KIND thumbnail is generated.
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inPreferredConfig = Bitmap.Config.ARGB_8888;

                Bitmap miniThumb = MediaStore.Images.Thumbnails.getThumbnail(cr, id, MediaStore.Images.Thumbnails.MINI_KIND, options);
                // This is for backward compatibility.
                storeThumbnail(cr, miniThumb, id, 50F, 50F, MediaStore.Images.Thumbnails.MICRO_KIND);
            } else {
                cr.delete(url, null, null);
                url = null;
            }
        } catch (Exception e) {
            if (url != null) {
                cr.delete(url, null, null);
                url = null;
            }
        }

        if (url != null) {
            stringUrl = url.toString();
        }


        Log.v("imgPath",stringUrl);
        return stringUrl;
    }

    /**
     * A copy of the Android internals StoreThumbnail method, it used with the insertImage to
     * populate the android.provider.MediaStore.Images.Media#insertImage with all the correct
     * meta data. The StoreThumbnail method is private so it must be duplicated here.
     * @see android.provider.MediaStore.Images.Media (StoreThumbnail private method)
     */
    private  final Bitmap storeThumbnail(
            ContentResolver cr,
            Bitmap source,
            long id,
            float width,
            float height,
            int kind) {

        // create the matrix to scale it
        Matrix matrix = new Matrix();

        float scaleX = width / source.getWidth();
        float scaleY = height / source.getHeight();

        matrix.setScale(scaleX, scaleY);

        Bitmap thumb = Bitmap.createBitmap(source, 0, 0,
                source.getWidth(),
                source.getHeight(), matrix,
                true
        );

        ContentValues values = new ContentValues(4);
        values.put(MediaStore.Images.Thumbnails.KIND,kind);
        values.put(MediaStore.Images.Thumbnails.IMAGE_ID,(int)id);
        values.put(MediaStore.Images.Thumbnails.HEIGHT,thumb.getHeight());
        values.put(MediaStore.Images.Thumbnails.WIDTH,thumb.getWidth());

        Uri url = cr.insert(MediaStore.Images.Thumbnails.EXTERNAL_CONTENT_URI, values);

        try {
            OutputStream thumbOut = cr.openOutputStream(url);
//            thumb.compress(Bitmap.CompressFormat.JPEG, 100, thumbOut);
            thumbOut.close();
            return thumb;
        } catch (FileNotFoundException ex) {
            return null;
        } catch (IOException ex) {
            return null;
        }


    }





    public  void visibleFragment (){
//        fragment.setVisibility(View.VISIBLE);

    }


    public void hideFragment() {
        selectFragment.setVisibility(View.GONE);
        typeFragment.setVisibility(View.GONE);


    }
    public String getStringVal(int id){
        return getString(id);
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.add_line_cancel_btn:
                finish();
                break;

            case R.id.add_line_add_more_btn:
                LineModelView previous =adapter.lineModelViews.get(adapter.lineModelViews.size()-1);
                Log.v("prev :", "pos: " + previous.position + " cat : " + previous.category + " item : " + previous.item + " unit : " + previous.unit + " price : " + previous.price + " quantity : " + previous.quantity + " amount : "+previous.amount + " vat : " + previous.vatInvoiceNumber);

                for (int i =0; i<adapter.lineModelViews.size();i++){
                    LineModelView newLine = adapter.lineModelViews.get(i);
                            Log.v("new Line "+i+" :", "pos: " + newLine.position + " cat : " + newLine.category + " item : " + newLine.item + " unit : " + newLine.unit + " price : " + newLine.price + " quantity : " + newLine.quantity + " amount : "+newLine.amount + " vat : " + newLine.vatInvoiceNumber);

                }

                if (adapter.viewHolder.checkValidate(previous)) {

                    LineModelView newLine = new LineModelView(previous.position + 1, previous.category, previous.unit, previous.item);
                    Log.v("new Line", "pos: " + newLine.position + " cat : " + newLine.category + " item : " + newLine.item + " unit : " + newLine.unit + " price : " + newLine.price + " quantity : " + newLine.quantity);
                    adapter.lineModelViews.add(newLine);
                    int i = 0;
                    while (i < adapter.lineModelViews.size()) {
                        Log.v("item " + i, String.valueOf(adapter.lineModelViews.get(i).position));
                        i++;
                    }
//                Collections.sort(lineList, (o1, o2) -> String.valueOf(o1.position).compareTo(String.valueOf(o2.position)));
//                i=0;
//                while (i<lineList.size()){
//                    Log.v("item "+i,String.valueOf(lineList.get(i).position));
//                    i++;
//                }
                    adapter.notifyDataSetChanged();
                    lines_recyclerView.smoothScrollToPosition(adapter.lineModelViews.size()-1);
                }else{

                    final CharSequence[] options = { "Okey" };

                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle(getResources().getString(R.string.all_red_feild_required));
                    builder.setItems(options, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }


                    });
                    builder.show();
                }
                break;

            case R.id.add_line_continue:
//                if (adapter.viewHolder.checkValidateForAll()) {
                    insetrAllLinesToDB();
//                }
                break;

            case R.id.add_line_save_and_close_btn:
        }
    }



    void insetrAllLinesToDB(){

        int i = 0;
        List<LineModelViewDB> lineModelViewDBList = new ArrayList<>();
        List<List<AttachmentModelView>> attachList = new ArrayList<>();
        while (i <= adapter.lineModelViews.size()-1){
            LineModelView current = adapter.lineModelViews.get(i);
            Log.v("date of line before",current.invoiceDate+"");
            LineModelViewDB lineModelViewDB = new LineModelViewDB(currentTransID,current.category,current.unit,current.item,current.quantity,current.price,current.amount,current.supplierName,current.invoiceNumber,current.vatInvoiceNumber,current.billedToCustomer,current.invoiceDate,current.cbsCode,current.expenditureType);
            Log.v("libeIdBefore", String.valueOf(lineModelViewDB.id));
            lineModelViewDBList.add(lineModelViewDB);



                attachList.add(current.docsList);



            i++;
        }


        Log.v("beforeLineList",String.valueOf(lineModelViewDBList.size()));
        Log.v("beforeAttachList",String.valueOf(attachList.size()));



        new Utlity.TaskRunner().executeAsync(new Utlity.AddLinesCallable(this.getApplication(),lineModelViewDBList ,attachList),(data) ->{

            Log.v("lineSizeF",String.valueOf(data));
            Intent toReview = new Intent(this, TransactionReview.class);
            toReview.putExtra(Utlity.transId, currentTransID);

            startActivity(toReview);

        });
//        new Utlity.TaskRunner().executeAsync(new Utlity.AddAttachmentCallable(this.getApplication(),attachList),(data) ->{
//            Log.v("AttachSizeF",String.valueOf(data));
//
//        });




    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.v("only","addLine");

        Log.v("pre0",permissions[0]+" = "+grantResults[0]);
        Log.v("pre1",permissions[1]+" = "+grantResults[1]);

        if ( grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Log.v("pre0",permissions[0]+" = "+grantResults[0]);
            if ( grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                Log.v("pre1",permissions[1]+" = "+grantResults[1]);


                if (requestCode == Utlity.CAMERA_REQUEST_ID) {
                    adapter.isGranted=true;
                    SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);

                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putBoolean(String.valueOf(Utlity.CAMERA_REQUEST_ID),adapter.isGranted).apply();
                    adapter.viewHolder.openFiles();
                }
            }
        }
    }
}