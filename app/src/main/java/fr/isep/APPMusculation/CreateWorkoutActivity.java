package fr.isep.APPMusculation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

public class CreateWorkoutActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private ImageView hamburgerMenu;
    private EditText exerciseNameEditText, exerciseDescriptionEditText;
    private Button addExerciseButton;
    private LinearLayout layoutForExercises;
    private Button finishButton;
    private List<String> exerciseList = new ArrayList<>();
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_workout);

        initializeViews();

        setHamburgerMenuListener();

        setAddExerciseButtonListener();

        setupMenuItems();

        setFinishButtonListener();
    }

    private void initializeViews() {
        drawerLayout = findViewById(R.id.drawer_layout);
        hamburgerMenu = findViewById(R.id.hamburgerMenu);
        exerciseNameEditText = findViewById(R.id.editText2);
        exerciseDescriptionEditText = findViewById(R.id.textInputEditText2);
        addExerciseButton = findViewById(R.id.button2);
        layoutForExercises = findViewById(R.id.layoutForExercises);

        sharedPreferences = getSharedPreferences("WorkoutPrefs", Context.MODE_PRIVATE);
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
                Intent intent = new Intent(CreateWorkoutActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        menuItem2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CreateWorkoutActivity.this, CreateWorkoutActivity.class);
                startActivity(intent);
            }
        });

        menuItem3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CreateWorkoutActivity.this, HistoryActivity.class);
                startActivity(intent);
            }
        });

        menuItem4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CreateWorkoutActivity.this, GraphActivity.class);
                startActivity(intent);
            }
        });


    }
    private void setHamburgerMenuListener() {
        hamburgerMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleSideMenu();
            }
        });
    }

    private void toggleSideMenu() {
        if (drawerLayout.isDrawerOpen(findViewById(R.id.side_menu))) {
            drawerLayout.closeDrawer(findViewById(R.id.side_menu));
        } else {
            drawerLayout.openDrawer(findViewById(R.id.side_menu));
        }
    }

    private void setAddExerciseButtonListener() {
        addExerciseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addExercise();
            }
        });
    }

    private void addExercise() {
        String exerciseName = exerciseNameEditText.getText().toString();
        String exerciseDescription = exerciseDescriptionEditText.getText().toString();

        exerciseList.add(exerciseName + " - " + exerciseDescription);

        saveExerciseInPreferences(exerciseName, exerciseDescription);
        resetInputFields();
        displayExerciseInLayout(exerciseName, exerciseDescription);
    }

    private void saveExerciseInPreferences(String exerciseName, String exerciseDescription) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("ExerciseName" + exerciseName, exerciseName);
        editor.putString("ExerciseDescription" + exerciseName, exerciseDescription);
        editor.apply();
    }


    private void resetInputFields() {
        exerciseNameEditText.setText("");
        exerciseDescriptionEditText.setText("");
    }

    private void removeExercise(TextView exerciseTextView, String exerciseName) {
        AlertDialog.Builder builder = new AlertDialog.Builder(CreateWorkoutActivity.this);
        builder.setTitle("Delete Exercise");
        builder.setMessage("Are you sure you want to delete this exercise?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                layoutForExercises.removeView(exerciseTextView);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove("ExerciseName" + exerciseName);
                editor.remove("ExerciseDescription" + exerciseName);
                editor.apply();
            }
        });
        builder.setNegativeButton("No", null);
        builder.show();
    }


    private void displayExerciseInLayout(String exerciseName, String exerciseDescription) {
        TextView savedExerciseTextView = new TextView(CreateWorkoutActivity.this);
        savedExerciseTextView.setText(exerciseName + " - " + exerciseDescription);
        savedExerciseTextView.setBackgroundColor(getResources().getColor(R.color.green));
        savedExerciseTextView.setPadding(10, 10, 10, 10);

        savedExerciseTextView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                v.setBackgroundColor(getResources().getColor(R.color.darker_green));

                removeExercise((TextView) v, exerciseName);
                return true;
            }
        });

        layoutForExercises.addView(savedExerciseTextView);
    }

    private void setFinishButtonListener() {
        finishButton = findViewById(R.id.button5);
        finishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveAllExercises();
            }
        });
    }


    private void saveAllExercises() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        JSONArray jsonArray = new JSONArray(exerciseList);
        editor.putString("AllExercises", jsonArray.toString());
        editor.apply();
        Intent intent = new Intent(CreateWorkoutActivity.this, MainActivity.class);
        startActivity(intent);
    }


}
