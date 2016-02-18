package com.flashlight.logify;

import android.hardware.Camera;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.GestureDetectorCompat;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment
        implements GestureDetector.OnGestureListener {

    /*
        TO DO:
        -animate background transition
     */

    private Camera cam;
    private Camera.Parameters camParams;
    private boolean hasCam,flashState;
    private RelativeLayout mainLayout;
    private GestureDetectorCompat gDetector;
    private ImageView stateImage;

    public MainActivityFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.gDetector = new GestureDetectorCompat(getActivity(),this);

        flashState = true;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }


    /**
     * turns the flashlight on devices on or off based on current state
     */
    private void turnOnOff() {

        if (flashState) {
            camParams.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
            setDayBackground();
            flashState = true;
        }

        if (!flashState) {
            camParams.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
            setNightBackground();
            flashState = false;
        }

        cam.setParameters(camParams);
        cam.startPreview();
    }

    @Override
    public void onDestroy() {

        if (cam != null) {
            cam.release();
            Log.d(this.getClass().getSimpleName(), "released");
        }

        Log.i(this.getClass().getSimpleName(), "Destroyed ");
        super.onDestroy();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //build and show moon animation
        mainLayout = (RelativeLayout) view.findViewById(R.id.flashlightRelativeLayout);
        stateImage = (ImageView) view.findViewById(R.id.typeImageView);

        getView().setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                //turn flash off/on
               gDetector.onTouchEvent(event);
                return true;
            }
        });


        try {
            Log.d("TORCH", "Check cam");
            // Get CAM reference
            cam = Camera.open();
            camParams = cam.getParameters();
            cam.startPreview();
            hasCam = true;
            Log.d("TORCH", "HAS CAM [" + hasCam + "]");
        } catch (Throwable t) {
            t.printStackTrace();
        }


        //set background for which fragment is transaction too!
        setDayBackground();

        //turn on flash here!!
        turnOnOff();

    }


    private void setNewStateImage(int image){
        stateImage.setImageDrawable(getActivity().getResources().getDrawable(image));
    }

    private void setNightBackground(){
        setBackground(mainLayout, R.drawable.night_background);
        setNewStateImage(R.drawable.moon);
    }

    private void setDayBackground(){
        setBackground(mainLayout, R.drawable.day_background);
        setNewStateImage(R.drawable.sun);
    }

    private void setBackground(RelativeLayout layout,int drawable){

        final int sdk = android.os.Build.VERSION.SDK_INT;
        if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
            layout.setBackgroundDrawable( getResources().getDrawable(drawable) );
        } else {
            layout.setBackground( getResources().getDrawable(drawable));
        }
    }

    /**
     * Notified when a tap occurs with the down {@link MotionEvent}
     * that triggered it. This will be triggered immediately for
     * every down event. All other events should be preceded by this.
     *
     * @param e The down motion event.
     */
    @Override
    public boolean onDown(MotionEvent e) {
        Log.i(this.getClass().getSimpleName(),"On Down Event Registered");
        if(flashState){
            //its on right now set to off
            flashState = false;
            turnOnOff();
        }else{
            flashState = true;
            turnOnOff();
        }
        return false;
    }

    /**
     * The user has performed a down {@link MotionEvent} and not performed
     * a move or up yet. This event is commonly used to provide visual
     * feedback to the user to let them know that their action has been
     * recognized i.e. highlight an element.
     *
     * @param e The down motion event
     */
    @Override
    public void onShowPress(MotionEvent e) {
        Log.i(this.getClass().getSimpleName(),"Show Press Event Registered");
    }

    /**
     * Notified when a tap occurs with the up {@link MotionEvent}
     * that triggered it.
     *
     * @param e The up motion event that completed the first tap
     * @return true if the event is consumed, else false
     */
    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        Log.i(this.getClass().getSimpleName(),"Single Tap Event Registered");
        return false;
    }

    /**
     * Notified when a scroll occurs with the initial on down {@link MotionEvent} and the
     * current move {@link MotionEvent}. The distance in x and y is also supplied for
     * convenience.
     *
     * @param e1        The first down motion event that started the scrolling.
     * @param e2        The move motion event that triggered the current onScroll.
     * @param distanceX The distance along the X axis that has been scrolled since the last
     *                  call to onScroll. This is NOT the distance between {@code e1}
     *                  and {@code e2}.
     * @param distanceY The distance along the Y axis that has been scrolled since the last
     *                  call to onScroll. This is NOT the distance between {@code e1}
     *                  and {@code e2}.
     * @return true if the event is consumed, else false
     */
    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        Log.i(this.getClass().getSimpleName(),"Scroll Event Registered");
        return false;
    }

    /**
     * Notified when a long press occurs with the initial on down {@link MotionEvent}
     * that trigged it.
     *
     * @param e The initial on down motion event that started the longpress.
     */
    @Override
    public void onLongPress(MotionEvent e) {
        Log.i(this.getClass().getSimpleName(),"Long Press Event Registered");
    }

    /**
     * Notified of a fling event when it occurs with the initial on down {@link MotionEvent}
     * and the matching up {@link MotionEvent}. The calculated velocity is supplied along
     * the x and y axis in pixels per second.
     *
     * @param e1        The first down motion event that started the fling.
     * @param e2        The move motion event that triggered the current onFling.
     * @param velocityX The velocity of this fling measured in pixels per second
     *                  along the x axis.
     * @param velocityY The velocity of this fling measured in pixels per second
     *                  along the y axis.
     * @return true if the event is consumed, else false
     */
    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        Log.i(this.getClass().getSimpleName(),"Fling Event Registered");
        return true;
    }
}
