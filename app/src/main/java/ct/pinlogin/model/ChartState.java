package ct.pinlogin.model;

import android.graphics.Color;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.model.Column;
import lecho.lib.hellocharts.model.ColumnChartData;
import lecho.lib.hellocharts.model.SubcolumnValue;
import lecho.lib.hellocharts.view.ColumnChartView;

/**
 * Created by sasinda on 11/21/15.
 */
public class ChartState {

    ColumnChartData data=new ColumnChartData();
    List<SubcolumnValue> values =new ArrayList<>();


    public void update(ColumnChartView chart, KeyPress kp){
        SubcolumnValue tval = new SubcolumnValue();
        tval.setColor(Color.MAGENTA);
        tval.setValue(kp.getDuration());
        values.add(tval);

        SubcolumnValue pVal=new SubcolumnValue();
        pVal.setColor(Color.BLUE);
        float p = (float) kp.getStartPressure()*10;
        pVal.setValue(p);
        values.add(pVal);


        Column col = new Column().setValues(values);


        List<Column> cols = new ArrayList<Column>();
        cols.add(col);

        data.setStacked(false);
        data.setColumns(cols);
        chart.setColumnChartData(data);
        chart.refreshDrawableState();
    }
}
