package com.example.mandatorykearating.Activities;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.mandatorykearating.models.ClassModel;
import com.example.mandatorykearating.R;
import com.example.mandatorykearating.models.MyParceArraylist;

import java.util.ArrayList;

public class viewListAct extends AppCompatActivity
{
    private static final String TAG = "MyTest";

    //listView
    private ListView listView;

    //to next activity
    public static final String EXTRA_Mes_model = "list model";
    public static final String EXTRA_ID = "model id";
    public static final String EXTRA_home = "home";
    public static final String EXTRA_create = "create";
    public static final String EXTRA_reload = "reload";

    //store obj
    private MyParceArraylist myParceArraylist = new MyParceArraylist();
    private ArrayList<String> nameOfClassList = new ArrayList<>();

    //activity
    private Intent intent;

    //floatingButton
    FloatingActionButton floatingActBut;

    //snackbar
    private Snackbar snackbar;

    //toast
    private int toastDuration;
    private CharSequence toastText;
    private Toast toast;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_list);

        myInit();

        // Get the Intent that started this activity and extract the string
        intent = getIntent();
        if (intent != null)
        {
            if (intent.getParcelableArrayListExtra(MainActivity.EXTRA_Mes_check) != null)
            {
                myParceArraylist.setArrayListOfClass(intent.getParcelableArrayListExtra(MainActivity.EXTRA_Mes_check));
            }
            if (intent.getParcelableArrayListExtra(RateClass.EXTRA_saveChange) != null)
            {
                myParceArraylist.setArrayListOfClass(intent.getParcelableArrayListExtra(RateClass.EXTRA_saveChange));
                Log.d(TAG, "onCreate: "+ myParceArraylist.getArrayListOfClass().size());
            }
            if (intent.getParcelableArrayListExtra(viewListAct.EXTRA_reload) != null)
            {
                myParceArraylist.setArrayListOfClass(intent.getParcelableArrayListExtra(viewListAct.EXTRA_reload));
            }
        }

        //setup for listView
        addNameToNameList(myParceArraylist.getArrayListOfClass());
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1 ,nameOfClassList);
        listView.setAdapter(arrayAdapter);

        //action listner for viewLisr
        viewListListner(nameOfClassList);
    }

    private void myInit()
    {
        //snackbar
        snackbar = Snackbar.make(findViewById(R.id.RelativeLayout), R.string.snack_delete, Snackbar.LENGTH_SHORT);

        //floatButton
        floatingActBut = findViewById(R.id.floatingActButAdd);

        //toast
        toastDuration = Toast.LENGTH_LONG;
        toast = Toast.makeText(this,toastText,toastDuration);

        //listView
        listView = (ListView)findViewById(R.id.listView);
    }

    private void addNameToNameList(ArrayList<ClassModel> list)
    {
        for (int i = 0; i < list.size(); i++)
        {
            nameOfClassList.add(list.get(i).getName());
        }
    }

    private void viewListListner(final ArrayList list)
    {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                showToast("you click: " + list.get(position).toString());

                goToRateClass((ClassModel) myParceArraylist.getArrayListOfClass().get(position), position);
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener()
        {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id)
            {
                snackbar.setAction(R.string.snack_deleteMeth, new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        showToast( list.get(position).toString() + " is delete");
                        myParceArraylist.getArrayListOfClass().remove(position);
                        reloadAct();
                    }
                });
                snackbar.show();
                return true;
            }
        });
    }

    private void reloadAct()
    {
        intent = new Intent(this, viewListAct.class);

        intent.putExtra(EXTRA_reload, myParceArraylist.getArrayListOfClass());
        startActivity(intent);
    }

    private void goToRateClass(ClassModel model, int id)
    {
        intent = new Intent(this, RateClass.class);

        intent.putExtra(EXTRA_Mes_model, myParceArraylist.getArrayListOfClass());
        intent.putExtra(EXTRA_ID,Integer.toString(id));
        startActivity(intent);

    }

    public void home(View view)
    {
        intent = new Intent(this, MainActivity.class);

        intent.putExtra(EXTRA_home, myParceArraylist.getArrayListOfClass());
        startActivity(intent);
    }

    public void createAct(View view)
    {
        intent = new Intent(this, CreateClassModel.class);

        intent.putExtra(EXTRA_create, myParceArraylist.getArrayListOfClass());
        startActivity(intent);
    }

    public void showToast(String toastText)
    {
        toast.setText(toastText);
        toast.show();
    }
}
