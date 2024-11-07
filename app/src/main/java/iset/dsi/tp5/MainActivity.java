package iset.dsi.tp5;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import iset.dsi.tp5.databinding.ActivityMainBinding;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private ArrayList<Task> tasks = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setupSpinner();
        setupTimePicker();
        setupButtons();
    }

    private void setupSpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.days_of_week, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.daySpinner.setAdapter(adapter);
    }

    private void setupTimePicker() {
        binding.timePicker.setIs24HourView(false);
    }

    private void setupButtons() {
        binding.addTaskButton.setOnClickListener(v -> addTask());
        binding.clearListButton.setOnClickListener(v -> clearTasks());
    }

    private void addTask() {
        String taskName = binding.taskNameInput.getText().toString().trim();
        String selectedDay = binding.daySpinner.getSelectedItem().toString();
        int hour = binding.timePicker.getCurrentHour();
        int minute = binding.timePicker.getCurrentMinute();
        String selectedTime = String.format("%02d:%02d %s",
                (hour % 12 == 0) ? 12 : hour % 12,
                minute, hour >= 12 ? "PM" : "AM");

        if (!taskName.isEmpty()) {
            Task newTask = new Task(taskName, selectedDay, selectedTime);
            tasks.add(newTask);
            displayTasks();
            Toast.makeText(this, "Task added!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Enter a task name", Toast.LENGTH_SHORT).show();
        }
    }

    private void displayTasks() {
        StringBuilder displayText = new StringBuilder();
        for (Task task : tasks) {
            displayText.append(task.toString()).append("\n");
        }
        binding.resultView.setText(displayText.toString());
    }

    private void clearTasks() {
        tasks.clear();
        binding.resultView.setText("");
        Toast.makeText(this, "Task list cleared", Toast.LENGTH_SHORT).show();
    }
}
