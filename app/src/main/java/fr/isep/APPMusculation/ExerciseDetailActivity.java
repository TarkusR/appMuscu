package fr.isep.APPMusculation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ExerciseDetailActivity extends AppCompatActivity {
    private SharedPreferences sharedPreferences;
    private List<String> allExercises;
    private int currentExerciseIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exercice);

        sharedPreferences = getSharedPreferences("WorkoutPrefs", MODE_PRIVATE);

        allExercises = getAllExercisesFromPreferences();

        if (!allExercises.isEmpty()) {
            updateExerciseDetails(allExercises.get(currentExerciseIndex));
        }

        Button doneButton = findViewById(R.id.button3);
        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentExerciseIndex++;
                if (currentExerciseIndex < allExercises.size()) {

                    updateExerciseDetails(allExercises.get(currentExerciseIndex));
                } else {
                    saveWorkoutTimestamp();
                    Toast.makeText(ExerciseDetailActivity.this, "Bravo!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(ExerciseDetailActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    private List<String> getAllExercisesFromPreferences() {
        List<String> exercises = new ArrayList<>();
        String exercisesString = sharedPreferences.getString("AllExercises", "");
        if (!exercisesString.isEmpty()) {
            try {
                JSONArray jsonArray = new JSONArray(exercisesString);
                for (int i = 0; i < jsonArray.length(); i++) {
                    exercises.add(jsonArray.getString(i));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return exercises;
    }

    private void updateExerciseDetails(String exerciseDetails) {
        String[] details = exerciseDetails.split(" - ");
        String exerciseName = details[0];
        String exerciseDescription = details[1];

        TextView exerciseNameTextView = findViewById(R.id.textView);
        TextView exerciseDescriptionTextView = findViewById(R.id.textView2);

        exerciseNameTextView.setText(exerciseName);
        exerciseDescriptionTextView.setText(exerciseDescription);
    }

    private void saveWorkoutTimestamp() {
        SharedPreferences sharedPreferences = getSharedPreferences("WorkoutPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        long currentTimestamp = System.currentTimeMillis();

        Set<String> workoutSet = sharedPreferences.getStringSet("Workouts", new HashSet<String>());
        workoutSet.add(String.valueOf(currentTimestamp));
        editor.putStringSet("Workouts", workoutSet);
        editor.apply();
    }
}
