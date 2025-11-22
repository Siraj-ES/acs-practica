package baseNoStates;

import java.time.LocalDateTime;
import java.util.Timer;
import java.util.TimerTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/*
  This class maintains and updates the ACS system time.
  Clock acts as an Observable in the Observer pattern: several objects can
  subscribe to receive periodic notifications and update their internal state
  when a certain time expires.

  In this system, the DoorState Unlocked registers as an observer to correctly
  implement the "unlock_shortly" action.

  The class uses a Java Timer to execute a repetitive task every 'period'
  seconds. At each tick, the clock updates the timestamp and notifies all
  registered observers.

  Only one clock must exist for the whole system, so this class is implemented
  as a Singleton.
*/

public class Clock extends Observable {

  private static final Logger log = LoggerFactory.getLogger(Clock.class);

  private LocalDateTime date;
  private final Timer timer;
  private final int period; // interval in seconds
  private static Clock instance;

  private Clock(int period) {
    this.period = period;
    this.timer = new Timer();
    this.date = LocalDateTime.now();
  }

  public void start() {
    TimerTask repeatedTask = new TimerTask() {
      @Override
      public void run() {
        date = LocalDateTime.now();
        log.debug("Clock updated at: {}", date);
        notifyObservers();
      }
    };

    timer.scheduleAtFixedRate(repeatedTask, 0, 1000L * period);
  }

  public static Clock getInstance() {
    if (instance == null) {
      instance = new Clock(1);
      return instance;
    }
    return instance;
  }

  public LocalDateTime getDate() {
    return date;
  }
}
