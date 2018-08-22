package com.guiajequi;



import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

import com.androidquery.AQuery;

import com.guiajequi.main.Constants;
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


public class HomeActiviry extends BaseActivity implements OnClickListener {
	AQuery aq;
	private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();

	int Count=0;
	Button btn;
	
	List<HashMap<String,String>> list;
	ArrayList<HashMap<String, String>> compList = new ArrayList<HashMap<String,String>>();
	
	DisplayImageOptions options;
	ImageLoaderConfiguration imgconfig; 
	final String PREFS_NAME = "MyPrefsFile";
	SharedPreferences settings;
	ConnectionDetector cd;
	categoryAdapter adptr;
	View progressBar;
	
	EditText txtSearch;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.home);


		if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
			NotificationManager mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
			NotificationChannel mChannel = new NotificationChannel(Constants.CHANNEL_ID, Constants.CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH);
			mChannel.setDescription(Constants.CHANNEL_DESCRIPTION);
			mChannel.enableLights(true);
			mChannel.setLightColor(Color.RED);
			mChannel.enableVibration(true);
			mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
			mNotificationManager.createNotificationChannel(mChannel);
		}


		cd = new ConnectionDetector(getApplicationContext());
		settings = getSharedPreferences(PREFS_NAME, 0);
		
        File cacheDir = StorageUtils.getCacheDirectory(this);
		
		options = new DisplayImageOptions.Builder()
		.showImageOnLoading(R.drawable.home_btn_default)
		.showImageForEmptyUri(R.drawable.home_btn_default)
		.showImageOnFail(R.drawable.home_btn_default)
		.cacheInMemory(true)
		.cacheOnDisk(true)
		.considerExifParams(true)
		.displayer(new RoundedBitmapDisplayer(0))
		.build();
		
		imgconfig = new ImageLoaderConfiguration.Builder(this)
		.build();
		ImageLoader.getInstance().init(imgconfig);
        
		progressBar = (View)findViewById(R.id.progressBarView);
		progressBar.setVisibility(View.GONE);

		Spinner spinner = (Spinner) findViewById(R.id.spinnerCidade);
		List<String> cidades = new ArrayList<>(Arrays.asList("CAPELINHA-MG","MINAS NOVAS-MG","TURMALINA-MG","VEREDINHA-MG"));
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, cidades );
		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		spinner.setAdapter(dataAdapter);

        
        try {
			compList = (ArrayList<HashMap<String,String>>) ObjectSerializer.deserialize(settings.getString(ConstValue.PREF_MAINMENU, ObjectSerializer.serialize(new ArrayList<HashMap<String,String>>())));		
		}catch (IOException e) {
			    e.printStackTrace();
		}
        
        GridView grid = (GridView)findViewById(R.id.gridView1);
        adptr = new categoryAdapter(getApplicationContext(), compList);
        grid.setAdapter(adptr);
        
        grid.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				HashMap<String, String> map = compList.get(position);
				ConstValue.sel_main_category = map;
				loadcatActivity();
			}
		});

        new MainMenuTask().execute(true);
        
        
        txtSearch = (EditText)findViewById(R.id.editSearch);
        txtSearch.setOnEditorActionListener(new OnEditorActionListener() {

			public boolean onEditorAction(TextView v, int actionId,
					KeyEvent event) {
				// TODO Auto-generated method stub
				if (actionId == EditorInfo.IME_ACTION_SEARCH ||
			            actionId == EditorInfo.IME_ACTION_DONE ||
			            event.getAction() == KeyEvent.ACTION_DOWN &&
			            event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {

			        	Intent intent = new Intent(HomeActiviry.this,CompanyMain.class);
						intent.putExtra("search", txtSearch.getText().toString());
			        	startActivity(intent);
			           return true; // consume.

			    }
			    return false; // pass on to other listeners. 
			}
		});
        
        ImageView imgSearch = (ImageView)findViewById(R.id.ImageView01);
        imgSearch.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(HomeActiviry.this,CompanyMain.class);
				intent.putExtra("search", txtSearch.getText().toString());
	        	startActivity(intent);
			}
		});
    }
    public void loadcatActivity()
    {
    	Intent intent=null;
		intent=new Intent(this,Categories.class);
		startActivity(intent);
    }

	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent intent=null;
		intent=new Intent(this,Categories.class);
		intent.putExtra("company", "company3");
		switch (v.getId()) {
/*		case R.id.btn1:
			ConstValue.SELECTED_CATEGORY_ID = "1";
			ConstValue.SELECTED_CATEGORY_TITLE = "Restaurent";
			break;
		case R.id.btn2:
			ConstValue.SELECTED_CATEGORY_ID = "2";
			ConstValue.SELECTED_CATEGORY_TITLE = "Hotels";
			break;
		case R.id.btn3:
			ConstValue.SELECTED_CATEGORY_ID = "3";
			ConstValue.SELECTED_CATEGORY_TITLE = "Tours And Travels";
			break;
		case R.id.btn4:
			ConstValue.SELECTED_CATEGORY_ID = "4";
			ConstValue.SELECTED_CATEGORY_TITLE = "Hospitals";
			break;
		case R.id.btn5:
			ConstValue.SELECTED_CATEGORY_ID = "5";
			ConstValue.SELECTED_CATEGORY_TITLE = "Bank & ATM";
			break;
		case R.id.btn6:
			ConstValue.SELECTED_CATEGORY_ID = "6";
			ConstValue.SELECTED_CATEGORY_TITLE = "Petrol Pump";
			break;
		case R.id.btn7:
			ConstValue.SELECTED_CATEGORY_ID = "7";
			ConstValue.SELECTED_CATEGORY_TITLE = "Schools And Colleges";
			break;
		case R.id.btn8:
			ConstValue.SELECTED_CATEGORY_ID = "8";
			ConstValue.SELECTED_CATEGORY_TITLE = "Small Business";
			break;
		case R.id.btn9:
			ConstValue.SELECTED_CATEGORY_ID = "9";
			ConstValue.SELECTED_CATEGORY_TITLE = "Shopping Mall";
			break; */
		default:
			break;
		}
		if(intent!=null)
			startActivity(intent);
	}
    
	public class MainMenuTask extends AsyncTask<Boolean, Void, ArrayList<HashMap<String, String>>> {

		JSONParser jParser;
		JSONObject json;
		
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			progressBar.setVisibility(View.VISIBLE);
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
			progressBar.setVisibility(View.GONE);
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
					Toast.makeText(getApplicationContext(), "Please connect mobile with working Internet", Toast.LENGTH_LONG).show();
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
  

	public class categoryAdapter extends BaseAdapter{

		Context act;
		ArrayList<HashMap<String, String>> data;
		private LayoutInflater inflater=null;
		public categoryAdapter(Context a,ArrayList<HashMap<String, String>> maincat){
    		act = a;
    		
    		data = maincat;
    		
    		inflater = (LayoutInflater)act.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
			 View vi=convertView;
		        if(convertView==null)
		            vi = inflater.inflate(R.layout.row_main_category, null);
	        
		        HashMap<String, String> map = new HashMap<String, String>();
		        map = data.get(position);
		       TextView txt = (TextView)vi.findViewById(R.id.textTitle);
		        String title = map.get("title");
		        if(map.get("title").length()>15)
		        {
		        	title = map.get("title").substring(0, 14) + "..";
		        }
		        String upperString = title.substring(0,1).toUpperCase() + title.substring(1).toLowerCase();
		        txt.setText(upperString);
		        
		        ImageView btn = (ImageView)vi.findViewById(R.id.imageView1);
		        //if(position<ConstValue.mainCatIcons.length)
		        //{
		        //	btn.setImageResource(ConstValue.mainCatIcons[position]);
		        //}
		        ImageLoader.getInstance().displayImage(ConstValue.CONTENTS_IMAGE+"small/"+map.get("icon"), btn, options, animateFirstListener);
		        
		        return vi;
		}
		
	}

	
}
