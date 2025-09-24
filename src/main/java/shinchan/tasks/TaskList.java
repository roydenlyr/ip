package shinchan.tasks;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class TaskList {
    private final ArrayList<Task> taskList;

    public TaskList(ArrayList<Task> taskList) {
        this.taskList = taskList;
    }

    public int size() {
        return taskList.size();
    }

    public Task get(int index) {
        return taskList.get(index);
    }

    public void add(Task task) {
        taskList.add(task);
    }

    public void removeAt(int index) {
        taskList.remove(index);
    }

    public void setDone(int index, boolean isDone) {
        taskList.get(index).setDone(isDone);
    }

    public List<Task> find(Predicate<Task> predicate) {
        return taskList.stream().filter(predicate).toList();
    }

    public List<Task> asUnmodifiableList() {
        return List.copyOf(taskList);
    }
}
