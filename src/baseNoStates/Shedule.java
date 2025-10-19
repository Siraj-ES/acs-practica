package baseNoStates;

import java.time.*;
import java.util.Set;

public class Shedule{
    private Set<DayOfWeek> validDays;
    private LocalTime startHour;
    private LocalTime endHour;
    private LocalDate startDate;
    private LocalDate endDate;


    public Shedule(Set<DayOfWeek> validDays, LocalTime startHour, LocalTime endHour,LocalDate startDate, LocalDate endDate) {
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
    public void setStartHour(LocalTime startHour) {
      this.startHour = startHour;
    }

    public LocalTime getEndHour() {
      return endHour;
    }

    public void setEndHour(LocalTime endHour) {
      this.endHour = endHour;
    }

    public LocalDate getStartDate() {
      return startDate;
    }

    public void setStartDate(LocalDate startDate) {
      this.startDate = startDate;
    }

    public LocalDate getEndDate() {
      return endDate;
    }
    public void setEndDate(LocalDate endDate) {
      this.endDate = endDate;
    }



    public LocalDateTime getNow() {
        return Clock.getInstance().getDate();
    }



}
