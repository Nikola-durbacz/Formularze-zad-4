package com.example.formularzezad4;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Button;
import android.view.View;
import android.widget.Toast;
import java.util.Locale;


public class MainActivity extends AppCompatActivity {

    private EditText etWeight;
    private EditText etHeight;
    private RadioGroup rgGender;
    private TextView tvResult;
    private TextView tvGenderError;
    private Button btnCalculate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        etWeight = findViewById(R.id.etWeight);
        etHeight = findViewById(R.id.etHeight);
        rgGender = findViewById(R.id.rgGender);
        tvResult = findViewById(R.id.tvResult);
        tvGenderError = findViewById(R.id.tvGenderError);
        btnCalculate = findViewById(R.id.btnCalculate);

        btnCalculate.setOnClickListener(v -> calculateBMI());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    private void calculateBMI() {
        String weightText = etWeight.getText().toString().trim();
        String heightText = etHeight.getText().toString().trim();

        tvResult.setText("");
        tvGenderError.setVisibility(View.GONE);

        if (weightText.isEmpty()) {
            etWeight.setError("Wpisz wagę");
            etWeight.requestFocus();
            return;
        }

        if (heightText.isEmpty()) {
            etHeight.setError("Wpisz wzrost");
            etHeight.requestFocus();
            return;
        }

        double weight = Double.parseDouble(weightText.replace(',', '.'));
        int height = Integer.parseInt(heightText);

        if (weight <= 0) {
            etWeight.setError("Waga musi być większa od 0");
            etWeight.requestFocus();
            return;
        }

        if (height <= 0) {
            etHeight.setError("Wzrost musi być większy od 0");
            etHeight.requestFocus();
            return;
        }

        if (height > 300) {
            etHeight.setError("Wzrost nie może przekraczać 300 cm");
            etHeight.requestFocus();
            return;
        }

        if (rgGender.getCheckedRadioButtonId() == -1) {
            tvGenderError.setVisibility(View.VISIBLE);
            rgGender.requestFocus();
            return;
        }

        double heightInMeters = height / 100.0;
        double bmi = weight / (heightInMeters * heightInMeters);

        String category;

        if (bmi < 18.5) {
            category = "Niedowaga";
        } else if (bmi < 25) {
            category = "Masa prawidłowa";
        } else if (bmi < 30) {
            category = "Nadwaga";
        } else {
            category = "Otyłość";
        }

        String result = String.format(Locale.getDefault(), "%.2f", bmi);

        tvResult.setText("BMI: " + result + "\nKategoria: " + category);
    }
}