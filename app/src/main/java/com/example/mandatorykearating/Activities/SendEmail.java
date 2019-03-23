package com.example.mandatorykearating.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.mandatorykearating.R;
import com.example.mandatorykearating.models.ClassModel;
import com.example.mandatorykearating.models.MyParceArraylist;

import java.util.ArrayList;

public class SendEmail extends AppCompatActivity
{
    //to next activity
    private static final String TAG = "MyTest";
    //public static final String EXTRA_returnHome = "back";

    private MyParceArraylist myParceArraylist = new MyParceArraylist();
    private ArrayList<String> nameOfClassList = new ArrayList<>();
    private ClassModel currentClassModel;
    private int id;

    //intent
    private Intent intent;

    //editText
    private EditText editTextTo;
    private EditText editTextSubject;
    private EditText editTextMessage;

    //button
    private Button butSend;

    //spinner
    private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_email);

        myInit();

        butSend.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                sendMail();
            }
        });

        intent = getIntent();
        if (intent != null)
        {
            if (intent.getParcelableArrayListExtra(MainActivity.EXTRA_SendEmail) != null)
            {
                myParceArraylist.setArrayListOfClass(intent.getParcelableArrayListExtra(MainActivity.EXTRA_SendEmail));
                Log.d(TAG, "onCreate: ");
            }
        }

        //setup for spinner
        addNameToNameList(myParceArraylist.getArrayListOfClass());
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1 ,nameOfClassList);
        spinner.setAdapter(arrayAdapter);
    }

    private void sendMail()
    {
        id = spinner.getSelectedItemPosition();
        currentClassModel = myParceArraylist.getArrayListOfClass().get(id);

        //to seperate multi emails: example@mail.com, example2@mail.com
        String recipients = editTextTo.getText().toString();
        String[] recipientsList = recipients.split(",");

        String subject = editTextSubject.getText().toString();
        String message = editTextMessage.getText().toString();

        String MyFormatMessage = message + "\n \n"
                + "Your selected Class"+ "\n"
                + "Class: " + currentClassModel.getName()+ "\n"
                + "Teacher: " + currentClassModel.getTeacher()+ "\n \n"
                + "Ratings: " + "\n"
                + "Subject Relevans: " + currentClassModel.getSubjectRelevans()+ "\n"
                + "Teacher Overall Performance: " + currentClassModel.getTeacherOverallPerformance()+ "\n"
                + "Teacher Preparation: " + currentClassModel.getTeacherPreparation()+ "\n"
                + "Amount of Feedback: " + currentClassModel.getAmountOfFeedback()+ "\n"
                + "How good their examples are: " + currentClassModel.getHowGoodTheirEx()+ "\n"
                + "Job opportunities: " + currentClassModel.getJobOpportunities()+ "\n"
                + "Cake is good!!!";

        intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_EMAIL,recipientsList);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, MyFormatMessage);

        intent.setType("message/rfc822");
        startActivity(Intent.createChooser(intent, "Choose an email client"));
    }

    private void myInit()
    {
        //editText
        editTextTo = findViewById(R.id.editText_to);
        editTextSubject = findViewById(R.id.editText_subject);
        editTextMessage = findViewById(R.id.editText_message);

        //button
        butSend = findViewById(R.id.but_send);

        //spinner
        spinner = findViewById(R.id.spinner);
    }

    private void addNameToNameList(ArrayList<ClassModel> list)
    {
        for (int i = 0; i < list.size(); i++)
        {
            nameOfClassList.add(list.get(i).getName());
        }
    }
}
