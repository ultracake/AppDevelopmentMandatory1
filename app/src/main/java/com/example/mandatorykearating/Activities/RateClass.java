package com.example.mandatorykearating.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.mandatorykearating.models.ClassModel;
import com.example.mandatorykearating.R;
import com.example.mandatorykearating.models.MyParceArraylist;

public class RateClass extends AppCompatActivity
{
    private static final String TAG = "MyTest";

    //parce
    public static final String EXTRA_saveChange = "change model";

    private double forFunText;

    //activity
    private Intent intent;

    //store obj
    private MyParceArraylist myParceArraylist = new MyParceArraylist();
    private int modelID;
    private ClassModel currentModel;
    private String saveStringResult = "";

    //textview
    private TextView textViewClassName;
    private TextView textViewTeacherName;
    private TextView txtRatingAllOverview;

    //rating
    private RatingBar ratingBarRelevans;
    private RatingBar ratingBarPerformance;
    private RatingBar ratingBarPreparation;
    private RatingBar ratingBarFeedback;
    private RatingBar ratingBarEx;
    private RatingBar ratingBarOpportunities;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate_class);

        // Get the Intent that started this activity and extract the string
        intent = getIntent();
        if (intent != null)
        {
            if ( intent.getParcelableArrayListExtra(viewListAct.EXTRA_Mes_model) != null)
            {
                //Log.d(TAG, "onCreate: test");
                myParceArraylist.setArrayListOfClass(intent.getParcelableArrayListExtra(viewListAct.EXTRA_Mes_model));
                modelID = Integer.parseInt(intent.getStringExtra(viewListAct.EXTRA_ID));
                currentModel = myParceArraylist.getArrayListOfClass().get(modelID);
            }
        }

        myInit();

        //input the save variable
        if (savedInstanceState != null)
        {
            String getOldString = savedInstanceState.getString(saveStringResult);
            txtRatingAllOverview.setText(getOldString);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState)
    {
        super.onSaveInstanceState(savedInstanceState);

        Log.d(TAG, "onSaveInstanceState: my test " + txtRatingAllOverview.getText().toString());
        savedInstanceState.putString(saveStringResult, txtRatingAllOverview.getText().toString());
    }


    private void myInit()
    {
        //textViews
        textViewClassName = (TextView) findViewById(R.id.classnameID);
        textViewClassName.setText(currentModel.getName());
        textViewTeacherName = (TextView) findViewById(R.id.teacherNNameID);
        textViewTeacherName.setText(currentModel.getTeacher());

        txtRatingAllOverview = (TextView) findViewById(R.id.funText);

        //ratings
        ratingBarRelevans = (RatingBar) findViewById(R.id.rateRelevans);
        ratingBarRelevans.setRating((float) currentModel.getSubjectRelevans());
        addListenerOnRatingBar(ratingBarRelevans,0);

        ratingBarPerformance = (RatingBar) findViewById(R.id.ratePerfomance);
        ratingBarPerformance.setRating((float) currentModel.getTeacherOverallPerformance());
        addListenerOnRatingBar(ratingBarPerformance, 1);

        ratingBarPreparation = (RatingBar) findViewById(R.id.ratePreparation);
        ratingBarPreparation.setRating((float) currentModel.getTeacherPreparation());
        addListenerOnRatingBar(ratingBarPreparation,2);

        ratingBarFeedback = (RatingBar) findViewById(R.id.rateFeedback);
        ratingBarFeedback.setRating((float) currentModel.getAmountOfFeedback());
        addListenerOnRatingBar(ratingBarFeedback,3);

        ratingBarEx = (RatingBar) findViewById(R.id.rateEx);
        ratingBarEx.setRating((float) currentModel.getHowGoodTheirEx());
        addListenerOnRatingBar(ratingBarEx,4);

        ratingBarOpportunities = (RatingBar) findViewById(R.id.rateOpportunities);
        ratingBarOpportunities.setRating((float) currentModel.getJobOpportunities());
        addListenerOnRatingBar(ratingBarOpportunities,5);
    }

    private void addListenerOnRatingBar(RatingBar ratingBar, final int ratebarID)
    {
        //if rating change current rating value in textView (automatically)
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener()
        {
            public void onRatingChanged(RatingBar ratingBar, float rating,
                                        boolean fromUser)
            {
                if (ratebarID == 0)
                {
                    currentModel.setSubjectRelevans(rating);
                }
                if (ratebarID == 1)
                {
                    currentModel.setTeacherOverallPerformance(rating);
                }
                if (ratebarID == 2)
                {
                    currentModel.setTeacherPreparation(rating);
                }
                if (ratebarID == 3)
                {
                    currentModel.setAmountOfFeedback(rating);
                }
                if (ratebarID == 4)
                {
                    currentModel.setHowGoodTheirEx(rating);
                }
                if (ratebarID == 5)
                {
                    currentModel.setJobOpportunities(rating);
                }
                opdateFunText();
            }
        });
    }

    private void opdateFunText()
    {
        forFunText = currentModel.getSubjectRelevans() +currentModel.getTeacherOverallPerformance()+currentModel.getTeacherPreparation()+
                     currentModel.getAmountOfFeedback() + currentModel.getHowGoodTheirEx()+currentModel.getJobOpportunities();
        if (forFunText >= 25)
        {
            txtRatingAllOverview.setText(String.valueOf("it´s lit fam!\n"+ "score:" + forFunText));
        }
        else if (forFunText >= 15)
        {
            txtRatingAllOverview.setText(String.valueOf("it´s awesome!\n"+"score:" + forFunText));
        }
        else if (forFunText >= 10)
        {
            txtRatingAllOverview.setText(String.valueOf("it´s ok!\n "+ "score:" +forFunText));
        }
        else if (forFunText >= 5)
        {
            txtRatingAllOverview.setText(String.valueOf("needs more cake!\n"+ "score:" + forFunText));
        }
        else
        {
            txtRatingAllOverview.setText("even cakes can´t save this!\n"+ "score:" + forFunText);
        }
    }

    public void saveChange(View view)
    {
        intent = new Intent(this, viewListAct.class);
        setNewToOld(currentModel, modelID);

        intent.putExtra(EXTRA_saveChange, myParceArraylist.getArrayListOfClass());

        startActivity(intent);
        Log.d(TAG, "saveChange: testNew");
    }

    private void setNewToOld(ClassModel model, int ID)
    {
        myParceArraylist.getArrayListOfClass().get(ID).setName(model.getName());
        myParceArraylist.getArrayListOfClass().get(ID).setTeacher(model.getTeacher());
        myParceArraylist.getArrayListOfClass().get(ID).setSubjectRelevans(model.getSubjectRelevans());
        myParceArraylist.getArrayListOfClass().get(ID).setTeacherOverallPerformance(model.getTeacherOverallPerformance());
        myParceArraylist.getArrayListOfClass().get(ID).setTeacherPreparation(model.getTeacherPreparation());
        myParceArraylist.getArrayListOfClass().get(ID).setAmountOfFeedback(model.getAmountOfFeedback());
        myParceArraylist.getArrayListOfClass().get(ID).setHowGoodTheirEx(model.getHowGoodTheirEx());
        myParceArraylist.getArrayListOfClass().get(ID).setJobOpportunities(model.getJobOpportunities());
    }
}
