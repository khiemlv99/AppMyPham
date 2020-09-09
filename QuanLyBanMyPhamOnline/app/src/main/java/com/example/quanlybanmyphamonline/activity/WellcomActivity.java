package com.example.quanlybanmyphamonline.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.quanlybanmyphamonline.R;

public class WellcomActivity extends AppCompatActivity {

    ImageView imgview;
    Animation anim;
    LottieAnimationView lottieAnimationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wellcom);
        getSupportActionBar().hide();
        //imgview = findViewById(R.id.imageViewvui);
        lottieAnimationView = findViewById(R.id.animation_view);

//        anim = AnimationUtils.loadAnimation(this, R.anim.anim_welcom_transition);
//        imgview.startAnimation(anim);
        startAnimationLip();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(WellcomActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        },3000);
    }
    private void startAnimationLip()
    {
        ValueAnimator animator = ValueAnimator.ofFloat(0f,1f).setDuration(3000);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                lottieAnimationView.setProgress((Float)animation.getAnimatedValue());

            }
        });
        if(lottieAnimationView.getProgress() == 0f)
        {
            animator.start();
        }
        else {
            lottieAnimationView.setProgress(0f);
        }
    }
}