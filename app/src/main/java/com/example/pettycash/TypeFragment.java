package com.example.pettycash;

import android.graphics.Color;
import android.graphics.Rect;
import android.inputmethodservice.Keyboard;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.method.TransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;

import static com.basgeekball.awesomevalidation.ValidationStyle.COLORATION;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TypeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TypeFragment extends Fragment implements View.OnClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private View keyboard;
    boolean clicked = false;
    TextView title =null;
    String hint = null;
            String existValue = null;
    EditText editText =null;
    AppCompatButton submitBtn = null;
    AddLine addLine;
    View view;
    int addLineNum =-1;
    LinearLayout editTextFullLayout;
    String value;


    int orginalTextViewID;
    private View cancelBtn;
    private AwesomeValidation mAwesomeValidation;

    public TypeFragment() {
        // Required empty public constructor
    }

    public TypeFragment(int stringId, int textViewID, String existValue, AddLine activity,int addLineNum) {

        this.existValue = existValue;
        addLine = activity;
        hint = addLine.getStringVal(stringId);
        Log.v("hint",hint);
        orginalTextViewID = textViewID;
        this.addLineNum = addLineNum;
        Log.v("select pos",String.valueOf(addLineNum));




    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TypeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TypeFragment newInstance(String param1, String param2) {
        TypeFragment fragment = new TypeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_type, container, false);

        mAwesomeValidation = new AwesomeValidation(COLORATION);
        mAwesomeValidation.setColor(Color.YELLOW);

        editTextFullLayout = view.findViewById(R.id.type_fragment_edit_text_layout);
        title = view.findViewById(R.id.type_fragment_title);
        if (hint != null )
            title.setText("Enter "+hint);
//        }
        editText = view.findViewById(R.id.type_fragment_edit_text);
        if (hint != null)
            editText.setHint("Enter the " + hint.toLowerCase());

        submitBtn = view.findViewById(R.id.type_fragment_btn);
        cancelBtn = view.findViewById(R.id.type_fragment_cancel);
//        if (submitBtn != null)
            submitBtn.setOnClickListener(this);
            cancelBtn.setOnClickListener(this);

            editText.setOnClickListener(this);

        if (orginalTextViewID == R.id.line_recycle_quantity_choose_text) {

        }else {
            editText.setInputType(InputType.TYPE_CLASS_PHONE);
        }

        mAwesomeValidation.addValidation(getActivity(), R.id.type_fragment_edit_text, "[0-9]+", R.string.value_greater_then_zere_err);

        return view;
    }

    @Override
    public void onClick(View v) {
        InputMethodManager imm;
        if (addLine != null) {
            switch (v.getId()) {
                case R.id.type_fragment_btn:
//                    if (orginalTextViewID == R.id.line_recycle_price_choose_text) {
                        Log.v("price", "clicked");
                        addLine.adapter.lineModelViews.get(addLineNum).priceClicked = true;
                        if (!value.isEmpty()) {
                            Log.v("priceUpdate",Integer.valueOf(editText.getText().toString())+"");
                            if (Integer.valueOf(editText.getText().toString()) <= 0) {
                            editTextFullLayout.setBackgroundColor(getActivity().getResources().getColor(R.color.red_light));
                                Toast.makeText(getActivity(),getActivity().getText(R.string.value_greater_then_zere_err),Toast.LENGTH_SHORT).show();
                            }else    if (Integer.valueOf(editText.getText().toString()) <= 0) {
                                editTextFullLayout.setBackgroundColor(getActivity().getResources().getColor(R.color.red_light));
                                Toast.makeText(getActivity(),getActivity().getText(R.string.value_greater_then_zere_err),Toast.LENGTH_SHORT).show();
                            } else {
                                addLine.adapter.viewHolder.upadteText(orginalTextViewID, value, addLineNum);
                                keyboard = addLine.getCurrentFocus();
                                if (keyboard == null) {
                                    keyboard = new View(addLine);
                                }
                                imm = (InputMethodManager) addLine.getSystemService(addLine.INPUT_METHOD_SERVICE);
                                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

                                addLine.hideFragment();
                            }

                        }
//                    }
//                    if (orginalTextViewID == R.id.line_recycle_quantity_choose_text){
//                        Log.v("quantity","clicked");
//                        addLine.adapter.lineModelViews.get(addLineNum).quantityClicked =true;
//                        if (!editText.getText().toString().isEmpty()) {
//                            if (Integer.valueOf(editText.getText().toString()) <= 0){
//                                addLine.adapter.viewHolder.upadteText(orginalTextViewID, "1", addLineNum);
//
//                            }else {
//                                addLine.adapter.viewHolder.upadteText(orginalTextViewID, editText.getText().toString(), addLineNum);
//
//                            }
//                    }
//
//                    }



                    break;
                case R.id.type_fragment_cancel:

                        keyboard = getActivity().getCurrentFocus();


                        if (keyboard == null) {
                            keyboard = new View(getActivity());
                        }
                        imm = (InputMethodManager) getActivity().getSystemService(getContext().INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(keyboard.getWindowToken(), 0);

                    addLine.hideFragment();

                case R.id.type_fragment_edit_text:
                    mAwesomeValidation.validate();
                    clicked = true;

                    if (orginalTextViewID == R.id.line_recycle_quantity_choose_text) {
                        editText.setInputType(InputType.TYPE_NUMBER_VARIATION_PASSWORD);

                        editText.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                            }

                            @Override
                            public void onTextChanged(CharSequence s, int start, int before, int count) {

                            }

                            @Override
                            public void afterTextChanged(Editable s) {
                                if (!s.toString().isEmpty()) {
                                    value = s.toString();
                                }
                            }
                        });

                        editText.setTransformationMethod(new TransformationMethod() {
                            @Override
                            public CharSequence getTransformation(CharSequence source, View view) {
                                return source;
                            }

                            @Override
                            public void onFocusChanged(View view, CharSequence sourceText, boolean focused, int direction, Rect previouslyFocusedRect) {

                            }
                        });
                    }else {
                        editText.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                            }

                            @Override
                            public void onTextChanged(CharSequence s, int start, int before, int count) {

                            }

                            @Override
                            public void afterTextChanged(Editable s) {
                                if (!s.toString().isEmpty()) {
                                    value = s.toString();
                                    if (value.matches("[0-9]+(.)") ){
                                        editTextFullLayout.setBackgroundColor(getActivity().getResources().getColor(R.color.white));

                                    }else {
                                        editTextFullLayout.setBackgroundColor(getActivity().getResources().getColor(R.color.red_light));
                                        Toast.makeText(getActivity(),getActivity().getText(R.string.value_greater_then_zere_and_num_err),Toast.LENGTH_SHORT).show();

                                    }
                                }
                            }
                        });
                    }

            }
        }

    }
}