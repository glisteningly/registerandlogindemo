package com.eeefan.registerandlogindemo;


import android.app.Application;
import com.facebook.stetho.Stetho;


public class ItLanbaoLibApplication extends Application{
	
	private static ItLanbaoLibApplication instance;
	 

	@Override
	public void onCreate() {
		super.onCreate();
		Stetho.initializeWithDefaults(this);
//        OkHttpClient okHttpClient = new OkHttpClient();
//        okHttpClient.networkInterceptors().add(new StethoInterceptor());
//        mRequestQueue = Volley.newRequestQueue(this, new OkHttpStack(okHttpClient));
		setInstance(this); 
      
	} 
	
	public static void setInstance(ItLanbaoLibApplication instance) {
		ItLanbaoLibApplication.instance = instance;
	}
	
	/** 
	 * 获取时间戳
	 * @return
	 */
	public String getTime(){
		return String.valueOf(System.currentTimeMillis());
	}

	/**
	 * 获取ItLanBaoApplication实例
	 * 
	 * @return
	 */
	public static ItLanbaoLibApplication getInstance()
	{
		return instance;
	}
 
}
