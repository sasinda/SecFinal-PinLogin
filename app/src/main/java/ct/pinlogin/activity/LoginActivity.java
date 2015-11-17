package ct.pinlogin.activity;

import android.inputmethodservice.Keyboard;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;

import ct.pinlogin.views.CustomKeyboardView;
import ct.pinlogin.listener.MyKeyboardActionListener;
import ct.pinlogin.listener.MyPressureListener;
import ct.pinlogin.R;
import lecho.lib.hellocharts.gesture.ContainerScrollType;
import lecho.lib.hellocharts.view.ColumnChartView;

public class LoginActivity extends AppCompatActivity {

    private CustomKeyboardView kbView;
    private View targetView;
    private Keyboard kb;
    private ColumnChartView chart;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //setup custom keyboard
        kb = new Keyboard(this, R.xml.keyboard);
        targetView = (EditText) findViewById(R.id.pinInput);
        targetView.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                showKeyboardWithAnimation();
                return true;
            }
        });
        kbView = (CustomKeyboardView) findViewById(R.id.keyboard_view);
        kbView.setKeyboard(kb);
        kbView.setOnKeyboardActionListener(new MyKeyboardActionListener(
                this));
        kbView.setOnTouchListener(new MyPressureListener(kbView, this));
    
        setupChart();

    }

    private void setupChart() {
        chart = (ColumnChartView) findViewById(R.id.chart);
        chart.setInteractive(true);
        chart.setContainerScrollEnabled(true, ContainerScrollType.HORIZONTAL);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }






    private void showKeyboardWithAnimation() {
        if (kbView.getVisibility() == View.GONE) {
            Animation animation = AnimationUtils
                    .loadAnimation(LoginActivity.this,
                            R.anim.slide_in_bottom);
            kbView.showWithAnimation(animation);
        }
    }

    public ColumnChartView getChart() {
        return chart;
    }
}
