package com.example.pettycash;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.TransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pettycash.Utality.Utlity;
import com.example.pettycash.databse.AttachmentModelView;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.security.Permission;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AddLineFragmentListAdapter extends RecyclerView.Adapter<AddLineFragmentListAdapter.ViewHolder> {

//    private final int selectorTitle;
    Context context;
    List<String> stringList;
    int icon;
    UserPreferences userPreferences;
    String sharedPrefId;
    AddLine addLine;

    ViewHolder viewHolder;

    AttachmentAdapter attachmentAdapter;


    AddLine activity;
    List<LineModelView> lineModelViews = new ArrayList<>();

    private DatePickerDialog.OnDateSetListener date;
    private Calendar myCalendar;
    ActivityResultLauncher<Intent> someActivityResultLauncher;
     String currentPhotoPath;
    private boolean isBilled;
     boolean isGranted;



    public AddLineFragmentListAdapter(Context context, AddLine activity, List<LineModelView> lines) {
        this.context = context;
        this.activity = activity;
        lineModelViews.addAll(lines);
        Log.v("adpList", String.valueOf(lineModelViews.size()));
        myCalendar = Calendar.getInstance();



//        this.stringList = stringList;
//        this.selectorTitle = selctorTitle;
//        this.icon = icon;
//        this.activeActivity = activeActivity;
//        this.sharedPrefId = sharedPrefId;




        addLine = (AddLine) context;

    }

    public void updateDocs(){
        viewHolder.attachmentAdapter.notifyDataSetChanged();
        Log.v("docsL", String.valueOf(lineModelViews.get(addLine.attachPos).docsList.size()));
        addLine.camere =false;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewHolder = LayoutInflater.from(context).inflate(R.layout.add_line_rcycle_view,parent,false);

        return new ViewHolder(viewHolder);    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {



        LineModelView current = lineModelViews.get(position);
        List<AttachmentModelView> docs= current.docsList;
        holder.attachmentAdapter.linePos = position+1;
        holder.attachmentAdapter.docList.clear();
        holder.attachmentAdapter.docList.addAll(docs);
        holder.attachmentAdapter.notifyDataSetChanged();

        int pos = position+1;
        Log.v("current : "+current.position,"adapter: " +position);
        Log.v("current","pos: "+position +" cat : "+current.category +" item : "+current.item +" unit : "+current.unit +" price : "+current.price +" quantity : "+current.quantity+" pClicked: "+Boolean.toString(current.priceClicked)+" qClicked "+current.quantityClicked);




        if (current.category != null)
            holder.categoryChooseText.setText(current.category);

        if (current.unit != null)
            holder.unitChooseText.setText(current.unit);

        if (current.item != null)
            holder.itemChooseText.setText(current.item);

        if (current.cbsCode != null)
            holder.cbsCodeText.setText(current.cbsCode);

        if (current.expenditureType != null)
            holder.expenditureTypeText.setText(current.expenditureType);

        if (current.price != 0 && current.priceClicked)
            holder.priceChooseText.setText(String.valueOf(current.price));

        if (current.quantity >1 && current.quantityClicked)
            holder.quantityChooseText.setText(String.valueOf(current.quantity));

        String myFormat="yyyy-MM-dd";
        SimpleDateFormat dateFormat=new SimpleDateFormat(myFormat, Locale.ENGLISH);

        holder.dateChooseText.setText(dateFormat.format(current.invoiceDate));

        double amount = current.price*current.quantity;
        holder.amountChooseText.setText(String.valueOf(amount) +" SAR");

        holder.billed.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                current.billedToCustomer =isChecked;
            }
        });




        holder.title.setText(addLine.getResources().getString(R.string.add_line)+ " "+String.valueOf(pos));

        date =new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH,month);
                myCalendar.set(Calendar.DAY_OF_MONTH,day);

                String myFormat="yyyy-MM-dd";
                SimpleDateFormat dateFormat=new SimpleDateFormat(myFormat, Locale.ENGLISH);
                if (myCalendar.getTime() == null) {

                    current.invoiceDate = myCalendar.getTime().getTime();
                }else {
                    current.invoiceDate = myCalendar.getTimeInMillis();

                }
                notifyDataSetChanged();
            }
        };









    }

    @Override
    public int getItemCount() {
        Log.v("adptS", String.valueOf(lineModelViews.size()));
        return lineModelViews.size();
    }




    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, CompoundButton.OnCheckedChangeListener , ActivityCompat.OnRequestPermissionsResultCallback {

          RecyclerView attachs_recyclerView;
        int ItemView;
        ImageButton imageView;
        RelativeLayout categorylayout,itemlayout,quantitylayout,unitlayout,datelayout,cbsCodeLayout,expenditureTypeLayout,priceEditText,amountEditText;
        TextView categoryChooseText,itemChooseText,quantityChooseText,unitChooseText,priceChooseText,amountChooseText,dateChooseText,cbsCodeText,expenditureTypeText,title;
        EditText invoiceEditText,supplierEditText,vatEditText;
        Switch billed;
        ImageView attachmentBtn;
        AttachmentAdapter attachmentAdapter;
        List<Uri> docs ;
        private SharedPreferences sharedPref;

        public ViewHolder(@NonNull View itemView) {



            super(itemView);
            sharedPref = PreferenceManager.getDefaultSharedPreferences(addLine);
            isGranted = sharedPref.getBoolean(String.valueOf(Utlity.CAMERA_REQUEST_ID),false);

            docs = new ArrayList<>();

            attachmentAdapter = new AttachmentAdapter(context,addLine);


            attachs_recyclerView = itemView.findViewById(R.id.line_recycle_attachment_recycle);
            LinearLayoutManager linearLayout = new LinearLayoutManager(context,RecyclerView.HORIZONTAL,false);
            attachs_recyclerView.setLayoutManager(linearLayout);
            attachs_recyclerView.setAdapter(attachmentAdapter);

            viewHolder = this;
            billed = itemView.findViewById(R.id.line_recycle_switch);
            title = itemView.findViewById(R.id.line_recycle_title_text);

            cbsCodeText = itemView.findViewById(R.id.line_recycle_cbs_code_choose_text);
            expenditureTypeText = itemView.findViewById(R.id.line_recycle_expenditure_type_choose_text);
            dateChooseText = itemView.findViewById(R.id.line_recycle_invoice_date_choose_text);
            imageView = itemView.findViewById(R.id.prefernces_fragment_list_image);
            categorylayout = itemView.findViewById(R.id.line_recycle_category_choose_layout);
            unitlayout = itemView.findViewById(R.id.line_recycle_unit_choose_layout);
            itemlayout = itemView.findViewById(R.id.line_recycle_item_choose_layout);
            quantitylayout = itemView.findViewById(R.id.line_recycle_quantity_choose_layout);
            priceEditText = itemView.findViewById(R.id.line_recycle_price_choose_layout);
            amountEditText = itemView.findViewById(R.id.line_recycle_amount_choose_layout);
            datelayout = itemView.findViewById(R.id.line_recycle_invoice_date_choose_layout);
            cbsCodeLayout = itemView.findViewById(R.id.line_recycle_cbs_code_choose_layout);
            expenditureTypeLayout = itemView.findViewById(R.id.line_recycle_expenditure_type_choose_layout);

            attachmentBtn = itemView.findViewById(R.id.line_recycle_attatchment_choose_layout);

            invoiceEditText = itemView.findViewById(R.id.line_recycle_invoice_number_choose_layout);
            supplierEditText = itemView.findViewById(R.id.line_recycle_supplier_name_choose_layout);
            vatEditText = itemView.findViewById(R.id.line_recycle_vat_number_choose_layout);
            amountChooseText = itemView.findViewById(R.id.line_recycle_amount_choose_text);

            cbsCodeLayout.setOnClickListener(this);
            expenditureTypeLayout.setOnClickListener(this);
            categorylayout.setOnClickListener(this);
            unitlayout.setOnClickListener(this);
            itemlayout.setOnClickListener(this);
            quantitylayout.setOnClickListener(this);
            priceEditText.setOnClickListener(this);
//            amountEditText.setOnClickListener(this);
            datelayout.setOnClickListener(this);
            billed.setOnCheckedChangeListener(this);
            invoiceEditText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
            lineModelViews.get(getAdapterPosition()).invoiceNumber = s.toString();
                }
            });
            supplierEditText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    lineModelViews.get(getAdapterPosition()).supplierName = s.toString();

                }
            });
            vatEditText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (!s.toString().isEmpty()) {
                        lineModelViews.get(getAdapterPosition()).vatInvoiceNumber = Long.valueOf(s.toString());
                    }
                }
            });

            vatEditText.setTransformationMethod(new TransformationMethod() {
                @Override
                public CharSequence getTransformation(CharSequence source, View view) {
                    return source;
                }

                @Override
                public void onFocusChanged(View view, CharSequence sourceText, boolean focused, int direction, Rect previouslyFocusedRect) {

                }
            });

            attachmentBtn.setOnClickListener(this);

            categoryChooseText = itemView.findViewById(R.id.line_recycle_category_choose_text);
//            amountChooseText = itemView.findViewById(R.id.line_recycle_amount_choose_text);
            itemChooseText = itemView.findViewById(R.id.line_recycle_item_choose_text);
            quantityChooseText = itemView.findViewById(R.id.line_recycle_quantity_choose_text);
            unitChooseText = itemView.findViewById(R.id.line_recycle_unit_choose_text);
            priceChooseText = itemView.findViewById(R.id.line_recycle_price_choose_text);

        }

        @Override
        public void onClick(View v) {

            SelectFragment selectFragment;
            TypeFragment typeFragment;

            switch (v.getId()){
                case R.id.line_recycle_category_choose_layout:
            selectFragment = new SelectFragment(R.string.category,R.drawable.outline_category_24,R.id.line_recycle_category_choose_text,categoryChooseText.getText().toString(),activity, getAdapterPosition());

            addLine.fragmentManager.beginTransaction()
                    .replace(R.id.add_line_select_fragment,selectFragment,"category")
                    .commit();
            addLine.selectFragment.setVisibility(View.VISIBLE);

            break;
                case R.id.line_recycle_amount_choose_layout:
                    selectFragment = new SelectFragment(R.string.amount,R.drawable.outline_category_24,R.id.line_recycle_amount_choose_text,amountChooseText.getText().toString(),activity, getAdapterPosition());

                    addLine.fragmentManager.beginTransaction()
                            .replace(R.id.add_line_select_fragment,selectFragment,"amount")
                            .commit();
//                    addLine.fragment.setVisibility(View.VISIBLE);

                    break;
                case R.id.line_recycle_price_choose_layout:
                    typeFragment = new TypeFragment(R.string.price,R.id.line_recycle_price_choose_text,priceChooseText.getText().toString(),activity, getAdapterPosition());

                    addLine.fragmentManager.beginTransaction()
                            .replace(R.id.add_line_type_fragment,typeFragment,"price")
                            .commit();
                    addLine.typeFragment.setVisibility(View.VISIBLE);

                    break;
                case R.id.line_recycle_unit_choose_layout:
                    selectFragment = new SelectFragment(R.string.unit,R.drawable.outline_category_24,R.id.line_recycle_unit_choose_text,unitChooseText.getText().toString(),activity, getAdapterPosition());

                    addLine.fragmentManager.beginTransaction()
                            .replace(R.id.add_line_select_fragment,selectFragment,"unit")
                            .commit();
                    addLine.selectFragment.setVisibility(View.VISIBLE);

                    break;
                case R.id.line_recycle_quantity_choose_layout:
                    typeFragment = new TypeFragment(R.string.quantity,R.id.line_recycle_quantity_choose_text,quantityChooseText.getText().toString(),activity, getAdapterPosition());

                    addLine.fragmentManager.beginTransaction()
                            .replace(R.id.add_line_type_fragment,typeFragment,"quantity")
                            .commit();
                    addLine.typeFragment.setVisibility(View.VISIBLE);

                    break;
                case R.id.line_recycle_item_choose_layout:
                    selectFragment = new SelectFragment(R.string.item,R.drawable.outline_category_24,R.id.line_recycle_item_choose_text,itemChooseText.getText().toString(),activity, getAdapterPosition());

                    addLine.fragmentManager.beginTransaction()
                            .replace(R.id.add_line_select_fragment,selectFragment,"item")
                            .commit();
                    addLine.selectFragment.setVisibility(View.VISIBLE);

                    break;

                case R.id.line_recycle_invoice_date_choose_layout:
                    new DatePickerDialog(context, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                    break;

                case R.id.line_recycle_attatchment_choose_layout:
                    ActivityCompat.requestPermissions(addLine, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, Utlity.CAMERA_REQUEST_ID);
                    break;

                case R.id.line_recycle_cbs_code_choose_layout:
                    selectFragment = new SelectFragment(R.string.cbs_code,R.drawable.outline_category_24,R.id.line_recycle_cbs_code_choose_text,cbsCodeText.getText().toString(),activity, getAdapterPosition());

                    addLine.fragmentManager.beginTransaction()
                            .replace(R.id.add_line_select_fragment,selectFragment,"cbs")
                            .commit();
                    addLine.selectFragment.setVisibility(View.VISIBLE);

                    break;
                case R.id.line_recycle_expenditure_type_choose_layout:
                    selectFragment = new SelectFragment(R.string.expenditure_type,R.drawable.outline_category_24,R.id.line_recycle_expenditure_type_choose_text,expenditureTypeText.getText().toString(),activity, getAdapterPosition());

                    addLine.fragmentManager.beginTransaction()
                            .replace(R.id.add_line_select_fragment,selectFragment,"exp")
                            .commit();
                    addLine.selectFragment.setVisibility(View.VISIBLE);

                    break;
            }



//
//
//            if (activeActivity instanceof UserPreferences) {
//                SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
//                SharedPreferences.Editor editor = sharedPref.edit();
//                editor.putString(sharedPrefId,nameText.getText().toString()).apply();
//
//                UserPreferences userPreferences = (UserPreferences)activeActivity;
//                userPreferences.hideFragment();
//                userPreferences.shared();
//            }else {
//                AddTransaction addTransaction = (AddTransaction) activeActivity;
//                addTransaction.hideFragment();
//                addTransaction.shared(nameText.getText().toString(),selectorTitle);
//            }




        }

        public void openFiles() {

            final CharSequence[] options = { "Take Photo", "Choose from Gallery","Cancel" };
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Add Photo!");
            builder.setItems(options, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int item) {
                    if (options[item].equals("Take Photo"))
                    {
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        addLine.camere=true;


                        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        // Ensure that there's a camera activity to handle the intent
//                        if (takePictureIntent.resolveActivity(context.getPackageManager()) != null) {
//                            // Create the File where the photo should go
//                            File photoFile = null;
//                            try {
//                                photoFile = createImageFile();
//                            } catch (IOException ex) {
//                                // Error occurred while creating the File
//                            }
//                            // Continue only if the File was successfully created
//                            if (photoFile != null) {
//                               addLine.photoURI = FileProvider.getUriForFile(context,
//                                        "com.example.android.fileprovider",
//                                        photoFile);
//                            }
//                        }
//                        ActivityCompat.requestPermissions(addLine,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
//                        addLine.someActivityResultLauncher.launch(takePictureIntent);
//                        Log.v("imgData", Boolean.valueOf(currentPhotoPath.isEmpty()));
                        LineModelView current = lineModelViews.get(getAdapterPosition());
                        List<AttachmentModelView> docs= current.docsList;
                        addLine.current = current;
//                        addLine.currentItemView = itemView;

                        int pos = current.position+1;

                        OutputStream fos;
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                            ContentResolver resolver = context.getContentResolver();
                            ContentValues contentValues = new ContentValues();
                            contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, pos + "." + docs.size());
                            contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "image/png");
                            contentValues.put(MediaStore.MediaColumns.RELATIVE_PATH, "DCIM/" + "PettyCash");
                            Uri imageUri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
                            try {
                                fos = resolver.openOutputStream(imageUri);
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }
                            Log.v("pathpath", String.valueOf(imageUri));
                            addLine.photoURI = imageUri;
                            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                            takePictureIntent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, imageUri);
                            addLine.someActivityResultLauncher.launch(takePictureIntent);

                        }
                    }
                    else if (options[item].equals("Choose from Gallery"))
                    {
                        Intent intent = new   Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        addLine.someActivityResultLauncher.launch(intent);
                    }
                    else if (options[item].equals("Cancel")) {
                        dialog.dismiss();
                    }
                }
            });
            builder.show();


        Intent chooseFile;
            Intent intent;
            chooseFile = new Intent(Intent.ACTION_GET_CONTENT);
            chooseFile.addCategory(Intent.CATEGORY_OPENABLE);
            chooseFile.setType("*/*");
            intent = Intent.createChooser(chooseFile, "Choose a file");
            addLine.attachPos=getAdapterPosition();
//           addLine.someActivityResultLauncher.launch(intent);
        }




        private File createImageFile() throws IOException {
            Log.v("cFile","yes");
            // Create an image file name
//            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
//            String imageFileName = "JPEG_" + timeStamp + "_";
//
            File picRoot = Environment.getExternalStorageDirectory();
            File storageDir = new File(picRoot.getAbsolutePath()+"/PettyCash");
            storageDir.mkdirs();



//            File image = File.createTempFile(
//                    imageFileName,  /* prefix */
//                    ".jpg",         /* suffix */
//                    storageDir      /* directory */
//            );
//
//
//            // Save a file: path for use with ACTION_VIEW intents
//            currentPhotoPath = image.getAbsolutePath();
//            Log.v("imgDir", currentPhotoPath);
//
//            return image;
//            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
//            String imageFileName = "JPEG_" + timeStamp + "_";
//            File storageDir = Environment.getExternalStorageDirectory();
//            File image = File.createTempFile(
//                    imageFileName,  /* prefix */
//                    ".jpg",         /* suffix */
//                    storageDir      /* directory */
//            );
//            Log.w("curPath",storageDir.getAbsolutePath());

            // Save a file: path for use with ACTION_VIEW intents
            currentPhotoPath = storageDir.getAbsolutePath();
            return storageDir;

        }

        public void upadteText(int viewId, String text,int pos){
            Log.v("udpate pos",String.valueOf(pos));

            switch (viewId){

                case R.id.line_recycle_category_choose_text:
                    lineModelViews.get(pos).category = text;
                    break;

                case R.id.line_recycle_item_choose_text:
                    lineModelViews.get(pos).item = text;
                    break;

                case R.id.line_recycle_unit_choose_text:
                    lineModelViews.get(pos).unit = text;
                    break;
                case R.id.line_recycle_cbs_code_choose_text:
                    lineModelViews.get(pos).cbsCode = text;
                    break;
                case R.id.line_recycle_expenditure_type_choose_text:
                    lineModelViews.get(pos).expenditureType = text;
                    break;

                case R.id.line_recycle_quantity_choose_text:
                    lineModelViews.get(pos).quantity = Integer.valueOf(text);
                    lineModelViews.get(pos).amount =    lineModelViews.get(pos).quantity*   lineModelViews.get(pos).price;
                    break;

                case R.id.line_recycle_price_choose_text:
                    lineModelViews.get(pos).price = Double.valueOf(text);
                    lineModelViews.get(pos).amount =    lineModelViews.get(pos).quantity*   lineModelViews.get(pos).price;

                    break;

                default:
            }
            notifyDataSetChanged();
//            Log.v("cat:",text);
//            TextView textView = itemView.findViewById(viewId);
//            textView.setText(text);

        }

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            isBilled=isChecked;
            lineModelViews.get(getAdapterPosition()).billedToCustomer =isBilled;

        }

        @Override
        public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
            Log.v("pre0",permissions[0]+" = "+grantResults[0]);
            Log.v("pre1",permissions[1]+" = "+grantResults[1]);

            if ( grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.v("pre0",permissions[0]+" = "+grantResults[0]);
                if ( grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    Log.v("pre1",permissions[1]+" = "+grantResults[1]);


                    if (requestCode == Utlity.CAMERA_REQUEST_ID) {
                        isGranted=true;
                        SharedPreferences.Editor editor = sharedPref.edit();
                        editor.putBoolean(String.valueOf(Utlity.CAMERA_REQUEST_ID),isGranted).apply();
                        openFiles();
                    }
                }
            }
        }
        }
    }

