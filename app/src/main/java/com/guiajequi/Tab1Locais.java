package com.guiajequi;

import android.content.Context;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.utils.StorageUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import defaultconfig.ConstValue;
import framework.BaseActivity;
import framework.ConnectionDetector;
import framework.ObjectSerializer;
import json.JSONParser;

public class Tab1Locais extends Fragment {
    BaseActivity baseActivity;
    DisplayImageOptions options;
    ImageLoaderConfiguration imgconfig;
    final String PREFS_NAME = "MyPrefsFile";
    SharedPreferences settings;
    ConnectionDetector cd;
    List<HashMap<String, String>> list;
    ArrayList<HashMap<String, String>> compList = new ArrayList<HashMap<String, String>>();
    categoryAdapter adptr;
    EditText txtSearch;
    private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.tab1locais, container, false);


        cd = new ConnectionDetector(getActivity ().getApplicationContext());
        settings = getActivity().getSharedPreferences(PREFS_NAME, 0);

        File cacheDir;
        cacheDir = StorageUtils.getCacheDirectory(rootView.getContext());

        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.home_btn_default)
                .showImageForEmptyUri(R.drawable.home_btn_default)
                .showImageOnFail(R.drawable.home_btn_default)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .displayer(new RoundedBitmapDisplayer(0))
                .build();

        imgconfig = new ImageLoaderConfiguration.Builder(rootView.getContext())
                .build();
        ImageLoader.getInstance().init(imgconfig);

        try {
            compList = (ArrayList<HashMap<String, String>>)
                    ObjectSerializer.deserialize(settings.getString(ConstValue.PREF_MAINMENU,
                            ObjectSerializer.serialize(new ArrayList<HashMap<String, String>>())));
        } catch (IOException e) {
            e.printStackTrace();
        }

        GridView grid = (GridView) rootView.findViewById(R.id.gridView1Locais);
        adptr = new categoryAdapter(getActivity().getApplicationContext(), compList);
        grid.setAdapter(adptr);

        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // TODO Auto-generated method stub
                HashMap<String, String> map = compList.get(position);
                ConstValue.sel_main_category = map;
                loadcatActivity(rootView);
            }
        });

        new MainMenuTask().execute(true);


        txtSearch = (EditText) rootView.findViewById(R.id.editSearchLocais);
        txtSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            public boolean onEditorAction(TextView v, int actionId,
                                          KeyEvent event) {
                // TODO Auto-generated method stub
                if (actionId == EditorInfo.IME_ACTION_SEARCH ||
                        actionId == EditorInfo.IME_ACTION_DONE ||
                        event.getAction() == KeyEvent.ACTION_DOWN &&
                                event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {

                    Intent intent = new Intent(rootView.getContext(), CompanyMain.class);
                    intent.putExtra("search", txtSearch.getText().toString());
                    startActivity(intent);
                    return true; // consume.

                }
                return false; // pass on to other listeners.
            }
        });

        ImageView imgSearch = (ImageView) rootView.findViewById(R.id.ImageView01Locais);
        imgSearch.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent intent = new Intent(v.getContext(), CompanyMain.class);
                intent.putExtra("search", txtSearch.getText().toString());
                startActivity(intent);
            }
        });


        String[] values =
                {"CAPELINHA-MG", "MINAS NOVAS-MG", "TURMALINA-MG", "VEREDINHA-MG"};
        Spinner spinner = (Spinner) rootView.findViewById(R.id.spinnerCidadeLocais);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(),
                android.R.layout.simple_spinner_item, values);
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_checked);
        spinner.setAdapter(adapter);


        return rootView;

    }

    public void loadcatActivity(View view) {
        Intent intent = null;
        intent = new Intent(view.getContext(), Categories.class);
        startActivity(intent);
    }

    public class MainMenuTask extends AsyncTask<Boolean, Void, ArrayList<HashMap<String, String>>> {

        JSONParser jParser;
        JSONObject json;

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub

            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(ArrayList<HashMap<String, String>> result) {
            // TODO Auto-generated method stub
            if (result!=null) {

                //adapter.notifyDataSetChanged();
            }
            try {
                settings.edit().putString(ConstValue.PREF_MAINMENU,ObjectSerializer.serialize(compList)).commit();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            adptr.notifyDataSetChanged();

            super.onPostExecute(result);
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            // TODO Auto-generated method stub
            super.onProgressUpdate(values);
        }



        @Override
        protected ArrayList<HashMap<String, String>> doInBackground(
                Boolean... params) {
            // TODO Auto-generated method stub
            try {
                jParser = new JSONParser();

                if(cd.isConnectingToInternet())
                {

                    //progressbar.setVisibility(View.VISIBLE);
                    json = jParser.getJSONFromUrl(ConstValue.JSON_MAINCAT);
                    JSONArray menus = json.getJSONArray("categories");
                    if(menus!=null)
                    {
                        compList.clear();
                        for (int i = 0; i < menus.length(); i++) {
                            JSONObject c = menus.getJSONObject(i);
                            HashMap<String, String> map = new HashMap<String, String>();
                            map.put("id", c.getString("id"));
                            map.put("title", c.getString("title"));
                            map.put("description", c.getString("description"));
                            map.put("icon", c.getString("icon"));
                            compList.add(map);
                        }

                    }

                }else
                {
                    Toast.makeText(getActivity().getApplicationContext(), "Please connect mobile with working Internet",
                            Toast.LENGTH_LONG).show();
                }

                // TODO Auto-generated method stub
                // Getting Array of Size


                jParser = null;
                json = null;

            } catch (Exception e) {
                // TODO: handle exception

                return null;
            }
            return null;
        }

    }



    public class categoryAdapter extends BaseAdapter {

        Context act;
        ArrayList<HashMap<String, String>> data;
        private LayoutInflater inflater = null;

        public categoryAdapter(Context a, ArrayList<HashMap<String, String>> maincat) {
            act = a;

            data = maincat;

            inflater = (LayoutInflater) act.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        public int getCount() {
            // TODO Auto-generated method stub
            return data.size();
        }

        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return null;
        }

        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return 0;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub
            View vi = convertView;
            if (convertView == null)
                vi = inflater.inflate(R.layout.row_main_category, null);

            HashMap<String, String> map = new HashMap<String, String>();
            map = data.get(position);
            TextView txt = (TextView) vi.findViewById(R.id.textTitle);
            String title = map.get("title");
            if (map.get("title").length() > 15) {
                title = map.get("title").substring(0, 14) + "..";
            }
            String upperString = title.substring(0, 1).toUpperCase() + title.substring(1).toLowerCase();
            txt.setText(upperString);

            ImageView btn = (ImageView) vi.findViewById(R.id.imageView1);
            //if(position<ConstValue.mainCatIcons.length)
            //{
            //	btn.setImageResource(ConstValue.mainCatIcons[position]);
            //}
            ImageLoader.getInstance().displayImage(ConstValue.CONTENTS_IMAGE + "small/" + map.get("icon"),
                    btn, options, animateFirstListener);

            return vi;
        }

    }
}
