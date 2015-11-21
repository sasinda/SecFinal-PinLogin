package ct.pinlogin.listener;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import ct.pinlogin.activity.LoginActivity;
import ct.pinlogin.model.KeyPress;

/**
 * Created by sasinda on 11/16/15.
 */
public class MyPressureListener implements View.OnTouchListener {

    View myView;
    LoginActivity myAct;
    /**
     *
     * @param myView  my target view, wich the inputs should be forwarded
     * @param loginActivity
     */
    public MyPressureListener(View myView, LoginActivity loginActivity) {
        this.myView = myView;
        myAct=loginActivity;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        Log.i("keyPressure", "" + event.getPressure());

        KeyPress kp=myAct.getKeyPress();
        if(kp==null){
            //init for next keypress
            kp = new KeyPress();
            myAct.setKeyPress(kp);
            kp.setStartAt(event.getDownTime());
            kp.setStartPressure(event.getPressure());
        }
        if(event.getAction()==MotionEvent.ACTION_DOWN){
            kp.addPressurePoint(event.getPressure());
        }
        if(event.getAction()==MotionEvent.ACTION_UP){
            kp.addPressurePoint(event.getPressure());
            kp.setEndAt(event.getEventTime());
            kp.setEndPressure(event.getPressure());
        }
        return myView.onTouchEvent(event);
    }



}
