package baseNoStates;

public abstract class DoorState {

  protected final Door door;
  protected String name;

  public DoorState(Door door) {
    this.door = door;
  }

  public abstract String getName();

  public abstract void open();

  public abstract void close();

  public abstract void lock();

  public abstract void unlock();

  public abstract void unlocked_shortly();

  // Removed because of checkstyle
  // public abstract void propped();
}
