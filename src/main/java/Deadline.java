public class Deadline extends Todo{
    protected String by;

    public Deadline(String description, String by){
        super(description);
        this.by = by;
    }

    public Deadline(String description) {
        super(description);
    }

    public String toString(){
        return "[D]" + getStatusIcon() + " " + getDescription() +
                " (by: " + by + ")";
    }
}
