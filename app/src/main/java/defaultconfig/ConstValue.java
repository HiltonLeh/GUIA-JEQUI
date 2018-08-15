package defaultconfig;

import java.util.ArrayList;
import java.util.HashMap;


public class ConstValue {

	public static final boolean DEBUG_MODE = true;
	public static final String LOG_TAG = "MAGIC_TIPS";
	public static String DATABASE_URL="data/data/com.way.magictrik/database";
	
	//------------new json----------
	public static final String BASE_URL = "http://valeconsultoria.com";
	public static final String JSON_MAINCAT = BASE_URL+"/index.php?component=json&action=maincategory";
	public static final String JSON_CAT = BASE_URL+"/index.php?component=json&action=categories&id=";
	public static final String JSON_COMPANI = BASE_URL+"/index.php?component=json&action=companies_new&cat=";
	public static final String JSON_COMPANI_NEAR_BY = BASE_URL+"/index.php?component=json&action=getnearby";
	public static final String JSON_COMPANI_REVIEW = BASE_URL+"/index.php?component=json&action=reviews&id=";

	public static final String JSON_REVIEW_ADD = BASE_URL+"/index.php?component=review&action=add";
	public static final String JSON_INQUIRY_ADD = BASE_URL+"/index.php?component=inquiry&action=add";
	public static final String JSON_REGISTRATION_ADD = BASE_URL+"/index.php?component=user&action=addpost";
	
	//----------IMAGES URL------------//
	public static final String CONTENTS_IMAGE = BASE_URL+"/userfiles/contents/";
	//----------JSON URLS------------//
	
	
	//----------MAIN CATEGORY CONSTANTS------------//
	public static String SELECTED_MAINCATEGORY_TITLE;
	public static String SELECTED_CATEGORY_TITLE;
	
	public static String SEARCH_TEXT="";
	

	

	public static ArrayList<HashMap<String, String>> compList1 = new ArrayList<HashMap<String,String>>();
	public static ArrayList<HashMap<String, String>> compList2 = new ArrayList<HashMap<String,String>>();
	public static ArrayList<HashMap<String, String>> compList3 = new ArrayList<HashMap<String,String>>();
	
	
	public static HashMap<String, String> sel_main_category = new HashMap<String, String>();
	public static HashMap<String, String> sel_category = new HashMap<String, String>();
	public static HashMap<String, String> sel_company = new HashMap<String, String>();
	
	public static String PREF_MAINMENU = "MAINMENUPREF";
	public static String PREF_CATEGORIES = "CATEGORIESPREF";
	public static String PREF_COMPANYIES = "COMPANYPREF";
	public static String PREF_REVIEW = "REVIEWPREF";
}
