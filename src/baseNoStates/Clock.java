package baseNoStates;

import java.time.LocalDateTime;
import java.util.Timer;
import java.util.TimerTask;

public class Clock extends Observable {
  private LocalDateTime date;
  private Timer timer;
  private int period; // interval in seconds


  public Clock(int period) {
    this.period = period;
    this.timer = new Timer();
    this.date = LocalDateTime.now();
  }

  public void start() {
    TimerTask repeatedTask = new TimerTask() {
      @Override
      public void run() {
        date = LocalDateTime.now();
        System.out.println("Clock updated at: " + date);
        notifyObservers();

      }
    };

    timer.scheduleAtFixedRate(repeatedTask, 0, 1000L* period);
  }

  public void stop() {
    timer.cancel();
  }

  public int getPeriod() {
    return period;
  }


  public LocalDateTime getDate() {
    return date;
  }
}
