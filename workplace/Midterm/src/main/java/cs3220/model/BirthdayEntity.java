package cs3220.model;

public class BirthdayEntity {
    static int idSeed = 1;
    private int id;
    private String name;
    private String birthday;

    public BirthdayEntity(String name, String birthday) {
        this.id = idSeed++;
        this.name = name;
        this.birthday = birthday;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getBirthday() { return birthday; }
    public void setBirthday(String birthday) { this.birthday = birthday; }
}