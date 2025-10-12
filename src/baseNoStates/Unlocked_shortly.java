package baseNoStates;

public class Unlocked_shortly extends DoorState implements Observer {
    private int sec;
    private Clock clock;

    public Unlocked_shortly(Door door, Clock clock) {
      super(door);
      this.clock = clock;
      this.clock.addObserver(this);
      this.sec = 0;
    }

  @Override
  public String getName() {
    return "unlocked_shortly";
  }


  @Override
    public void open(){
      door.setClosed(false);
      System.out.println("Opening the door...");
  }

    @Override
    public void close(){
      door.setClosed(true);
      System.out.println("Closing the door");
    }

    @Override
    public void lock(){}

    @Override
    public void unlock(){}

    @Override
    public void unlocked_shortly(){}

    @Override
    public void propped(){}

    @Override
    public void update(){
        this.sec++;
        if(sec > 9) {
          if (door.isClosed()) {
            door.setState(new Locked(door));
            System.out.println("10 sec have passed and the door was closed so Locking...");
          }
          else{
            door.setState(new Propped(door));
            System.out.println("10 sec have passed and the door was opened so Propped...");
          }
          clock.removeObserver(this);

        }
    }
}
