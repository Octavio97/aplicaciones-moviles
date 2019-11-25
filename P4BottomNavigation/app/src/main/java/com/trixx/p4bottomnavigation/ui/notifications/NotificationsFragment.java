package com.trixx.p4bottomnavigation.ui.notifications;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.trixx.p4bottomnavigation.R;

public class NotificationsFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_notifications, container, false);

        TextView textView = root.findViewById(R.id.text_notifications);
        textView.setText(R.string.title_notifications);

        return root;
    }
}