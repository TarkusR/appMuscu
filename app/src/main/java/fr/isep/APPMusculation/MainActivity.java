package fr.isep.APPMusculation;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private SharedPreferences sharedPreferences;
    private DrawerLayout drawerLayout;
    private ImageView hamburgerMenu;
    private RecyclerView workoutsRecyclerView;
    private WorkoutAdapter workoutAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acceuil);

        drawerLayout = findViewById(R.id.drawer_layout);
        hamburgerMenu = findViewById(R.id.hamburgerMenu);
        sharedPreferences = getSharedPreferences("WorkoutPrefs", Context.MODE_PRIVATE);
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
        workoutsRecyclerView = findViewById(R.id.workout_recycler_view);
        workoutsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<String> savedWorkouts = getSavedWorkouts();
        workoutAdapter = new WorkoutAdapter(this, savedWorkouts);
        workoutsRecyclerView.setAdapter(workoutAdapter);
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
                Intent intent = new Intent(MainActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        menuItem2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CreateWorkoutActivity.class);
                startActivity(intent);
            }
        });

        menuItem3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, HistoryActivity.class);
                startActivity(intent);
            }
        });

        menuItem4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, GraphActivity.class);
                startActivity(intent);
            }
        });

        Button createWorkoutButton = findViewById(R.id.button);
        createWorkoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CreateWorkoutActivity.class);
                startActivity(intent);
            }
        });

        TextView armWorkoutTextView1 = findViewById(R.id.textView7);
        armWorkoutTextView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, WorkoutActivity.class);
                startActivity(intent);
            }
        });


    }

    private List<String> getSavedWorkouts() {
        List<String> retrievedExercises = new ArrayList<>();


        String exercisesString = sharedPreferences.getString("AllExercises", "");


        if (!exercisesString.equals("")) {
            try {
                JSONArray jsonArray = new JSONArray(exercisesString);

                for (int i = 0; i < jsonArray.length(); i++) {
                    retrievedExercises.add(jsonArray.getString(i));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return retrievedExercises;
    }



}
