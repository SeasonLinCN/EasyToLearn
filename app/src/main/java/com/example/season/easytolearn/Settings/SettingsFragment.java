package com.example.season.easytolearn.Settings;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.example.season.easytolearn.LoginActivity;
import com.example.season.easytolearn.R;

/**
 * Created by Season on 2017/5/15.
 */

public class SettingsFragment extends Fragment {
    static String ID = "id";

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Button changePassword = (Button)getActivity().findViewById(R.id.changePassword);
        Button report = (Button)getActivity().findViewById(R.id.report);
        Button logout = (Button)getActivity().findViewById(R.id.logout);

        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),ChangePasswordActivity.class);
                intent.putExtra(ChangePasswordActivity.ID,getActivity().getIntent().getLongExtra(ID,0));
                startActivity(intent);
            }
        });
        report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),ReportActivity.class);
                startActivity(intent);
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),LoginActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.settings_fragment, container, false);

        return view;
    }

}
