package cs3220.model;

public class GuestBookEntry {

    private static int idSeed = 1;

    private int id;
    private String name;
    private String message;

    public GuestBookEntry(String name, String message) {
        this.id = idSeed++;
        this.name = name;
        this.message = message;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getMessage() {
        return message;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMessage(String message) {
        this.message = message;
    }

	
}