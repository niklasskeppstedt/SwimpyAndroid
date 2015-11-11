package se.skeppstedt.swimpy;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.ViewManager;
import android.view.ViewParent;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import se.skeppstedt.swimpy.application.Swimmer;
import se.skeppstedt.swimpy.application.SwimmerApplication;

public class MainActivity extends AppCompatActivity {

    private ArrayList<String> selectedSwimmers = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TableLayout table =(TableLayout)findViewById(R.id.swimTable);
        for (final Swimmer swimmer : getSwimmerApplication().swimmers             ) {
            final TableRow tableRow = new TableRow(this);
//        tableRow.setLayoutParams(new TableLayout.LayoutParams( TableLayout.LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT));
            CheckBox checkBox = new CheckBox(this);
            checkBox.setTag("checkBox");
            checkBox.setVisibility(View.INVISIBLE);
            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked){
                        selectedSwimmers.add(swimmer.octoId);
                    } else {
                        selectedSwimmers.remove(swimmer.octoId);
                    }
                }
            });
            //checkBox.setBackgroundColor(Color.GREEN);
            tableRow.addView(checkBox);
            final TextView textview = new TextView(this);
            textview.setText(swimmer.name);
            tableRow.addView(textview);
            table.addView(tableRow);
            tableRow.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    Intent intent = new Intent(MainActivity.this, ShowSwimmerActivity.class);
                    intent.putExtra("swimmerid", swimmer.octoId);
                    startActivity(intent);
                    return true;
                }
            });
            tableRow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View clickedTableRow) {
                    CheckBox checkBox = (CheckBox) clickedTableRow.findViewWithTag("checkBox");
                    if (checkBox != null) {
                        checkBox.setChecked(!checkBox.isChecked());
                    }
                    ViewParent table = clickedTableRow.getParent();
                    int tableRowCount = ((ViewGroup) table).getChildCount();
                    for (int i = 0; i < tableRowCount; ++i) {
                        TableRow tableRow = (TableRow) ((ViewGroup) table).getChildAt(i);
                        checkBox = (CheckBox) tableRow.findViewWithTag("checkBox");
                        if (checkBox != null) {
                            if (checkBox.getVisibility() == View.INVISIBLE) {
                                checkBox.setVisibility(View.VISIBLE);
                            }
                        }
                    }

                }
            });
            Button medley50button = (Button) findViewById(R.id.medley50Button);
            medley50button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, ShowBestMedleyTeams50Activity.class);
                    intent.putStringArrayListExtra("selectedswimmers", selectedSwimmers);
                    startActivity(intent);
                }
            });
        }

    }



    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
    }

    SwimmerApplication getSwimmerApplication() {
        return (SwimmerApplication) getApplication();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
