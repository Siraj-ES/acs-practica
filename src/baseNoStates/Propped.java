package baseNoStates;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Propped extends DoorState {

  private static final Logger log = LoggerFactory.getLogger(Propped.class);

  public Propped(Door door) {
    super(door);
  }

  @Override
  public String getName() {
    return "propped";
  }

  @Override
  public void open() {
    log.warn(
        "Door {} is already opened (PROPPED state)",
        door.getId()
    );
  }

  @Override
  public void close() {
    door.setClosed(true);
    door.setState(new Locked(door));
  }

  @Override
  public void lock() {
    door.setState(new Locked(door));
  }

  @Override
  public void unlock() {
    // No action in PROPPED state
  }

  @Override
  public void unlocked_shortly() {
    // No action in PROPPED state
  }
}
