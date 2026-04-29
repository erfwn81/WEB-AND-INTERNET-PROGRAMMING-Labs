package cs3220.model;

public class HolidayEntry {

    static int idSeed = 1;

    private int id;
    private String holidayDate; // yyyy-MM-dd
    private String holiday;

    public HolidayEntry(String holidayDate, String holiday) {
        this.id = idSeed++;
        this.holidayDate = holidayDate;
        this.holiday = holiday;
    }

    public int getId() {
        return id;
    }

    public String getHolidayDate() {
        return holidayDate;
    }

    public void setHolidayDate(String holidayDate) {
        this.holidayDate = holidayDate;
    }

    public String getHoliday() {
        return holiday;
    }

    public void setHoliday(String holiday) {
        this.holiday = holiday;
    }
}