package cs3220.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

/**
 * Model representing a single holiday entry.
 * id is auto-generated UUID so delete/update can target a specific entry safely.
 */
public class HolidaysModel {

    private String id;
    private LocalDate holidayDate;
    private String holiday;

    // DateTimeFormatter for display: "dd MMMM yyyy"
    private static final DateTimeFormatter DISPLAY_FORMAT =
            DateTimeFormatter.ofPattern("dd MMMM yyyy");

    /**
     * Constructor used by DataComponent to seed initial data.
     * @param dateStr ISO date string "yyyy-MM-dd"
     * @param holiday holiday name
     */
    public HolidaysModel(String dateStr, String holiday) {
        this.id = UUID.randomUUID().toString();
        this.holidayDate = LocalDate.parse(dateStr);
        this.holiday = holiday;
    }

    // --- Getters ---

    public String getId() { return id; }

    public LocalDate getHolidayDate() { return holidayDate; }

    public String getHoliday() { return holiday; }

    /** Returns formatted date string for display in templates */
    public String getFormattedDate() {
        return holidayDate.format(DISPLAY_FORMAT);
    }

    // --- Setters (used by update flow) ---

    public void setHolidayDate(LocalDate holidayDate) { this.holidayDate = holidayDate; }

    public void setHoliday(String holiday) { this.holiday = holiday; }
}
