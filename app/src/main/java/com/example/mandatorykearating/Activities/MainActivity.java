package com.example.mandatorykearating.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mandatorykearating.models.ClassModel;
import com.example.mandatorykearating.R;
import com.example.mandatorykearating.models.MyParceArraylist;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
{
    //activity
    private Intent intent;

    //to next activity
    private static final String TAG = "MyTest";
    public static final String EXTRA_Mes_create = "create";
    public static final String EXTRA_Mes_check = "check";
    public static final String EXTRA_SendEmail = "mail";

    private MyParceArraylist myParceArraylist = new MyParceArraylist();

    //navigation drawer/ sidebar menu (my version)
    private  NavigationView navigationView;
    private int turnOnOff = 1;

    //rating
    private RatingBar ratingBar;
    private TextView txtRatingValue;

    //toast
    private int toastDuration;
    private CharSequence toastText;
    private Toast toast;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //initiate my variables
        myInit();
        loadData();
        //test();

        intent = getIntent();
        if (intent != null)
        {
            if (intent.getParcelableArrayListExtra(CreateClassModel.EXTRA_return_create) != null)
            {
                myParceArraylist.setArrayListOfClass(intent.getParcelableArrayListExtra(CreateClassModel.EXTRA_return_create));
                Log.d(TAG, "onCreate: ");
            }
            if (intent.getParcelableArrayListExtra(viewListAct.EXTRA_home) != null)
            {
                myParceArraylist.setArrayListOfClass(intent.getParcelableArrayListExtra(viewListAct.EXTRA_home));
            }
        }
    }

    private void myInit()
    {
        //side menu
        navigationView = findViewById(R.id.navList);
        addListenerOnNavDraw();

        //rating
        ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        txtRatingValue = (TextView) findViewById(R.id.rateVal);
        addListenerOnRatingBar(ratingBar,txtRatingValue);

        //toast
        toastDuration = Toast.LENGTH_LONG;
        toast = Toast.makeText(this,toastText,toastDuration);
    }

    private void addListenerOnNavDraw()
    {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        // set item as selected to persist highlight
                        menuItem.setChecked(true);

                        if (menuItem.getTitle().equals("Create class"))
                        {
                            toastText = "go to create";
                            toast.setText(toastText);
                            toast.show();
                            createClassModel();
                        }
                        else if (menuItem.getTitle().equals("Take a picture"))
                        {
                            toastText = "toast: test delete";
                            toast.setText(toastText);
                            toast.show();
                            takePicAct();
                        }
                        else if (menuItem.getTitle().equals("Save list of class"))
                        {
                            toastText = "toast: save";
                            toast.setText(toastText);
                            toast.show();
                            saveData();
                        }
                        else if (menuItem.getTitle().equals("Load list of class"))
                        {
                            toastText = "toast: load";
                            toast.setText(toastText);
                            toast.show();
                            loadData();
                        }

                        return true;
                    }
                });
    }

    private void addListenerOnRatingBar(RatingBar ratingBar, final TextView txtRatingValue)
    {
        //if rating change current rating value in textView (automatically)
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener()
        {
            public void onRatingChanged(RatingBar ratingBar, float rating,
                                        boolean fromUser)
            {
                if (rating >= 5)
                {
                    txtRatingValue.setText(String.valueOf("Perfeckt: "+ rating));
                }
                else if (rating >= 4)
                {
                    txtRatingValue.setText(String.valueOf("Amazing: "+ rating));
                }
                else if (rating >= 3)
                {
                    txtRatingValue.setText(String.valueOf("Average: "+ rating));
                }
                else if (rating >= 2)
                {
                    txtRatingValue.setText(String.valueOf("ok: "+ rating));
                }
                else if (rating >= 1)
                {
                    txtRatingValue.setText(String.valueOf("bad: "+ rating));
                }
                else
                {
                    txtRatingValue.setText(String.valueOf("very bad: "+ rating));
                }
            }
        });
    }

    public void sendEmail(View view)
    {
        intent = new Intent(this,SendEmail.class);
        intent.putExtra(EXTRA_SendEmail,myParceArraylist.getArrayListOfClass());
        startActivity(intent);
    }

    public void goToViewListOfClass(View view)
    {
        intent = new Intent(this, viewListAct.class);
        intent.putExtra(EXTRA_Mes_check, myParceArraylist.getArrayListOfClass());
        startActivity(intent);
    }


    public void showSideMenu(View view)
    {
        if (turnOnOff == 1)
        {
            navigationView.setVisibility(view.VISIBLE);
            turnOnOff = 0;
        }
        else
        {
            navigationView.setVisibility(view.INVISIBLE);
            turnOnOff = 1;
        }
    }

    private void createClassModel()
    {
        intent = new Intent(this, CreateClassModel.class);
        intent.putExtra(EXTRA_Mes_create, myParceArraylist.getArrayListOfClass());
        startActivity(intent);
    }

    private void takePicAct()
    {
        intent = new Intent(this, TakeAFoto.class);
        startActivity(intent);
    }

    private void saveData()
    {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(myParceArraylist.getArrayListOfClass());
        editor.putString("task list", json);
        editor.apply();
    }

    private void loadData()
    {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences",MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("task list",null);
        Type type = new TypeToken<ArrayList<ClassModel>>() {}.getType();
        myParceArraylist.setArrayListOfClass((ArrayList) gson.fromJson(json,type));
    }
/*
    private void test()
    {
        //test
        ClassModel classModel1 = new ClassModel("App development","Faisal");
        ClassModel classModel2 = new ClassModel("Cake and gaming","Jackie");
        ClassModel classModel3 = new ClassModel("iOS development","Jon");
        ClassModel classModel4 = new ClassModel("Python","Claus");

        classModel1.setJobOpportunities(3);
        classModel2.setJobOpportunities(2);

        myParceArraylist.getArrayListOfClass().add(classModel1);
        myParceArraylist.getArrayListOfClass().add(classModel2);
        myParceArraylist.getArrayListOfClass().add(classModel3);
        myParceArraylist.getArrayListOfClass().add(classModel4);
    }
*/
}
