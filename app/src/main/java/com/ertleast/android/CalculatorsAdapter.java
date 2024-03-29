package com.ertleast.android;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.InputFilter;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Date;


public class CalculatorsAdapter extends RecyclerView.Adapter<CalculatorsAdapter.ExampleViewHolder> {
    private ArrayList<CalculatorsItem> mExampleList;
    private Context mContext;
    private String m_Text = "";
    public CalculatorsAdapter(Context context, ArrayList<CalculatorsItem> exampleList) {
        mExampleList = exampleList;
        mContext = context;
    }

    @Override
    public ExampleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.example_item, parent, false);
        ExampleViewHolder evh = new ExampleViewHolder(v);
        return evh;
    }

    @Override
    public void onBindViewHolder(final ExampleViewHolder holder, final int position) {
        CalculatorsItem currentItem = mExampleList.get(position);

        holder.mImageView.setImageResource(currentItem.getImageResource());
        holder.mTextView1.setText(currentItem.getText1());
        holder.mTextView2.setText(currentItem.getText2());

        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String calculator_uri = null;
                //Intent intent = new Intent(mContext, WebpageDetailsActivity.class);
                //Intent intent = new Intent(mContext, StockPhotoActivity.class);
                /*
                switch (position) {
                    case 0:
                        calculator_uri = "AbbreviatedMentalTestScore.html";
                        break;
                    case 1:
                        calculator_uri = "ABCD.html";
                        break;
                    case 2:
                        calculator_uri = "AllowableBloodLoss.html";
                        break;
                    case 3:
                        calculator_uri = "Alvarado.html";
                        break;
                    case 4:
                        calculator_uri = "BicarbDeficit.html";
                        break;
                    case 5:
                        calculator_uri = "BishopScoreCalculator.html";
                        break;
                    case 6:
                        calculator_uri = "BMI.html";
                        break;
                    case 7:
                        calculator_uri = "BodySurfaceArea.html";
                        break;
                    case 8:
                        calculator_uri = "BodySurfaceAreaWithoutHeight.html";
                        break;
                    case 9:
                        calculator_uri = "BohrDeadSpace.html";
                        break;
                    case 10:
                        calculator_uri = "ChronotropicIndex.html";
                        break;
                    case 11:
                        calculator_uri = "CorrectedSerumCa.html";
                        break;
                    case 12:
                        calculator_uri = "CorrectedSerumSodium.html";
                        break;
                    case 13:
                        calculator_uri = "CreatinineClearanceCockcroftGault.html";
                        break;
                    case 14:
                        calculator_uri = "CreatinineClearanceCockcroftGaultByIdealBodyWeight.html";
                        break;
                    case 15:
                        calculator_uri = "CreatinineClearanceSchwartz.html";
                        break;
                    case 16:
                        calculator_uri = "EdinburgPostNatalDepressionScale.html";
                        break;
                    case 17:
                        calculator_uri = "Glasgow.html";
                        break;
                    case 18:
                        calculator_uri = "HeartRateFromEKG.html";
                        break;
                    case 19:
                        calculator_uri = "HendersonHasselbach.html";
                        break;
                    case 20:
                        calculator_uri = "IdealBodyWeight.html";
                        break;
                    case 21:
                        calculator_uri = "IronDeficit.html";
                        break;
                    case 22:
                        calculator_uri = "LDLCholesterol.html";
                        break;
                    case 23:
                        calculator_uri = "LeanBodyWeight.html";
                        break;
                    case 24:
                        calculator_uri = "ModifiedAlvarado.html";
                        break;
                    case 25:
                        calculator_uri = "NottinghamEczemaSeverityScore.html";
                        break;
                    case 26:
                        calculator_uri = "NumberNeededToHarm.html";
                        break;
                    case 27:
                        calculator_uri = "NumberNeededToTreat.html";
                        break;
                    case 28:
                        calculator_uri = "SodiumDeficit.html";
                        break;
                    case 29:
                        calculator_uri = "TargetHeartRate.html";
                        break;
                    case 30:
                        calculator_uri = "UrineOutput.html";
                        break;
                    case 31:
                        calculator_uri = "WaterDeficit.html";
                        break;
                }
                */
                Context context = holder.itemView.getContext();
                //String m_Text = "";
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Update Location Info");

                // Set up the input
                final EditText input = new EditText(context);
                // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
                input.setInputType(InputType.TYPE_CLASS_TEXT  | InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS);
                input.setFilters(new InputFilter[] {new InputFilter.LengthFilter(30)});
                builder.setView(input);

                // Set up the buttons
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        m_Text = input.getText().toString();
                        DBHandler dbHandler = new DBHandler(holder.itemView.getContext());
                        Date date = new Date();
                        String scanned_time = String.valueOf(date.getTime() / 1000L);
                        dbHandler.updateCourse( mExampleList.get(position).getText1(),
                                m_Text, scanned_time, "NULL");
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();

                //intent.putExtra("calculator_uri", "file:///android_asset/calculators/" + calculator_uri);
                //intent.putExtra("position", mExampleList.get(position).getText1());
                //Log.i("aniruddha",mExampleList.get(position).getText1());
                //mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mExampleList.size();
    }

    public static class ExampleViewHolder extends RecyclerView.ViewHolder {
        public ImageView mImageView;
        public TextView mTextView1;
        public TextView mTextView2;
        CardView parentLayout;

        public ExampleViewHolder(View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.imageView);
            mTextView1 = itemView.findViewById(R.id.textView);
            mTextView2 = itemView.findViewById(R.id.textView2);
            parentLayout = itemView.findViewById(R.id.parent_layout);
        }
    }
}