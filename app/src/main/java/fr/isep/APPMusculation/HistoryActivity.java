package fr.isep.APPMusculation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.HashSet;
import java.util.Set;

public class HistoryActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private ImageView hamburgerMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history);

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
        updateWorkoutCount();
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
                Intent intent = new Intent(HistoryActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        menuItem2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HistoryActivity.this, CreateWorkoutActivity.class);
                startActivity(intent);
            }
        });

        menuItem3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HistoryActivity.this, HistoryActivity.class);
                startActivity(intent);
            }
        });

        menuItem4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HistoryActivity.this, GraphActivity.class);
                startActivity(intent);
            }
        });


    }

    private void updateWorkoutCount() {
        SharedPreferences sharedPreferences = getSharedPreferences("WorkoutPrefs", Context.MODE_PRIVATE);
        Set<String> workoutSet = sharedPreferences.getStringSet("Workouts", new HashSet<String>());
        long currentTime = System.currentTimeMillis();
        long thirtyDaysInMillis = 30L * 24L * 60L * 60L * 1000L;
        int count = 0;

        for (String time : workoutSet) {
            long timestamp = Long.parseLong(time);
            if (currentTime - timestamp <= thirtyDaysInMillis) {
                count++;
            }
        }

        String message = "You worked out " + count + " times in the last 30 days.";


        TextView textView6 = findViewById(R.id.textView6);
        textView6.setText(message);
    }
}