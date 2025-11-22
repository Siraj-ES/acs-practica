package baseNoStates;

// Before executing enable assertions :
// https://se-education.org/guides/tutorials/intellijUsefulSettings.html

public class Main {
  static void main(String[] args) {
    // Make a clock and start it.
    Clock clock = Clock.getInstance();
    clock.start();
    DirectoryDoors.makeDoors(clock);
    DirectoryAreas.makeAllAreas();
    DirectoryUsers.makeUsers();

    new WebServer();
  }
}
