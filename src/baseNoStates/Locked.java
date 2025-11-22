package baseNoStates;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Locked extends DoorState {

  private static final Logger log = LoggerFactory.getLogger(Locked.class);

  public Locked(Door door) {
    super(door);
  }

  @Override
  public String getName() {
    return States.LOCKED;
  }

  @Override
  public void open() {
    log.warn("Have to unlock door {} to open it", door.getId());
  }

  @Override
  public void close() {
    log.warn("Door {} is already closed", door.getId());
  }

  @Override
  public void lock() {
    door.setState(new Locked(door));
  }

  @Override
  public void unlock() {
    door.setState(new Unlocked(door));
  }

  @Override
  public void unlocked_shortly() {
    Clock clock = door.getClock();
    Unlocked_shortly unlockedShortly = new Unlocked_shortly(door, clock);
    door.setState(unlockedShortly);
  }
}
