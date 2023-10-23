package fr.isep.APPMusculation;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class WorkoutAdapter extends RecyclerView.Adapter<WorkoutAdapter.WorkoutViewHolder> {

    private List<String> workoutList;
    private Context context;

    public WorkoutAdapter(Context context, List<String> workoutList) {
        this.context = context;
        this.workoutList = workoutList;
    }
    @NonNull
    @Override
    public WorkoutViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.workout_item, parent, false);
        return new WorkoutViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WorkoutViewHolder holder, int position) {
        holder.workoutTextView.setText(workoutList.get(position));
    }

    @Override
    public int getItemCount() {
        return workoutList.size();
    }

    private List<String> getSavedWorkouts() {
        List<String> retrievedExercises = new ArrayList<>();
        SharedPreferences sharedPreferences = context.getSharedPreferences("WorkoutPrefs", Context.MODE_PRIVATE);
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


    public class WorkoutViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView workoutTextView;

        public WorkoutViewHolder(@NonNull View itemView) {

            super(itemView);
            workoutTextView = itemView.findViewById(R.id.workout_text_view);
            itemView.setOnClickListener(this);
        }



        @Override
        public void onClick(View v) {
            List<String> savedWorkouts = getSavedWorkouts();
            String workoutName = savedWorkouts.get(getAdapterPosition());
            Intent intent = new Intent(v.getContext(), ExerciseDetailActivity.class);
            intent.putExtra("WORKOUT_NAME", workoutName);
            v.getContext().startActivity(intent);
        }
    }
}
