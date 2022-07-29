package com.example.prc.dashboard;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.prc.DailyQuestion;
import com.example.prc.DetailHandReading;
import com.example.prc.Gun_Milan;
import com.example.prc.HandReading;
import com.example.prc.MessageActivity;
import com.example.prc.OneQuestion;
import com.example.prc.R;
import com.example.prc.ThreeQuestion;
import com.example.prc.YearlyPrediction;
import com.example.prc.lovemarriage;
import com.example.prc.talkto_palmist;
import com.example.prc.yearly_package;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

public class DashboardFragment extends Fragment {



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        Button hreading = (Button) root.findViewById(R.id.dwnldME);
        Button dhreading = (Button) root.findViewById(R.id.dwnldCE);
        Button oydhreading = (Button) root.findViewById(R.id.dwnldEE);
        Button gm = (Button) root.findViewById(R.id.dwnldExtc);
        Button lovemarrige = (Button) root.findViewById(R.id.dwnldCM);
        Button ypackage = (Button) root.findViewById(R.id.dwnldIF);
        Button askque = (Button) root.findViewById(R.id.aoq);
        Button top = (Button) root.findViewById(R.id.top);
        ypackage.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent findPeopleIntent = new Intent(getActivity(), yearly_package.class);
                startActivity(findPeopleIntent);
            }
        });
        askque.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent findPeopleIntent = new Intent(getActivity(), OneQuestion.class);
                startActivity(findPeopleIntent);
            }
        });
        hreading.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent findPeopleIntent = new Intent(getActivity(), HandReading.class);
                startActivity(findPeopleIntent);
            }
        });
        dhreading.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent findPeopleIntent = new Intent(getActivity(), DetailHandReading.class);
                startActivity(findPeopleIntent);
            }
        });
        oydhreading.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent findPeopleIntent = new Intent(getActivity(), YearlyPrediction.class);
                startActivity(findPeopleIntent);
            }
        });
        gm.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent findPeopleIntent = new Intent(getActivity(), Gun_Milan.class);
                startActivity(findPeopleIntent);
            }
        });
        lovemarrige.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent findPeopleIntent = new Intent(getActivity(), lovemarriage.class);
                startActivity(findPeopleIntent);
            }
        });
        top.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent findPeopleIntent = new Intent(getActivity(), talkto_palmist.class);
                startActivity(findPeopleIntent);
            }
        });

        return root;
    }
}