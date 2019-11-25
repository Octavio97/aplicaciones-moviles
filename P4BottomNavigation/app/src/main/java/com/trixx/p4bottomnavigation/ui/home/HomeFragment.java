package com.trixx.p4bottomnavigation.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.trixx.p4bottomnavigation.R;

public class HomeFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        TextView textView = root.findViewById(R.id.text_home);
        textView.setText(R.string.title_home);

        return root;
    }
}