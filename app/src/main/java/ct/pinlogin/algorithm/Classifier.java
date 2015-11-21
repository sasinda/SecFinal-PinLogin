package ct.pinlogin.algorithm;

import java.util.List;

import ct.pinlogin.model.KeyPress;

import com.google.gson.Gson;

import android.content.SharedPreferences;

import java.lang.reflect.Type;

import com.google.gson.reflect.TypeToken;

/**
 * Created by Velicue on 2015/11/21.
 */
public class Classifier {
    SharedPreferences pref;

    public Classifier(SharedPreferences _pref) {
        pref = _pref;
    }

    public void fit(List<KeyPress> keys) {
        Gson gson = new Gson();
        String json = gson.toJson(keys);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("trainset", json);
        editor.commit();
        return;
    }

    public boolean predict(List<KeyPress> keys) {
        Gson gson = new Gson();
        String json = pref.getString("trainset", "");
        if (json.equals("")) {
            return false;
        }
        Type listType = new TypeToken<List<KeyPress>>() {
        }.getType();
        List<KeyPress> train = gson.fromJson(json, listType);
        if (train.size() != keys.size()) {
            return false;
        }
        double durationE = 0;
        double pressureE = 0;
        double dc = 500;
        double pc = 0.005;
        for (int i = 0; i < train.size(); i++) {
            double dtr = train.get(i).getDuration();
            double dte = keys.get(i).getDuration();
            double ptr = train.get(i).getPressure();
            double pte = keys.get(i).getPressure();
            durationE += (dtr - dte) * (dtr - dte) / (dtr + dc);
            pressureE += (dtr - dte) * (dtr - dte) / (dtr + dc);
        }
        durationE /= train.size();
        pressureE /= train.size();
        if (durationE > 300) return false;
        if (pressureE > 0.06) return false;
        else return true;
    }
}
