package com.m520it.jdmall03.activity;

import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.ImageView;

import com.m520it.jdmall03.R;
import com.m520it.jdmall03.util.ActivityUtil;

public class SplashActivity extends BaseActivity {

	private ImageView mIv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		initUI();
		alphaAnim();
	}
	
	private void alphaAnim(){
		AlphaAnimation anim=new AlphaAnimation(0.2f, 1.0f);
		anim.setAnimationListener(new AnimationListener() {
			
			@Override
			public void onAnimationStart(Animation arg0) {
				
			}
			
			@Override
			public void onAnimationRepeat(Animation arg0) {
				
			}
			
			@Override
			public void onAnimationEnd(Animation arg0) {
//				Æô¶¯µÇÂ¼
				ActivityUtil.start(SplashActivity.this, LoginActivity.class,true);
			}
		});
		anim.setDuration(3000);
		anim.setFillAfter(true);
		mIv.startAnimation(anim);
	}


	@Override
	protected void initUI() {
		mIv =(ImageView) findViewById(R.id.logo_iv);
	}
	

}
