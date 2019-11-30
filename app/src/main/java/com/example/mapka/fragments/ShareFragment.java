package com.example.mapka.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.mapka.R;
import com.example.mapka.models.LocalizationModel;

public class ShareFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);

        SharedPreferences pref = getActivity().getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        //TODO try catch
        String locationString = pref.getString("currentLocation", null);

        shareLocationFromHistory(locationString);
        return inflater.inflate(R.layout.fragment_share, container, false);
    }


    public void shareLocationFromHistory(String locationString){
        Toast.makeText(getActivity(), locationString, Toast.LENGTH_SHORT).show();
    }


}
