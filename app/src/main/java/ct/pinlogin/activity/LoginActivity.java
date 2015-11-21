package ct.pinlogin.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.inputmethodservice.Keyboard;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;

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

    private Activity me=this;
    private FloatingActionButton btnTrain;
    private Button btnLogin;
    private CustomKeyboardView kbView;
    private EditText pinInput;
    private Keyboard kb;
    private ColumnChartView chart;
    private ChartState chartState = new ChartState();

    private List<KeyPress> kpList = new ArrayList<>();
    private KeyPress keyPress;
    private boolean trainMode = false;

    private Classifier classifier;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        btnTrain = (FloatingActionButton) findViewById(R.id.fab);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        configButton();
        //setup custom keyboard
        kb = new Keyboard(this, R.xml.keyboard);
        pinInput = (EditText) findViewById(R.id.pinInput);
        pinInput.setOnTouchListener(new View.OnTouchListener() {

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
        btnTrain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get into training mode
                if (trainMode) {
                    //when exit training mode fit the model with recorded data
                    classifier.fit(kpList);
                    trainMode = false;
                    btnTrain.setColorFilter(Color.RED);
                } else {
                    //activate training mode
                    trainMode = true;
                    btnTrain.setColorFilter(Color.GREEN);
                }
                //reset state
                kpList = new ArrayList<KeyPress>();
                chartState=new ChartState();
                chart.refreshDrawableState();
                pinInput.setText("");
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean predict = classifier.predict(kpList);
                //reset state
                pinInput.setText("");
                chartState=new ChartState();
                chart.refreshDrawableState();
                kpList=new ArrayList<KeyPress>();

                //Alert Box
                AlertDialog.Builder alt_bld = new AlertDialog.Builder(me);
                if (predict) {
                    alt_bld.setMessage("Login Success");
                } else {
                    alt_bld.setMessage("Login Failed, Try Again");
                    kpList = new ArrayList<KeyPress>();
                    chartState = new ChartState();
                }
                alt_bld.setCancelable(true);
                alt_bld.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                    }
                });
//                alt_bld.create();
                alt_bld.show();
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
        chartState.update(chart, keyPress);
    }
}
