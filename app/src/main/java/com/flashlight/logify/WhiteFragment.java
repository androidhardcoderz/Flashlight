package com.flashlight.logify;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;


/**
 * A simple {@linkFragment} subclass.
 * Use the {@linkWhiteFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WhiteFragment extends Fragment {

    public static final float HIGH_BRIGHTNESS = 1F;


    public WhiteFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
           //no arguments
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.white_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        changeScreenBrightness(HIGH_BRIGHTNESS);
    }

    private void changeScreenBrightness(float amount){

        //gets the current windows layout
        WindowManager.LayoutParams layout = getActivity().getWindow().getAttributes();
        layout.screenBrightness = amount; //sets brightness factor from 0F to 1F
        getActivity().getWindow().setAttributes(layout);

    }
}
