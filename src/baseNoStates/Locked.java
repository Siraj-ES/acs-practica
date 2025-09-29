package baseNoStates;

import java.util.Timer;
import java.util.TimerTask;


public class Locked extends DoorState {
    public Locked(Door door) {
      super(door);
    }


    @Override
    public String getName() {
      return "locked";
    }

    @Override
    public void open() {
      System.out.println("Have to unlock to open the door");
    }

    @Override
    public void close() {
      System.out.println("The door is already closed");
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

      door.setState(new Unlocked_shortly(door));
      Timer timer = new Timer();
      TimerTask task = new TimerTask() {
        int sec = 0;

        public void run() {
          sec++;
          System.out.println("There are " + (10 - sec) + " seconds left to lock de door");

          if (sec == 10) {
            if (door.isClosed()){
              door.setState(new Locked(door));
            }
            else{
              door.setState(new Propped(door));
            }
            timer.cancel();

          }

        }
      };
      timer.scheduleAtFixedRate(task, 0, 1000);

    }

    @Override
    public void propped() {}
}
