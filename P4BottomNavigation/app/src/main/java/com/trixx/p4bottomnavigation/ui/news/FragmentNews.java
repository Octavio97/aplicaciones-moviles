package com.trixx.p4bottomnavigation.ui.news;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.trixx.p4bottomnavigation.R;

public class FragmentNews extends Fragment {


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_news, container, false);

        TextView textView = root.findViewById(R.id.news_textview);
        textView.setText(R.string.news_string);

        return root;
    }
}