package com.trixx.p4bottomnavigation.ui.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.trixx.p4bottomnavigation.R;

public class DashboardFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);

        TextView textView = root.findViewById(R.id.text_dashboard);
        textView.setText(R.string.title_dashboard);

        return root;
    }
}