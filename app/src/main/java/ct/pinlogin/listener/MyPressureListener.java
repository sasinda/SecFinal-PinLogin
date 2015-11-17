package ct.pinlogin.listener;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import ct.pinlogin.activity.LoginActivity;
import ct.pinlogin.model.EventList;
import ct.pinlogin.model.KeyPress;

/**
 * Created by sasinda on 11/16/15.
 */
public class MyPressureListener implements View.OnTouchListener {

    View myView;
    EventList list=new EventList();
    int currentEvent=0;
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

        KeyPress kp;
        if(event.getAction()==MotionEvent.ACTION_DOWN){
            kp = new KeyPress();
            kp.setStartAt(event.getDownTime());
            kp.setStartPressure(event.getPressure());
            list.add(kp);
        }
        if(event.getAction()==MotionEvent.ACTION_UP){
            kp=list.get(currentEvent);
            assert kp.getStartAt()==event.getDownTime();
            kp.setEndAt(event.getEventTime());
            kp.setEndPressure(event.getPressure());
            list.updateChart( myAct.getChart(),kp);
            currentEvent++;
        }
        return myView.onTouchEvent(event);
    }



}
