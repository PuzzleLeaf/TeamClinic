package puzzleleaf.tistory.com.teamclinic.Unity;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.PixelFormat;
import android.inputmethodservice.Keyboard;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.Window;

import com.unity3d.player.UnityPlayer;

public class UnityPlayerActivity extends Activity
{
	protected UnityPlayer mUnityPlayer; // don't change the name of this variable; referenced from native code

	String contents = "1";
	// Setup activity layout
	@Override protected void onCreate (Bundle savedInstanceState)
	{
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		getWindow().setFormat(PixelFormat.RGBX_8888); // <--- This makes xperia play happy

		Intent intent = getIntent();
		contents = intent.getExtras().getString("contents");

		mUnityPlayer = new UnityPlayer(this);
		setContentView(mUnityPlayer);
		mUnityPlayer.requestFocus();
		handler.sendEmptyMessageDelayed(0,9000);
	}


	final Handler handler = new Handler(){
	@Override
	public void handleMessage(Message msg) {
		UnityPlayer.UnitySendMessage("Manager","AndroidToUnity",contents);
	}
};


	// Quit Unity
	@Override protected void onDestroy ()
	{
		mUnityPlayer.quit();
		super.onDestroy();
	}

	// Pause Unity
	@Override protected void onPause()
	{
		super.onPause();
		mUnityPlayer.pause();
	}

	// Resume Unity
	@Override protected void onResume()
	{
		super.onResume();
		mUnityPlayer.resume();
	}

	// This ensures the layout will be correct.
	@Override public void onConfigurationChanged(Configuration newConfig)
	{
		super.onConfigurationChanged(newConfig);
		mUnityPlayer.configurationChanged(newConfig);
	}

	// Notify Unity of the focus change.
	@Override public void onWindowFocusChanged(boolean hasFocus)
	{
		super.onWindowFocusChanged(hasFocus);
		mUnityPlayer.windowFocusChanged(hasFocus);
	}

	// Low Memory Unity
	@Override public void onLowMemory()
	{
		super.onLowMemory();
		mUnityPlayer.lowMemory();
	}

	// Trim Memory Unity
	@Override public void onTrimMemory(int level)
	{
		super.onTrimMemory(level);
		if (level == TRIM_MEMORY_RUNNING_CRITICAL)
		{
			mUnityPlayer.lowMemory();
		}
	}

	// For some reason the multiple keyevent type is not supported by the ndk.
	// Force event injection by overriding dispatchKeyEvent().
	@Override public boolean dispatchKeyEvent(KeyEvent event)
	{
		if (event.getAction() == KeyEvent.ACTION_MULTIPLE)
			return mUnityPlayer.injectEvent(event);
		return super.dispatchKeyEvent(event);
	}


	// Pass any events not handled by (unfocused) views straight to UnityPlayer
	@Override public boolean onKeyUp(int keyCode, KeyEvent event)
	{ return mUnityPlayer.injectEvent(event); }

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode == KeyEvent.KEYCODE_BACK) {
			mUnityPlayer.quit();
			return true;
		}
		else{
			return false;
		}
	}

	@Override public boolean onTouchEvent(MotionEvent event){
		return mUnityPlayer.injectEvent(event);
	}

	/*API12*/ public boolean onGenericMotionEvent(MotionEvent event)  { return mUnityPlayer.injectEvent(event); }
}
