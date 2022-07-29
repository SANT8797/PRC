package com.example.prc.notifications1;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.prc.CoursesActivity;
import com.example.prc.R;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;


public class NotificationsFragment extends Fragment {

    private NotificationsViewModel notificationsViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_notifications, container, false);
        TextView b1 = root.findViewById(R.id.button);
        b1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent findPeopleIntent = new Intent(getActivity(), CoursesActivity.class);
                startActivity(findPeopleIntent);
            }
        });
        return root;
    }


}