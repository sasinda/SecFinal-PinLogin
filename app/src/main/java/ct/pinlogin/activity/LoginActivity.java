package ct.pinlogin.activity;

import android.content.Context;
import android.graphics.Color;
import android.inputmethodservice.Keyboard;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;

import java.security.Key;
import java.util.ArrayList;
import java.util.List;

import ct.pinlogin.algorithm.Classifier;
import ct.pinlogin.model.ChartState;
import ct.pinlogin.model.KeyPress;
import ct.pinlogin.views.CustomKeyboardView;
import ct.pinlogin.listener.MyKeyboardActionListener;
import ct.pinlogin.listener.MyPressureListener;
import ct.pinlogin.R;
import lecho.lib.hellocharts.gesture.ContainerScrollType;
import lecho.lib.hellocharts.view.ColumnChartView;

public class LoginActivity extends AppCompatActivity {

    private FloatingActionButton button;
    private CustomKeyboardView kbView;
    private View targetView;
    private Keyboard kb;
    private ColumnChartView chart;
    private ChartState chartState=new ChartState();

    private List<KeyPress> kpList=new ArrayList<>();
    private KeyPress keyPress;
    private boolean trainMode=false;

    private Classifier classifier;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        button= (FloatingActionButton) findViewById(R.id.fab);
        configButton();
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

        classifier = new Classifier(getSharedPreferences("secfinal", Context.MODE_PRIVATE));

    }

    private void configButton() {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get into training mode
                if(trainMode){
                    classifier.fit(kpList);
                    trainMode=false;
                    button.setBackgroundColor(Color.MAGENTA);
                }else {
                    trainMode=true;
                    button.setBackgroundColor(Color.GREEN);
                }
                kpList=new ArrayList<KeyPress>();
            }
        });
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

    public KeyPress getKeyPress() {
        return keyPress;
    }

    public void setKeyPress(KeyPress keyPress) {
        this.keyPress = keyPress;
        kpList.add(keyPress);
    }

    public List<KeyPress> getKpList() {
        return kpList;
    }

    public void updateChart() {
        chartState.update(chart,keyPress);
    }
}
