package cs3220;

import cs3220.model.HolidaysModel;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

/**
 * DataComponent acts as the in-memory data store (singleton via @Component).
 * Spring injects this wherever needed — satisfies the @Component DI requirement.
 */
@Component
public class DataComponent {

    // Shared in-memory list — lives for the lifetime of the app
    private final List<HolidaysModel> holidays = new ArrayList<>();

    /**
     * Seed the initial US holiday data on startup.
     */
    public DataComponent() {
        holidays.add(new HolidaysModel("2026-01-01", "New Year's Day"));
        holidays.add(new HolidaysModel("2026-01-19", "Martin Luther King Jr. Day"));
        holidays.add(new HolidaysModel("2026-02-16", "Presidents' Day"));
        holidays.add(new HolidaysModel("2026-05-25", "Memorial Day"));
        holidays.add(new HolidaysModel("2026-07-03", "Independence Day (Observed)"));
        holidays.add(new HolidaysModel("2026-09-07", "Labor Day"));
        holidays.add(new HolidaysModel("2026-11-26", "Thanksgiving Day"));
        holidays.add(new HolidaysModel("2026-12-25", "Christmas Day"));
    }

    /** Returns list sorted by date ascending */
    public List<HolidaysModel> getAllSorted() {
        holidays.sort(Comparator.comparing(HolidaysModel::getHolidayDate));
        return holidays;
    }

    /** Check if a date is already taken (for duplicate detection on add) */
    public boolean dateExists(LocalDate date) {
        return holidays.stream()
                .anyMatch(h -> h.getHolidayDate().equals(date));
    }

    /**
     * Check if a date is already taken by another entry (for update flow).
     * Excludes the entry being updated.
     */
    public boolean dateExistsExcluding(LocalDate date, String excludeId) {
        return holidays.stream()
                .filter(h -> !h.getId().equals(excludeId))
                .anyMatch(h -> h.getHolidayDate().equals(date));
    }

    public void addHoliday(HolidaysModel h) {
        holidays.add(h);
    }

    public Optional<HolidaysModel> findById(String id) {
        return holidays.stream().filter(h -> h.getId().equals(id)).findFirst();
    }

    public void deleteById(String id) {
        holidays.removeIf(h -> h.getId().equals(id));
    }
}
