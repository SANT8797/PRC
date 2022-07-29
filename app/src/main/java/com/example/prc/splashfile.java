package com.example.prc;

import android.content.Intent;
import android.view.WindowManager;

import com.daimajia.androidanimations.library.Techniques;
import com.viksaa.sssplash.lib.activity.AwesomeSplash;
import com.viksaa.sssplash.lib.cnst.Flags;
import com.viksaa.sssplash.lib.model.ConfigSplash;

import androidx.appcompat.app.ActionBar;

/**
 * Created by Sant
 */

public class splashfile extends AwesomeSplash {

    public void initSplash(ConfigSplash configSplash) {
        ActionBar actionBar = getSupportActionBar();


        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        configSplash.setBackgroundColor(R.color.brw);
        configSplash.setAnimCircularRevealDuration(2000);
        configSplash.setRevealFlagX(Flags.REVEAL_LEFT);
        configSplash.setRevealFlagX(Flags.REVEAL_BOTTOM);

        configSplash.setLogoSplash(R.drawable.logo_pal);
        configSplash.setAnimCircularRevealDuration(1000);
        configSplash.setAnimLogoSplashTechnique(Techniques.FadeIn);

        configSplash.setTitleSplash("By Shri. Anil Kumawat");
        configSplash.setTitleTextColor(R.color.clr1);
        configSplash.setTitleTextSize(20f);
        configSplash.setAnimTitleDuration(1000);
        configSplash.setAnimTitleTechnique(Techniques.FadeIn);

    }

    @Override
    public void animationsFinished() {
        Intent intent=new Intent(splashfile.this,MainActivity.class);
        startActivity(intent);
        finish();
    }


}