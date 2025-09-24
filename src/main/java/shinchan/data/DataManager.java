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

public class DataManager {
    public static final Path DEFAULT_PATH = Path.of("data", "data.txt");
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");

    private final Path dataPath;

    public DataManager(String fileName) {
        this(Path.of(fileName));
    }

    public DataManager(Path dataPath) {
        this.dataPath = dataPath;
    }

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

    public void append(Task task) throws IOException {
        ensureFileExists();
        try (BufferedWriter writer = Files.newBufferedWriter(dataPath, StandardCharsets.UTF_8, StandardOpenOption.APPEND)) {
            writer.write(formatRecord(task));
            writer.newLine();
        }
    }

    public static void writeToFile(List<Task> tasks) throws IOException {
        new DataManager(DEFAULT_PATH).saveAll(tasks);
    }

    public static void appendToFile(Task task) throws IOException {
        new DataManager(DEFAULT_PATH).append(task);
    }

    private void ensureFileExists() throws IOException {
        ensureParentDir();
        if (!Files.exists(dataPath)) {
            Files.createFile(dataPath);
        }
    }

    private void ensureParentDir() throws IOException {
        Path parent = dataPath.getParent();
        if (parent != null && !Files.exists(parent)) {
            Files.createDirectories(parent);
        }
    }

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

    private static String sanitize(String line) {
        return line == null ? "" : line.replace("|", "/");
    }

    private static String unsanitize(String line) {
        return line;
    }
}
