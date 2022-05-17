package com.ertleast.android;

import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class CalculatorsHeaderActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleHelper.onAttach(base, "en"));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_calculatorsheader);
        setTitle(R.string.calculators);

        ArrayList<CalculatorsItem> exampleList = new ArrayList<>();
        exampleList.add(new CalculatorsItem(R.drawable.ic_android, "Abbreviated Mental Test Score", "possibility of dementia"));
        exampleList.add(new CalculatorsItem(R.drawable.ic_android, "ABCD Risk Score", "determine risk for stroke"));
        exampleList.add(new CalculatorsItem(R.drawable.ic_android, "Allowable Blood Loss", "maximum allowable blood loss"));
        exampleList.add(new CalculatorsItem(R.drawable.ic_android, "Alvarado Appendicitis Score", "diagnosis of appendicitis"));
        exampleList.add(new CalculatorsItem(R.drawable.ic_android, "Bicarbonate Deficit", "correct a metabolic acidosis"));
        exampleList.add(new CalculatorsItem(R.drawable.ic_android, "Bishop Score", "readiness of the cervix"));
        exampleList.add(new CalculatorsItem(R.drawable.ic_android, "Body Mass Index (BMI)", "measure of body fat"));
        exampleList.add(new CalculatorsItem(R.drawable.ic_android, "Body Surface Area", "indicator of metabolic mass"));
        exampleList.add(new CalculatorsItem(R.drawable.ic_android, "Body Surface Area Without Height", "indicator of metabolic mass"));
        exampleList.add(new CalculatorsItem(R.drawable.ic_android, "Bohr Dead Space", "space in a person's lungs"));
        exampleList.add(new CalculatorsItem(R.drawable.ic_android, "Chronotropic Index", "Heart rate reserve"));
        exampleList.add(new CalculatorsItem(R.drawable.ic_android, "Corrected Serum Calcium", "Serum Calcium concentration"));
        exampleList.add(new CalculatorsItem(R.drawable.ic_android, "Corrected Serum Sodium", "Serum sodium levels"));
        exampleList.add(new CalculatorsItem(R.drawable.ic_android, "Creatinine Clearance Cockcroft Gault", "predicts Creatinine Clearance"));
        exampleList.add(new CalculatorsItem(R.drawable.ic_android, "Creatinine Clearance Cockcroft Gault By Ideal Body Weight", "predicts Creatinine Clearance"));
        exampleList.add(new CalculatorsItem(R.drawable.ic_android, "Creatinine Clearance Schwartz", "glomerular filtration rate"));
        exampleList.add(new CalculatorsItem(R.drawable.ic_android, "Edinburg Post Natal Depression Scale", "detecting major depression"));
        exampleList.add(new CalculatorsItem(R.drawable.ic_android, "Glasgow Coma Scale", "level of consciousness in a person"));
        exampleList.add(new CalculatorsItem(R.drawable.ic_android, "Heart Rate From ECG", "Heart rate calculation"));
        exampleList.add(new CalculatorsItem(R.drawable.ic_android, "Henderson–Hasselbalch", "estimate pH of buffer solution"));
        exampleList.add(new CalculatorsItem(R.drawable.ic_android, "Ideal Body Weight", "calculates ideal body weight"));
        exampleList.add(new CalculatorsItem(R.drawable.ic_android, "Iron Deficit", "Equation for Iron Deficiency Anemia"));
        exampleList.add(new CalculatorsItem(R.drawable.ic_android, "LDL Cholesterol", "low-density lipoproteins"));
        exampleList.add(new CalculatorsItem(R.drawable.ic_android, "Lean Body Weight", "Weight without body fat"));
        exampleList.add(new CalculatorsItem(R.drawable.ic_android, "Modified Alvarado", "tool for acute appendicitis"));
        exampleList.add(new CalculatorsItem(R.drawable.ic_android, "Nottingham Eczema Severity Score", "severity of atopic eczema"));
        exampleList.add(new CalculatorsItem(R.drawable.ic_android, "Number Needed To Harm", "show the harmful effect"));
        exampleList.add(new CalculatorsItem(R.drawable.ic_android, "Number Needed To Treat", "prevent one additional bad outcome"));
        exampleList.add(new CalculatorsItem(R.drawable.ic_android, "Sodium Deficit", "Sodium quantity missing in hyponatremia"));
        exampleList.add(new CalculatorsItem(R.drawable.ic_android, "Target Heart Rate", "maximum heart rate for exercise"));
        exampleList.add(new CalculatorsItem(R.drawable.ic_android, "Urine Output", "normal range of urine output"));
        exampleList.add(new CalculatorsItem(R.drawable.ic_android, "Water Deficit", "correct dehydration during fluid-replacement"));

        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new CalculatorsAdapter(this, exampleList);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }
}