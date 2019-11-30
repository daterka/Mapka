package com.example.mapka.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.fragment.app.ListFragment;
import com.example.mapka.R;
import com.example.mapka.database.DataBaseAdapter;
import com.example.mapka.models.LocalizationModel;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import static com.example.mapka.database.DBStrings.DEBUG_TAG;

public class HistoryFragment extends Fragment {
    DataBaseAdapter dataBaseAdapter;
    ListView listView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);

        View rootView = inflater.inflate(R.layout.fragment_history, container, false);

        dataBaseAdapter = new DataBaseAdapter(getActivity().getApplicationContext());
        dataBaseAdapter.open();
        ArrayList<LocalizationModel> locaplizationHistory = dataBaseAdapter.getAllLocalizations();
        dataBaseAdapter.close();

        listView = rootView.findViewById(R.id.listViewHistory);


       if (locaplizationHistory != null){
           final StableAdapter stableAdapter = new StableAdapter(getActivity(), locaplizationHistory);
           Log.d("HISTORY FRAGMENT", "StableAdapter object created Succesfully.");

           listView.setAdapter(stableAdapter);


           listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
               @Override
               public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                   final LocalizationModel item = (LocalizationModel) parent.getItemAtPosition(position);
                   view.animate().setDuration(10).alpha(0.8f).withEndAction(new Runnable() {
                       @Override
                       public void run() {
                           Intent intent = new Intent(getContext(), ShareFragment.class);
                           String locationString = item.getName() + " (" + item.getLatitude() + ", " + item.getLongitude() + ")";
                           //intent.putExtra("shareLocationFromHistory", locationString);

                           SharedPreferences pref = getActivity().getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
                           SharedPreferences.Editor editor = pref.edit();
                           editor.putString("currentLocation", locationString);
                           editor.apply();

                           FragmentManager fragmentManager = getFragmentManager();
                           fragmentManager.beginTransaction().replace(R.id.fragment_container, new ShareFragment()).commit();

                       }
                   });
               }
           });
       }
       else{
           final StableAdapter stableAdapter = new StableAdapter(getActivity(), locaplizationHistory);
           Log.d("HISTORY FRAGMENT", "StableAdapter object created ERROR.");
           throw new ExceptionInInitializerError();
       }



        return rootView;
    }

    private class StableAdapter extends ArrayAdapter<LocalizationModel>{
        Context context;
        ArrayList<LocalizationModel> objects;
        HashMap<LocalizationModel, Integer> mIdMap = new HashMap<LocalizationModel, Integer>();

        public StableAdapter(Context context, ArrayList<LocalizationModel> objects) {
            super(context, -1, objects);
            this.context = context;
            this.objects = objects;

            if(!objects.isEmpty()){
                for (int i = 0; i < objects.size(); i++) {
                    mIdMap.put(objects.get(i), i);
                }
            }
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View rootView = inflater.inflate(R.layout.history_list_record, parent, false);
            ImageView iconMap = (ImageView) rootView.findViewById(R.id.icon_map);
            ImageView iconDelete = (ImageView) rootView.findViewById(R.id.icon_delete);
            TextView name = (TextView) rootView.findViewById(R.id.firstLine);
            TextView date = (TextView) rootView.findViewById(R.id.secondLine);

            name.setText(objects.get(position).getName());
            date.setText(objects.get(position).getDate() + " " + objects.get(position).getTime());

            return rootView;
        }

        @Override
        public long getItemId(int position) {
            return super.getItemId(position);
        }

        @Override
        public boolean hasStableIds() {
            return super.hasStableIds();
        }
    }
}
