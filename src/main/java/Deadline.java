public class Deadline extends Todo{
    protected String deadline;

    public Deadline(String description, String deadline){
        super(description);
        this.deadline = deadline;
    }

    public Deadline(String description) {
        super(description);
    }

    public String toString(){
        return "[D]" + getStatusIcon() + " " + getDescription() +
                " (by: " + deadline + ")";
    }
}
