package com.example.pettycash;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class ImageFragment extends Fragment implements View.OnClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    private View view;

    Uri imagePath;


    FragmentListAdapter fragmentListAdapter;
    public ImageFragment(Uri imagePath) {
      this.imagePath = imagePath;
      Log.v("path", String.valueOf(imagePath));
        // Required empty public constructor
    }

//    public SelectFragment(int select_lagal , int imageBtn ) {
//        titleString =select_lagal;
//        icon = imageBtn;
//        this.sherdPrefId = sherdPrefId;
//
//
//        // Required empty public constructor
//    }

    public ImageFragment() {
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment SelectFragment.
     */
    // TODO: Rename and change types and number of parameters


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.fragment_image, container, false);

        ImageView imageView =view.findViewById(R.id.image_fragment_image);
        ImageButton cancelBtn = view.findViewById(R.id.image_fragment_cancel_btn);
        if (imageView != null) {
            Picasso.get().load(imagePath).into(imageView);
            cancelBtn.setOnClickListener(this);
        }

        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onClick(View view) {
        Log.v("imageGA",getActivity().getLocalClassName());

        switch (view.getId()) {
            case R.id.image_fragment_cancel_btn:
                Log.v("imageGA",getActivity().toString());
                if (getActivity() instanceof AddLine) {
                    AddLine addLine = (AddLine) getActivity();
                    addLine.imageFragment.setVisibility(View.GONE);

                }else {
                    TransactionReview  transactionReview = (TransactionReview) getActivity();
                    transactionReview.imageFragment.setVisibility(View.GONE);

                }



                break;


        }

    }
}