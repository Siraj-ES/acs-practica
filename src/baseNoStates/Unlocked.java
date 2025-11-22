package baseNoStates;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Unlocked extends DoorState {

  private static final Logger log = LoggerFactory.getLogger(Unlocked.class);

  public Unlocked(Door door) {
    super(door);
  }

  @Override
  public String getName() {
    return States.UNLOCKED;
  }

  @Override
  public void open() {
    if (door.isClosed()) {
      door.setClosed(false);
    } else {
      log.warn("Door {} is already opened", door.getId());
    }
  }

  @Override
  public void close() {
    if (!door.isClosed()) {
      door.setClosed(true);
    } else {
      log.warn("Door {} is already closed", door.getId());
    }
  }

  @Override
  public void lock() {
    if (door.isClosed()) {
      door.setState(new Locked(door));
    } else {
      log.warn("Cannot lock door {} because it is open", door.getId());
    }
  }

  @Override
  public void unlock() {
    door.setState(new Unlocked(door));
  }

  @Override
  public void unlocked_shortly() {
    log.warn("Door {} is already unlocked", door.getId());
  }
}
