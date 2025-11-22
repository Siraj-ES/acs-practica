package baseNoStates;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;

public class Shedule {

  private Set<DayOfWeek> validDays;
  private final LocalTime startHour;
  private final LocalTime endHour;
  private final LocalDate startDate;
  private final LocalDate endDate;

  public Shedule(Set<DayOfWeek> validDays,
                 LocalTime startHour,
                 LocalTime endHour,
                 LocalDate startDate,
                 LocalDate endDate) {
    this.validDays = validDays;
    this.startHour = startHour;
    this.endHour = endHour;
    this.startDate = startDate;
    this.endDate = endDate;
  }

  public Set<DayOfWeek> getValidDays() {
    return validDays;
  }

  public void setValidDays(Set<DayOfWeek> validDays) {
    this.validDays = validDays;
  }

  public LocalTime getStartHour() {
    return startHour;
  }

  public LocalTime getEndHour() {
    return endHour;
  }

  public LocalDate getStartDate() {
    return startDate;
  }

  public LocalDate getEndDate() {
    return endDate;
  }
}
