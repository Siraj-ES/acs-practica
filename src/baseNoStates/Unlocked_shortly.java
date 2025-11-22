package baseNoStates;

import java.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Unlocked_shortly extends DoorState implements Observer {

  private static final Logger log =
      LoggerFactory.getLogger(Unlocked_shortly.class);

  private int sec;
  private final Clock clock;

  public Unlocked_shortly(Door door, Clock clock) {
    super(door);
    this.clock = clock;
    this.clock.addObserver(this);
    this.sec = 0;

    name = States.UNLOCKED_SHORTLY;
    LocalDateTime dateTimeUnlocked = LocalDateTime.now();

    log.info(
        "Door {} enters Unlocked shortly at {}",
        door.getId(),
        dateTimeUnlocked
    );
  }

  @Override
  public String getName() {
    return "unlocked_shortly";
  }

  @Override
  public void open() {
    door.setClosed(false);
    log.info("Opening the door {}", door.getId());
  }

  @Override
  public void close() {
    door.setClosed(true);
    log.info("Closing the door {}", door.getId());
  }

  @Override
  public void lock() {
    // nothing
  }

  @Override
  public void unlock() {
    // nothing
  }

  @Override
  public void unlocked_shortly() {
    // nothing
  }

  @Override
  public void update() {
    this.sec++;
    if (sec > 9) {
      if (door.isClosed()) {
        door.setState(new Locked(door));
        log.info(
            "10 sec have passed and door {} was CLOSED → Locking...",
            door.getId()
        );
      } else {
        door.setState(new Propped(door));
        log.warn(
            "10 sec have passed and door {} was OPEN → Propped...",
            door.getId()
        );
      }
      clock.removeObserver(this);
    }
  }
}
