package com.example.quanlybanmyphamonline.Fragment;

import android.animation.ValueAnimator;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.airbnb.lottie.LottieAnimationView;
import com.example.quanlybanmyphamonline.Class.checkconnection;
import com.example.quanlybanmyphamonline.R;


public class ThongBaoFragment extends Fragment {

    LottieAnimationView lottieAnimationView;
    LottieAnimationView notifycation;
    // TODO: Rename and change types of parameters


    public ThongBaoFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_thong_bao, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        lottieAnimationView = getView().findViewById(R.id.lottie_nointernetThongBao);
        notifycation = getView().findViewById(R.id.lottie_notifycation);
        if(checkconnection.haveNetworkConnection(getContext()))
        {
            lottieAnimationView.setVisibility(View.GONE);
            notifycation.setVisibility(View.VISIBLE);
            //startAnimationLip();


        } else {
            checkconnection.Toast_Short(getContext(), "Hãy kiểm tra lại kết nối ");
            notifycation.setVisibility(View.GONE);
            lottieAnimationView.setVisibility(View.VISIBLE);
            //startAnimationLip();

        }
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
