package com.sychu.thunderchaser;

import android.os.Handler;
import android.os.SystemClock;

interface IOnChronometerTick
{
	public void OnChronometerTick(CustomChronometer chronometer);
}

public class CustomChronometer {

	private IOnChronometerTick mChronometerTick = null;
	private long baseTime = 0;
	private long elapsedTime = 0;
	private Handler mHandler = new Handler();
	private CustomChronometer mChronometer = null;
	private boolean killMe = false;
	
	private long delay = 0;
	private long period = 1000;
	private long nextStep = 0;
	
	private boolean isActive = false;
	


	public void KillMe()
	{
		killMe = true;
	}
	
	public CustomChronometer(long delay, long period, long baseTime, long elapsedTime)
	{
		mChronometer = this;
		if(delay >0)
			this.delay = delay;
		
		if(period > 0)
			this.period = period;
		
		if(baseTime > 0)
			this.baseTime = baseTime;
		else
			this.baseTime  = SystemClock.elapsedRealtime();
		
		if(elapsedTime > 0)
			this.elapsedTime = elapsedTime;
		else
			this.elapsedTime = baseTime;
	}
	
	
	public void start() {
		isActive = true;
		baseTime = SystemClock.uptimeMillis();
		nextStep = baseTime + period;
		mHandler.removeCallbacks(mTick);
		mHandler.postDelayed(mTick, delay);
	}
	
	public void cont() {
		isActive = true;
		mHandler.removeCallbacks(mTick);
		mHandler.postDelayed(mTick, 0);
	}
	
	
	public void stop() {
		getElapsedTime();
		isActive = false;
		mHandler.removeCallbacks(mTick);
		if(mChronometerTick != null)
			mChronometerTick.OnChronometerTick(mChronometer);
	}
	
	
	public long getBaseTime() {
		return baseTime;
	}
	
	public long getElapsedTime() {
		if(isActive)
			elapsedTime = SystemClock.uptimeMillis();
		
		return 	elapsedTime;
	}
	
	public long getDelta() {	
		getElapsedTime();
		return elapsedTime - baseTime;
	}
	
	public void setChronometerTickEvent(IOnChronometerTick chronometerTickEvent) {
		mChronometerTick = chronometerTickEvent;
	}
	
	private Runnable mTick = new Runnable() {
		
		public void run() {
			if(killMe)
				return;
			
			long delta = getDelta();
			
			if(nextStep<elapsedTime)
			{
				nextStep=baseTime + delta/period * period + period;
			}
			
			if(mChronometerTick != null)
				mChronometerTick.OnChronometerTick(mChronometer);

			mHandler.postAtTime(this, nextStep);
			nextStep+=period;
		}
	};
	
}
