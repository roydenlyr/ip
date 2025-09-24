package shinchan.data;

import shinchan.tasks.Deadline;
import shinchan.tasks.Event;
import shinchan.tasks.Task;
import shinchan.tasks.Todo;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Manages the persistence of tasks to and from disk.
 * <p>
 * The {@code DataManager} provides methods to load, save, and append
 * {@link Task} objects in a text-based format. Each task is serialized
 * into a line of text with fields separated by {@code |}.
 * <ul>
 *   <li>{@code T|done|description} for a {@link Todo}</li>
 *   <li>{@code D|done|description|by} for a {@link Deadline}</li>
 *   <li>{@code E|done|description|from|to} for an {@link Event}</li>
 * </ul>
 * A {@code done} field is represented as {@code "1"} for completed
 * tasks and {@code "0"} otherwise.
 */
public class DataManager {
    public static final Path DEFAULT_PATH = Path.of("data", "data.txt");
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");

    private final Path dataPath;

    /**
     * Creates a data manager that saves tasks to the given file name.
     *
     * @param fileName the file name to store tasks in
     */
    public DataManager(String fileName) {
        this(Path.of(fileName));
    }

    /**
     * Creates a data manager that saves tasks to the given file path.
     *
     * @param dataPath the file path to store tasks in
     */
    public DataManager(Path dataPath) {
        this.dataPath = dataPath;
    }

    /**
     * Loads all tasks from the storage file.
     *
     * @return a list of tasks read from the file
     * @throws IOException if an error occurs while reading the file
     */
    public List<Task> load() throws IOException {
        ensureFileExists();
        List<String> lines = Files.readAllLines(dataPath, StandardCharsets.UTF_8);
        List<Task> tasks = new ArrayList<>(lines.size());
        for (String line : lines) {
            if (line == null || line.isEmpty()) {
                continue;
            }
            Task task = parseRecord(line);
            if (task != null) {
                tasks.add(task);
            }
        }
        return tasks;
    }

    /**
     * Saves all tasks to the storage file, overwriting any existing data.
     *
     * @param tasks the list of tasks to save
     * @throws IOException if an error occurs while writing the file
     */
    public void saveAll(List<Task> tasks) throws IOException {
        ensureParentDir();
        Path tmp = dataPath.resolveSibling(dataPath.getFileName() + ".tmp");
        try (BufferedWriter writer = Files.newBufferedWriter(tmp, StandardCharsets.UTF_8)) {
            for (Task task : tasks) {
                writer.write(formatRecord(task));
                writer.newLine();
            }
        }
        Files.move(tmp, dataPath, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE);
    }

    /**
     * Appends a single task to the storage file.
     *
     * @param task the task to append
     * @throws IOException if an error occurs while writing the file
     */
    public void append(Task task) throws IOException {
        ensureFileExists();
        try (BufferedWriter writer = Files.newBufferedWriter(dataPath, StandardCharsets.UTF_8, StandardOpenOption.APPEND)) {
            writer.write(formatRecord(task));
            writer.newLine();
        }
    }

    /**
     * Saves all tasks to the default storage file.
     *
     * @param tasks the tasks to save
     * @throws IOException if an error occurs while writing the file
     */
    public static void writeToFile(List<Task> tasks) throws IOException {
        new DataManager(DEFAULT_PATH).saveAll(tasks);
    }

    /**
     * Appends a single task to the default storage file.
     *
     * @param task the task to append
     * @throws IOException if an error occurs while writing the file
     */
    public static void appendToFile(Task task) throws IOException {
        new DataManager(DEFAULT_PATH).append(task);
    }

    /**
     * Ensures the storage file exists, creating it and its parent
     * directories if necessary.
     */
    private void ensureFileExists() throws IOException {
        ensureParentDir();
        if (!Files.exists(dataPath)) {
            Files.createFile(dataPath);
        }
    }

    /**
     * Ensures the parent directory of the storage file exists.
     */
    private void ensureParentDir() throws IOException {
        Path parent = dataPath.getParent();
        if (parent != null && !Files.exists(parent)) {
            Files.createDirectories(parent);
        }
    }

    /**
     * Formats a task into its storage string representation.
     */
    private static String formatRecord(Task task) {
        String done = task.isDone() ? "1" : "0";
        String description = sanitize(task.getDescription());
        if (task instanceof Event event) {
            return String.join("|", "T", done, description,
                    FORMATTER.format(event.getFrom()), FORMATTER.format(event.getBy()));
        }
        if (task instanceof Deadline deadline) {
            return String.join("|", "D", done, description, FORMATTER.format(deadline.getBy()));
        }
        if (task instanceof Todo) {
            return String.join("|", "T", done, description);
        }
        throw new IllegalArgumentException("Unknown Task type: " + task.getClass().getName());
    }

    /**
     * Parses a line of text into a task object.
     */
    private static Task parseRecord(String line) {
        String[] parts = line.split("\\|", -1);
        if (parts.length < 3) {
            return null;
        }
        String type = parts[0].trim();
        boolean isDone = "1".equals(parts[1].trim());
        String description = unsanitize(parts[2]);

        switch (type) {
        case "T":
            Todo todo = new Todo(description);
            todo.setDone(isDone);
            return todo;
        case "D":
            if (parts.length < 4) {
                return null;
            }
            LocalDateTime by = LocalDateTime.parse(parts[3].trim(), FORMATTER);
            Deadline deadline = new Deadline(description, by);
            deadline.setDone(isDone);
            return deadline;
        case "E":
            if (parts.length < 5) {
                return null;
            }
            LocalDateTime from = LocalDateTime.parse(parts[3].trim(), FORMATTER);
            LocalDateTime to = LocalDateTime.parse(parts[4].trim(), FORMATTER);
            Event event = new Event(description, from, to);
            event.setDone(isDone);
            return event;
        default:
            return null;
        }
    }

    /**
     * Replaces reserved characters (like {@code |}) in task descriptions
     * to prevent conflicts during storage.
     */
    private static String sanitize(String line) {
        return line == null ? "" : line.replace("|", "/");
    }

    /**
     * Reverses the sanitization applied during saving.
     */
    private static String unsanitize(String line) {
        return line;
    }
}
