package com.example.lab1a;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.view.MenuItem;


import org.w3c.dom.Text;

import java.lang.reflect.Array;

public class Oceny extends AppCompatActivity {

    private Button btnn_srednia;
    private int[] gradesArray;
    private double averageGrade;

    //zeby zachowac wprowadzone zmiany po obrocie ekranu
    private static final String KEY_GRADES = "GRADES";
    private static final String KEY_AVERAGE_GRADE = "AVERAGE_GRADE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oceny);

        //strzalka powrotu
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back);

        btnn_srednia = findViewById(R.id.button_srednia);

        Intent intent = getIntent();
        int grades1 = intent.getIntExtra(MainActivity.EXTRA_GRADES, 5);

        TableLayout tableLayout = findViewById(R.id.oceny_table);
        //ScrollView scrollView = findViewById(R.id.scroll_view);

        //zeby zachowac wprowadzone zmiany po obrocie ekranu

        if (savedInstanceState != null) {
            // Przywrócenie wartości po ponownym utworzeniu aktywności
            gradesArray = savedInstanceState.getIntArray(KEY_GRADES);
            averageGrade = savedInstanceState.getDouble(KEY_AVERAGE_GRADE);
        } else {
            // Inicjalizacja wartości
            gradesArray = new int[grades1];
            averageGrade = 0;
        }

        for (int i = 0; i < grades1; i++) {
            TableRow tableRow = new TableRow(this);
            tableRow.setOrientation(LinearLayout.HORIZONTAL);

            TextView textView1 = new TextView(this);
            textView1.setLayoutParams(new TableRow.LayoutParams(0));
            String[] subjectsArray = getResources().getStringArray(R.array.subjects_array);
            textView1.setText(subjectsArray[i]);
            textView1.setTextSize(16);
            textView1.setGravity(Gravity.CENTER_HORIZONTAL);
            tableRow.addView(textView1);

            RadioGroup radioGroup = new RadioGroup(this);
            radioGroup.setLayoutParams(new TableRow.LayoutParams(1));
            radioGroup.setOrientation(LinearLayout.HORIZONTAL);

            for (int j = 2; j <= 5; j++) {
                RadioButton radioButton = new RadioButton(this);
                radioButton.setText(Integer.toString(j));
                radioButton.setTextSize(16);
                radioGroup.addView(radioButton);
                if(j==2)
                {
                    radioButton.setChecked(true);
                 }
            }
            tableRow.addView(radioGroup);
            tableLayout.addView(tableRow);
        }



        btnn_srednia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // tworzenie tablicy na oceny
                gradesArray = new int[grades1];
                double suma = 0;
                for (int i = 0; i < grades1; i++) {
                    TableRow tableRow = (TableRow) tableLayout.getChildAt(i);
                    RadioGroup radioGroup = (RadioGroup) tableRow.getChildAt(1);

                    int checkedId = radioGroup.getCheckedRadioButtonId();
                    RadioButton radioButton = (RadioButton) radioGroup.findViewById(checkedId);
                    String selectedValue = radioButton.getText().toString();

                    // zapisywanie zaznaczonej wartości do tablicy
                    gradesArray[i] = Integer.parseInt(selectedValue);
                }
                double sum = 0;
                for (int i = 0; i < gradesArray.length; i++) {
                    sum += gradesArray[i];
                }
                averageGrade = sum / gradesArray.length;
                openMain(averageGrade);
            }
        });

    }

    public void openMain(double averageGrade) {
        Intent intent = new Intent();
        intent.putExtra("averageGrade", averageGrade);

        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        TableLayout tableLayout = findViewById(R.id.oceny_table);
        int rowCount = tableLayout.getChildCount();

        for (int i = 0; i < rowCount; i++) {
            TableRow tableRow = (TableRow) tableLayout.getChildAt(i);
            RadioGroup radioGroup = (RadioGroup) tableRow.getChildAt(1);
            int checkedButtonId = radioGroup.getCheckedRadioButtonId();

            if (checkedButtonId != -1) {
                RadioButton checkedButton = findViewById(checkedButtonId);
                String checkedValue = checkedButton.getText().toString();
                outState.putString("radio_button_" + i, checkedValue);
            }
        }
    }


    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        TableLayout tableLayout = findViewById(R.id.oceny_table);
        int rowCount = tableLayout.getChildCount();

        for (int i = 0; i < rowCount; i++) {
            TableRow tableRow = (TableRow) tableLayout.getChildAt(i);
            RadioGroup radioGroup = (RadioGroup) tableRow.getChildAt(1);

            if (savedInstanceState.containsKey("radio_button_" + i)) {
                String checkedValue = savedInstanceState.getString("radio_button_" + i);

                int childCount = radioGroup.getChildCount();
                for (int j = 0; j < childCount; j++) {
                    RadioButton radioButton = (RadioButton) radioGroup.getChildAt(j);
                    if (radioButton.getText().toString().equals(checkedValue)) {
                        radioButton.setChecked(true);
                        break;
                    }
                }
            }
        }
    }



}