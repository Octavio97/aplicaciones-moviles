package com.trixx.p6menunavigation.ui.share;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.trixx.p6menunavigation.R;

public class ShareFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_share, container, false);
        TextView textView = root.findViewById(R.id.text_share);
        textView.setText(R.string.menu_share);
        return root;
    }
}