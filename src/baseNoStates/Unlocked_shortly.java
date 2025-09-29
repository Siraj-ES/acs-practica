package baseNoStates;

public class Unlocked_shortly extends DoorState{

    public Unlocked_shortly(Door door) {
      super(door);
    }

  @Override
  public String getName() {
    return "unlocked_shortly";
  }


  @Override
    public void open(){}

    @Override
    public void close(){}

    @Override
    public void lock(){}

    @Override
    public void unlock(){}

    @Override
    public void unlocked_shortly(){}

    @Override
    public void propped(){}
}
