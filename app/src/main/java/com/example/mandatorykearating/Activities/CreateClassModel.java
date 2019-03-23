package com.example.mandatorykearating.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.example.mandatorykearating.R;
import com.example.mandatorykearating.models.ClassModel;
import com.example.mandatorykearating.models.MyParceArraylist;

public class CreateClassModel extends AppCompatActivity
{
    private static final String TAG = "MyTest";
    //to next activity
    public static final String EXTRA_return_create = "Return create";
    private MyParceArraylist myParceArraylist = new MyParceArraylist();
    private ClassModel newClass;

    //intent
    private Intent intent;

    //editText
    private EditText editName;
    private EditText editTeacher;

    //rating
    private RatingBar ratingBarRelevans;
    private RatingBar ratingBarPerformance;
    private RatingBar ratingBarPreparation;
    private RatingBar ratingBarFeedback;
    private RatingBar ratingBarEx;
    private RatingBar ratingBarOpportunities;

    //toast
    private int toastDuration;
    private CharSequence toastText;
    private Toast toast;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_class_model);

        myInit();
        // Get the Intent that started this activity and extract the string
        intent = getIntent();
        if (intent != null)
        {
            if (intent.getParcelableArrayListExtra(MainActivity.EXTRA_Mes_create) != null)
            {
                myParceArraylist.setArrayListOfClass(intent.getParcelableArrayListExtra(MainActivity.EXTRA_Mes_create));
                Log.d(TAG, "onCreate: ");
            }
            if (intent.getParcelableArrayListExtra(viewListAct.EXTRA_create) != null)
            {
                myParceArraylist.setArrayListOfClass(intent.getParcelableArrayListExtra(viewListAct.EXTRA_create));
            }

        }
    }

    private void myInit()
    {
        //editText
        editName = (EditText) findViewById(R.id.inNameC);
        editTeacher = (EditText) findViewById(R.id.inTeacherC);

        //toast
        toastDuration = Toast.LENGTH_LONG;
        toast = Toast.makeText(this,toastText,toastDuration);

        //ratings
        ratingBarRelevans = (RatingBar) findViewById(R.id.rateRel);
        addListenerOnRatingBar(ratingBarRelevans);

        ratingBarPerformance = (RatingBar) findViewById(R.id.ratePer);
        addListenerOnRatingBar(ratingBarPerformance);

        ratingBarPreparation = (RatingBar) findViewById(R.id.ratePre);
        addListenerOnRatingBar(ratingBarPreparation);

        ratingBarFeedback = (RatingBar) findViewById(R.id.rateFee);
        addListenerOnRatingBar(ratingBarFeedback);

        ratingBarEx = (RatingBar) findViewById(R.id.rateExa);
        addListenerOnRatingBar(ratingBarEx);

        ratingBarOpportunities = (RatingBar) findViewById(R.id.rateOp);
        addListenerOnRatingBar(ratingBarOpportunities);
    }

    private void addListenerOnRatingBar(RatingBar ratingBar)
    {
        //if rating change current rating value in textView (automatically)
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener()
        {
            public void onRatingChanged(RatingBar ratingBar, float rating,
                                        boolean fromUser)
            {

            }
        });
    }

    public void createBut(View view)
    {
        newClass = new ClassModel(editName.getText().toString() ,editTeacher.getText().toString(),
                                  ratingBarRelevans.getRating(),ratingBarPerformance.getRating(),
                                  ratingBarPreparation.getRating(),ratingBarFeedback.getRating(),
                                  ratingBarEx.getRating(), ratingBarOpportunities.getRating());
        
        myParceArraylist.getArrayListOfClass().add(newClass);

        toast.setText("class/coures is created: " + newClass.getName());
        toast.show();

        intent = new Intent(this, MainActivity.class);
        intent.putExtra(EXTRA_return_create, myParceArraylist.getArrayListOfClass());
        startActivity(intent);
        Log.d(TAG, "createBut: ");
    }
}
