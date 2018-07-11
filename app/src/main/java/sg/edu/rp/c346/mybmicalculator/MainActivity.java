package sg.edu.rp.c346.mybmicalculator;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    EditText etWeight, etHeight;
    Button btnCalc, btnReset;
    TextView tvDate, tvBMI, tvDesc;

    float weight;
    float height;
    float bmi;

    Calendar now = Calendar.getInstance();  //Create a Calendar object with current date and time
    final String datetime = now.get(Calendar.DAY_OF_MONTH) + "/" +
            (now.get(Calendar.MONTH)+1) + "/" +
            now.get(Calendar.YEAR) + " " +
            now.get(Calendar.HOUR_OF_DAY) + ":" +
            now.get(Calendar.MINUTE);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etWeight = findViewById(R.id.editTextWeight);
        etHeight = findViewById(R.id.editTextHeight);
        btnCalc = findViewById(R.id.buttonCalculate);
        btnReset = findViewById(R.id.buttonResetData);
        tvDate = findViewById(R.id.textViewDate);
        tvBMI = findViewById(R.id.textViewBMI);
        tvDesc = findViewById(R.id.textViewDesc);


        btnCalc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                weight = Float.parseFloat(etWeight.getText().toString());
                height = Float.parseFloat(etHeight.getText().toString());
                bmi = weight / (height * height);

                //Step 1a: Obtain an instance of the SharedPreferences
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);

                //Step 1b: Obtain an instance of the SharedPreference Editor for update later
                SharedPreferences.Editor prefEdit = prefs.edit();

                //Step 1c: Add the key-value pair
                prefEdit.putFloat("weight", weight);
                prefEdit.putFloat("height", height);
                prefEdit.putString("date", datetime);
                prefEdit.putFloat("bmi", bmi);

                //Step 1d: Call commit() method to save the changes into the Shared Preferences
                prefEdit.commit();

                tvDate.setText(getString(R.string.date) + datetime);
                tvBMI.setText(getString(R.string.lastCalc) + bmi);

                if (bmi >= 18.5 && bmi <= 24.9){
                    tvDesc.setText("Your BMI is normal");
                    tvDesc.setText("You are underweight");
                } else if (bmi >= 25.0 && bmi <= 29.9){
                    tvDesc.setText("You are overweight");
                } else if (bmi >= 30.0){
                    tvDesc.setText("You are obese");
                } else {
                    tvDesc.setText("You are underweight");
                }
                etWeight.setText(null);
                etHeight.setText(null);
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etWeight.setText(null);
                etHeight.setText(null);
                tvDesc.setText(null);
                tvDate.setText(getString(R.string.date));
                tvBMI.setText(getString(R.string.lastCalc));
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();

        //Step 1a: Obtain an instance of the SharedPreferences
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);

        //Step 1b: Obtain an instance of the SharedPreference Editor for update later
        SharedPreferences.Editor prefEdit = prefs.edit();

        //Step 1c: Add the key-value pair
        prefEdit.putFloat("weight", weight);
        prefEdit.putFloat("height", height);
        prefEdit.putString("date", datetime);
        prefEdit.putFloat("bmi", bmi);

        //Step 1d: Call commit() method to save the changes into the Shared Preferences
        prefEdit.commit();
    }

    @Override
    protected void onResume() {
        super.onResume();

        //Step 2a: Obtain an instance of the SharedPreferences
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);

        //Step 2b: Retrieve the saved data from the SharedPreferences object
        float weight = prefs.getFloat("weight", 0);
        float height = prefs.getFloat("height", 0);
        String data = prefs.getString("date", "");
        float bmi = prefs.getFloat("bmi", 0);

        tvDate.setText(getString(R.string.date) + datetime);
        tvBMI.setText(getString(R.string.lastCalc) + bmi);

        if (bmi >= 18.5 && bmi <= 24.9){
            tvDesc.setText("Your BMI is normal");
        } else if (bmi >= 25.0 && bmi <= 29.9){
            tvDesc.setText("You are overweight");
        } else if (bmi >= 30.0){
            tvDesc.setText("You are obese");
        } else {
            tvDesc.setText("You are underweight");
        }
    }
}
