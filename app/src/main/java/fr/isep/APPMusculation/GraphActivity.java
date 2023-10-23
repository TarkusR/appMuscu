package fr.isep.APPMusculation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GraphActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private ImageView hamburgerMenu;
    private static final String PREFERENCES_FILE = "app_musculation_data";
    private static final String WEIGHT_KEY = "weight";
    private static final String BODY_FAT_KEY = "body_fat";
    private static final String DATES_LIST_KEY = "dates_list";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.graph);
        clearSharedPreferencesData(); //use this to clear the data in case you want to try to break it (you wont lmao)
        drawerLayout = findViewById(R.id.drawer_layout);
        hamburgerMenu = findViewById(R.id.hamburgerMenu);

        hamburgerMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (drawerLayout.isDrawerOpen(findViewById(R.id.side_menu))) {
                    drawerLayout.closeDrawer(findViewById(R.id.side_menu));
                } else {
                    drawerLayout.openDrawer(findViewById(R.id.side_menu));
                }
            }
        });
        setupMenuItems();
        retrieveAndDisplayData();

        setupAddButton();

    }

    private void setupMenuItems() {

        LinearLayout menuItem1 = findViewById(R.id.menu_item_1);
        ImageView icon1 = menuItem1.findViewById(R.id.menu_icon);
        icon1.setImageResource(R.drawable.first_item);

        TextView label1 = menuItem1.findViewById(R.id.menu_label);
        label1.setText("HOME");


        LinearLayout menuItem2 = findViewById(R.id.menu_item_2);
        ImageView icon2 = menuItem2.findViewById(R.id.menu_icon);
        icon2.setImageResource(R.drawable.second_item);

        TextView label2 = menuItem2.findViewById(R.id.menu_label);
        label2.setText("CREATE YOUR WORKOUT");

        LinearLayout menuItem3 = findViewById(R.id.menu_item_3);
        ImageView icon3 = menuItem3.findViewById(R.id.menu_icon);
        icon3.setImageResource(R.drawable.third_item);

        TextView label3 = menuItem3.findViewById(R.id.menu_label);
        label3.setText("YOUR HISTORY");

        LinearLayout menuItem4 = findViewById(R.id.menu_item_4);
        ImageView icon4 = menuItem4.findViewById(R.id.menu_icon);
        icon4.setImageResource(R.drawable.fourth_item);

        TextView label4 = menuItem4.findViewById(R.id.menu_label);
        label4.setText("VIEW YOUR PROGRESS");


        menuItem1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GraphActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        menuItem2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GraphActivity.this, CreateWorkoutActivity.class);
                startActivity(intent);
            }
        });

        menuItem3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GraphActivity.this, HistoryActivity.class);
                startActivity(intent);
            }
        });

        menuItem4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GraphActivity.this, GraphActivity.class);
                startActivity(intent);
            }
        });


    }

    private void saveData(String key, String value) {
        SharedPreferences sharedPreferences = getSharedPreferences(PREFERENCES_FILE, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    private String retrieveData(String key) {
        SharedPreferences sharedPreferences = getSharedPreferences(PREFERENCES_FILE, MODE_PRIVATE);
        return sharedPreferences.getString(key, "");
    }

    private List<String> retrieveDateList() {
        SharedPreferences sharedPreferences = getSharedPreferences(PREFERENCES_FILE, MODE_PRIVATE);
        String dateListString = sharedPreferences.getString(DATES_LIST_KEY, "");
        return new ArrayList<>(Arrays.asList(dateListString.split(",")));
    }

    private void saveDateList(List<String> datesList) {
        SharedPreferences sharedPreferences = getSharedPreferences(PREFERENCES_FILE, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String dateListString = TextUtils.join(",", datesList);
        editor.putString(DATES_LIST_KEY, dateListString);
        editor.apply();
    }


    private void saveAllData() {
        EditText weightEditText = findViewById(R.id.editTextText2);
        EditText bodyFatEditText = findViewById(R.id.editTextText);

        String weight = weightEditText.getText().toString();
        String bodyFat = bodyFatEditText.getText().toString();
        String todayDate = LocalDate.now().toString();

        saveData(WEIGHT_KEY + "_" + todayDate, weight);
        saveData(BODY_FAT_KEY + "_" + todayDate, bodyFat);

        List<String> datesList = retrieveDateList();
        if (!datesList.contains(todayDate)) {
            datesList.add(todayDate);
            saveDateList(datesList);
        }
    }

    private void retrieveAndDisplayData() {
        List<String> dateList = retrieveDateList();

        List<DataPoint> weightDataPointsList = new ArrayList<>();
        List<DataPoint> bodyFatDataPointsList = new ArrayList<>();


        DataPoint[] premadeWeightData = {
                new DataPoint(0, 70),
                new DataPoint(1, 71),
                new DataPoint(2, 72),
                new DataPoint(3, 71.5),
                new DataPoint(4, 70.5),
                new DataPoint(5, 70),
                new DataPoint(6, 71)
        };

        DataPoint[] premadeBodyFatData = {
                new DataPoint(0, 15),
                new DataPoint(1, 14.9),
                new DataPoint(2, 14.8),
                new DataPoint(3, 15.1),
                new DataPoint(4, 15),
                new DataPoint(5, 15.2),
                new DataPoint(6, 15)
        };


        weightDataPointsList.addAll(Arrays.asList(premadeWeightData));
        bodyFatDataPointsList.addAll(Arrays.asList(premadeBodyFatData));

        for (int i = 0; i < dateList.size(); i++) {
            String weight = retrieveData(WEIGHT_KEY + "_" + dateList.get(i));
            String bodyFat = retrieveData(BODY_FAT_KEY + "_" + dateList.get(i));

            double weightValue = weight.isEmpty() ? 0 : Double.parseDouble(weight);
            double bodyFatValue = bodyFat.isEmpty() ? 0 : Double.parseDouble(bodyFat);

            weightDataPointsList.add(new DataPoint(i + 7, weightValue));
            bodyFatDataPointsList.add(new DataPoint(i + 7, bodyFatValue));
        }

        plotGraph(weightDataPointsList.toArray(new DataPoint[0]), bodyFatDataPointsList.toArray(new DataPoint[0]));
    }




    private void plotGraph(DataPoint[] weightDataPoints, DataPoint[] bodyFatDataPoints) {
        GraphView graph = findViewById(R.id.graph);

        LineGraphSeries<DataPoint> weightSeries = new LineGraphSeries<>(weightDataPoints);
        LineGraphSeries<DataPoint> bodyFatSeries = new LineGraphSeries<>(bodyFatDataPoints);

        graph.addSeries(weightSeries);
        graph.addSeries(bodyFatSeries);

        Button addButton = findViewById(R.id.button4);
        if (retrieveDateList().contains(LocalDate.now().toString())) {
            addButton.setEnabled(false);
        }
    }



    private void setupAddButton() {
        Button addButton = findViewById(R.id.button4);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveAllData();
                retrieveAndDisplayData();
            }
        });
    }


    private void clearSharedPreferencesData() {
        SharedPreferences sharedPreferences = getSharedPreferences(PREFERENCES_FILE, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(WEIGHT_KEY);
        editor.remove(BODY_FAT_KEY);
        editor.remove(DATES_LIST_KEY);
        editor.apply();
    }


}