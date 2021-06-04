package com.example.shopify.helper;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefManager {

    private String SHARED_PREF_NAME = "Shopify Shared Preference";
    private String KEY_NAME = "Name";
    private String KEY_EMAIL = "Email";
    private String KEY_LOGIN = "login";
    private String KEY_SHOP_NAME = "ShName";
    private String KEY_SHOP_ADDRESS = "ShAdd";
    private String KEY_SHOP_PH = "ShopPhone";

    @SuppressLint("StaticFieldLeak")
    private static SharedPrefManager mInstance;
    @SuppressLint("StaticFieldLeak")
    private static Context mCtx;
    private final SharedPreferences sharedPreferences;
    private final SharedPreferences.Editor editor;

    @SuppressLint("CommitPrefEdits")
    public SharedPrefManager(Context context) {
        mCtx = context;
        sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }


    public static synchronized SharedPrefManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SharedPrefManager(context);
        }
        return mInstance;
    }

    public void login()
    {
        editor.putBoolean(KEY_LOGIN, true);
        editor.apply();
    }

    public void setShopPH(String phone)
    {
        editor.putString(KEY_SHOP_PH, phone);
        editor.apply();
    }

    public String getShopPH()
    {
        return sharedPreferences.getString(KEY_SHOP_PH, null);
    }

    public void setEmail(String email)
    {
        editor.putString(KEY_EMAIL, email);
        editor.apply();
    }
    public void setName(String name)
    {
        editor.putString(KEY_NAME, name);
        editor.apply();
    }
   public void setShopName(String shname)
   {
       editor.putString(KEY_SHOP_NAME, shname);
       editor.apply();
   }
   public void setShopAddress(String shadd)
   {
       editor.putString(KEY_SHOP_ADDRESS, shadd);
       editor.apply();
   }
    public void loginUser(String name, String email, String shname, String shadd, String phone)
    {
        setEmail(email);
        setName(name);
        setShopName(shname);
        setShopAddress(shadd);
        setShopPH(phone);
        login();
    }

    public boolean isUserLoggedIn()
    {
        return sharedPreferences.getBoolean(KEY_LOGIN, false);
    }

    public void logout()
    {
        editor.putBoolean(KEY_LOGIN, false);
        editor.apply();
    }

    public String getName()
    {
        return  sharedPreferences.getString(KEY_NAME, null);
    }
    public String getShopName()
    {
        return sharedPreferences.getString(KEY_SHOP_NAME, null);
    }

    public String getShopAddress()
    {
        return  sharedPreferences.getString(KEY_SHOP_ADDRESS, null);
    }



}
