package com.example.pettycash;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.Preference;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class FragmentListAdapter  extends RecyclerView.Adapter<FragmentListAdapter.ViewHolder> {

    private final int selectorTitle;
    private int  textViewID;
    Context context;
    List<String> stringList;
    int icon;
    UserPreferences userPreferences;
    String  existValue ,prefID;
    private AppCompatActivity activeActivity;
    int addLineNum =-1;


    public FragmentListAdapter(String sharedPrefId,Context context, List<String> stringList , int selctorTitle , int icon , AppCompatActivity activeActivity,  String existValue) {
        this.context = context;
        this.stringList = stringList;
        this.selectorTitle = selctorTitle;
        this.icon = icon;
        this.activeActivity = activeActivity;
        prefID=sharedPrefId;
        this.existValue = existValue;

        if (activeActivity instanceof UserPreferences) {
            Log.v("avtivity", "user");
        }else             Log.v("avtivity", "tran");


    }


    public FragmentListAdapter(Context context, List<String> stringList, int selctorTitle, int icon, AppCompatActivity activeActivity, int textviewID, String existValue ,         int addLineNum
    ) {
        this.context = context;
        this.stringList = stringList;
        this.selectorTitle = selctorTitle;
        this.icon = icon;
        this.activeActivity = activeActivity;

        this.textViewID = textviewID;
        this.existValue = existValue;
        this.addLineNum = addLineNum;
        Log.v("fragment pos",String.valueOf(addLineNum));


    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewHolder = LayoutInflater.from(context).inflate(R.layout.prefrences_list_view,parent,false);
        return new ViewHolder(viewHolder);    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.nameText.setText(stringList.get(position));
        holder.imageView.setImageResource(icon);
        if (stringList.get(position).equals(existValue)) {
            holder.layout.setBackgroundColor(context.getResources().getColor(R.color.red_light));
            holder.nameText.setTextColor(context.getResources().getColor(R.color.white));
            ImageView imageView = holder.imageView;
            // Set the background color to white
            imageView.setBackgroundColor(context.getResources().getColor(R.color.red_light));
            // Parse the SVG file from the resource
//            SVG svg =
//             Get a drawable from the parsed SVG and set it as the drawable for the ImageView
//            imageView.setImageDrawable(svg.createPictureDrawable());
            // Set the ImageView as the content view for the Activity
                  }
        }




    @Override
    public int getItemCount() {
        return stringList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        TextView nameText;
        ImageView imageView;
        RelativeLayout layout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameText=itemView.findViewById(R.id.prefernces_fragment_list_name);
//            nameText.setOnClickListener(this);

            imageView = itemView.findViewById(R.id.prefernces_fragment_list_image);

            layout = itemView.findViewById(R.id.prefernces_fragment_list_layout);
            layout.setOnClickListener(this);



        }
        @Override
        public void onClick(View v) {





            if (activeActivity instanceof UserPreferences) {
                SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString(prefID,nameText.getText().toString()).apply();
                MainActivity.createToast(context, "id:"+prefID+"  ,changed to "+nameText.getText().toString());

                UserPreferences userPreferences = (UserPreferences)activeActivity;
                userPreferences.hideFragment();
                userPreferences.shared();

            }else if (activeActivity instanceof AddTransaction){
                AddTransaction addTransaction = (AddTransaction) activeActivity;
                addTransaction.hideFragment();
                addTransaction.shared(nameText.getText().toString(),selectorTitle);
            }
            else {
                AddLine addLine = (AddLine) activeActivity;
                addLine.hideFragment();
                addLine.adapter.viewHolder.upadteText(textViewID,nameText.getText().toString(),addLineNum);
            }




        }
    }
}
