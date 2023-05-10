package com.example.lab1a;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    public static final String EXTRA_GRADES = "com.example.lab1a.EXTRA_GRADES";

    //stan aktywnosci przy zmianie orientacji urzadzenia
    private static final String KEY_NAME = "name";
    private static final String KEY_SURNAME = "surname";
    private static final String KEY_GRADES = "grades";

    private EditText mEditName;
    private EditText mEditSurname;
    private EditText mEditGrades;
    private Button mButtonGrades;
    private TextView mAverage;
    private TextView mLabelAverage;
    private TextView mKomunikatKoniec;
    private Handler mHandler = new Handler();



    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mEditName = findViewById(R.id.edit_name);
        mEditSurname = findViewById(R.id.edit_surname);
        mEditGrades = findViewById(R.id.edit_grades);
        mButtonGrades = findViewById(R.id.button_grades);
        mAverage = findViewById(R.id.average);
        mLabelAverage = findViewById(R.id.label_average);
        mKomunikatKoniec = findViewById(R.id.komunikatKoniec);



        if (savedInstanceState != null) {
            String name = savedInstanceState.getString("name");
            String surname = savedInstanceState.getString("surname");
            String grades = savedInstanceState.getString("grades");
            mEditName.setText(name);
            mEditSurname.setText(surname);
            mEditGrades.setText(grades);
            validate_name(true);
            validate_surname(true);
            validate_grades(true);
        }





        ///////////////sprawdzenie poprawnosci wprowadzonych danych po kazdej
        ///////////////zmianie zawartosci pola

        mEditName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                checkValidation();
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        mEditSurname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                checkValidation();
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        mEditGrades.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                checkValidation();
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        /////////////////komunikaty o błędach
        /////////////////

        mEditName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus)
            {
                if(!hasFocus)
                {
                    validate_name(true);
                    checkValidation();
                }
            }
        });

        mEditSurname.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b)
            {
                if(!b)
                {
                    validate_surname(true);
                    checkValidation();
                }
            }
        });

        mEditGrades.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b)
            {
                if(!b)
                {
                    validate_grades(true);
                    checkValidation();
                }
            }
        });

        /////////////otworzenie nowej aktywnosci po
        /////////////nacisnieciu przycisku

        mButtonGrades.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openOceny();
            }
        });


        if (savedInstanceState != null) {
            mEditName.setText(savedInstanceState.getString(KEY_NAME));
            mEditSurname.setText(savedInstanceState.getString(KEY_SURNAME));
            mEditGrades.setText(savedInstanceState.getString(KEY_GRADES));
            checkValidation();
        }

    }

    private boolean validate_name(boolean czyWyswietlac)
    {
        String name = mEditName.getText().toString().trim();

        if(TextUtils.isEmpty(name))
        {
            if(czyWyswietlac)
            {
                mEditName.setError(getString(R.string.name_error));
            }
            mButtonGrades.setVisibility(View.INVISIBLE);
            return false;
        }
        else
        {
            mEditName.setError(null);
            return true;
        }
    }

    private boolean validate_surname(boolean czyWyswietlac)
    {
        String surname = mEditSurname.getText().toString().trim();

        if(TextUtils.isEmpty(surname))
        {
            if(czyWyswietlac) {
                mEditSurname.setError(getString(R.string.surname_error));
            }
            mButtonGrades.setVisibility(View.INVISIBLE);
            return false;
        }
        else
        {
            mEditSurname.setError(null);
            return true;
        }
    }

    private boolean validate_grades(boolean czyWyswietlacTekst)
    {
        String grades = mEditGrades.getText().toString().trim();

        if(TextUtils.isEmpty(grades))
        {
            if(czyWyswietlacTekst) {
                mEditGrades.setError(getString(R.string.grades_error));
            }
            mButtonGrades.setVisibility(View.INVISIBLE);
            return false;
        }
        else
        {
            try {

                int gradesInt = Integer.parseInt(grades);

                if (gradesInt < 5 || gradesInt > 15) {
                    if(czyWyswietlacTekst) {
                        mEditGrades.setError(getString(R.string.grades_hint_out_of_range));
                    }
                    mButtonGrades.setVisibility(View.INVISIBLE);
                    return false;
                } else {
                    mButtonGrades.setError(null);

                    return true;
                }
            }
            catch(NumberFormatException e)
            {
                if(czyWyswietlacTekst)
                {
                    mEditGrades.setError(getString(R.string.grades_hint_number_format_exception));
                }
                mButtonGrades.setVisibility(View.INVISIBLE);
                return false;
            }
        }

    }

    private void checkValidation()
    {
        if (validate_grades(false)==true && validate_name(false)==true  && validate_surname(false)==true) {
            mButtonGrades.setVisibility(View.VISIBLE);
        } else {
            mButtonGrades.setVisibility(View.INVISIBLE);
        }
    }

    public void openOceny()
    {

        EditText get_grades = (EditText) findViewById(R.id.edit_grades);
        int grades1 = Integer.parseInt(get_grades.getText().toString());

        Intent intent = new Intent(this, Oceny.class);
        intent.putExtra(EXTRA_GRADES, grades1);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("name", mEditName.getText().toString());
        outState.putString("surname", mEditSurname.getText().toString());
        outState.putString("grades", mEditGrades.getText().toString());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        String name = savedInstanceState.getString("name");
        String surname = savedInstanceState.getString("surname");
        String grades = savedInstanceState.getString("grades");
        mEditName.setText(name);
        mEditSurname.setText(surname);
        mEditGrades.setText(grades);
        validate_name(true);
        validate_surname(true);
        validate_grades(true);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1)
        {
            if(resultCode == RESULT_OK)
            {
                double srednia = data.getDoubleExtra("averageGrade", 2);
                mAverage.setVisibility(View.VISIBLE);
                mLabelAverage.setVisibility(View.VISIBLE);

                int decimalPlaces = 2; // ilość miejsc po przecinku
                String text = String.format("%." + decimalPlaces + "f", srednia);
                mAverage.setText(text);


                if(srednia >= 3.0)
                {
                    mButtonGrades.setText("SUPER!");
                    mButtonGrades.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            mKomunikatKoniec.setVisibility(View.VISIBLE);
                            mKomunikatKoniec.setText(getString(R.string.komunikat_koniec_pozytywny));

                            mHandler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    finish();
                                }
                            }, 3000); // opóźnienie w milisekundach (tutaj 5 sekund)
                        }
                    });

                }
                else
                {
                    mButtonGrades.setText("Tym razem nie wyszło :(");
                    mButtonGrades.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            mKomunikatKoniec.setVisibility(View.VISIBLE);
                            mKomunikatKoniec.setText(getString(R.string.komunikat_koniec_negatywny));

                            mHandler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    finish();
                                }
                            }, 3000); // opóźnienie w milisekundach (tutaj 5 sekund)
                        }
                    });
                }
            }
        }
    }
}
