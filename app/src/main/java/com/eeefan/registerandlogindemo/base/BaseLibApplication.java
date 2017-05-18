package com.eeefan.registerandlogindemo.base;


import android.app.Application;
import com.facebook.stetho.Stetho;

/**
 * 捕获应用异常Application
 * 在这里完成整个应用退出；在这里进行全局变量的传递；在这里完成低内存的释放；在这里捕获未抓住的异常；用于应用配置, 预加载处理
 *
 * @author eeefan
 */


public class BaseLibApplication extends Application{
	
	private static BaseLibApplication instance;
	 

	@Override
	public void onCreate() {
		super.onCreate();
		Stetho.initializeWithDefaults(this);
		setInstance(this); 
      
	} 
	
	public static void setInstance(BaseLibApplication instance) {
		BaseLibApplication.instance = instance;
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
	public static BaseLibApplication getInstance()
	{
		return instance;
	}
 
}
