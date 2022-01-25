package com.example.pettycash;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pettycash.databse.AttachmentModelView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class AttachmentAdapter extends RecyclerView.Adapter<AttachmentAdapter.ViewHolder> {

    Context context;
    List<AttachmentModelView> docList;
    AddLine addLine;
    int linePos = -1;


    AddLineFragmentListAdapter.ViewHolder viewHolder;

    public AttachmentAdapter(Context context, List<AttachmentModelView> docList, AddLine addLine ) {
        this.context = context;
        this.docList = docList;
        this.addLine = addLine;
    }

    public AttachmentAdapter(Context context,AddLine addLine) {
        this.context = context;
        docList = new ArrayList<>();
        this.addLine = addLine;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewHolder = LayoutInflater.from(context).inflate(R.layout.attachment_recycle_view,parent,false);

        return new ViewHolder(viewHolder);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            if (linePos != -1) {
                Log.v("doc", "doc" + position + " :" + docList.get(position));
                int pos = position + 1;
                holder.title.setText(linePos + "." + pos);

                Picasso.get().load(docList.get(position).path).into(holder.image);
            }
    }



    @Override
    public int getItemCount() {
        if (docList != null) {
            Log.v("docsC", String.valueOf(docList.size()));

            return docList.size();
        }else return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        ImageView image;
        TextView title;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.attachment_recycle_image);
            title = itemView.findViewById(R.id.attachment_recycle_title);

            image.setOnClickListener(this);
            image.setOnLongClickListener(this);





        }

        @Override
        public void onClick(View v) {

            ImageFragment imageFragment = new ImageFragment(Uri.parse(docList.get(getAdapterPosition()).path));
            addLine.fragmentManager.beginTransaction()
                    .replace(R.id.add_line_Image_fragment,imageFragment,"image")
                    .commit();
            addLine.imageFragment.setVisibility(View.VISIBLE);


        }

        @Override
        public boolean onLongClick(View v) {
            return false;
        }
    }
}
