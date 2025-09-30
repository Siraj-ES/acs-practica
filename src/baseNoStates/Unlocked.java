package baseNoStates;

public class Unlocked extends DoorState{


  public Unlocked(Door door) {
    super(door);
  }

  @Override
  public String getName() {
    return "unlocked";
  }


  @Override
  public void open() {
    if (door.isClosed()){
      door.setClosed(false);
    }
    else {
      System.out.println("Door is already opened");
    }
  }

  @Override
  public void close() {
    if (!door.isClosed()){
      door.setClosed(true);
    }
    else  {
      System.out.println("Door is already closed");
    }
  }

  @Override
  public void lock() {
    if(door.isClosed()){
      door.setState(new Locked(door));

    }
    else{
      System.out.println("You have to close the door to Lock it");
    }
  }

  @Override
  public void unlock() {
    door.setState(new Unlocked(door));

  }

  @Override
  public void unlocked_shortly() {
    System.out.println("The door is already unlocked");
  }

  @Override
  public void propped() {}
}
