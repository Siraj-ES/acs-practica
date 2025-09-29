package baseNoStates;



public class Propped extends DoorState{
    public Propped(Door door) {
      super(door);
    }

    @Override
    public String getName() {
      return "propped";
    }

    @Override
    public void open() {
      System.out.println("Door is already opened");
    }

    @Override
    public void close() {
      door.setClosed(true);
    }

    @Override
    public void lock() {
      door.setState(new Locked(door));
    }

    @Override
    public void unlock() {}

    @Override
    public void unlocked_shortly() {}

    @Override
    public void propped() {}


}
