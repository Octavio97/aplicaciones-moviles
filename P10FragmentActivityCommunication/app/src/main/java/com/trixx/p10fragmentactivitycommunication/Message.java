package com.trixx.p10fragmentactivitycommunication;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;


/**
 * A simple {@link Fragment} subclass.
 */
public class Message extends Fragment {
//Variables miembro
    private EditText mNameEditText;
    private Button mSendButton;
    OnMessageReadListener mOnMessageReadListener;
    public interface OnMessageReadListener {
        public void onMessageRead(String message);
    }

    public Message() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_message, container, false);
        mNameEditText = view.findViewById(R.id.editText);
        mSendButton = view.findViewById(R.id.button);
        mSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = mNameEditText.getText().toString();
                mOnMessageReadListener.onMessageRead(message);
            }
        });
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Activity activity = (Activity) context;
        try {
             mOnMessageReadListener = (OnMessageReadListener) activity;
        }
        catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()+ "debes de sobreescrivir onMessageRead...");
        }
    }
}