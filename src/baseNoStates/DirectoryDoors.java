package baseNoStates;

import java.util.ArrayList;
import java.util.Arrays;

public final class DirectoryDoors {
  private static ArrayList<Door> allDoors;


  //Now all doors have a clock.
  public static void makeDoors(Clock clock) {

    // basement
    Door d1 = new Door("D1",  "exterior", "parking", clock); // exterior, parking
    Door d2 = new Door("D2",  "stairs", "parking", clock); // stairs, parking

    // ground floor
    Door d3 = new Door("D3",  "exterior", "hall", clock); // exterior, hall
    Door d4 = new Door("D4",  "stairs", "hall", clock); // stairs, hall
    Door d5 = new Door("D5",  "hall", "room1", clock); // hall, room1
    Door d6 = new Door("D6",  "hall", "room2", clock); // hall, room2

    // first floor
    Door d7 = new Door("D7",  "stairs", "corridor", clock); // stairs, corridor
    Door d8 = new Door("D8",  "corridor", "room3", clock); // corridor, room3
    Door d9 = new Door("D9",  "corridor", "IT", clock); // corridor, IT

    allDoors = new ArrayList<>(Arrays.asList(d1, d2, d3, d4, d5, d6, d7, d8, d9));
  }

  public static Door findDoorById(String id) {
    for (Door door : allDoors) {
      if (door.getId().equals(id)) {
        return door;
      }
    }
    System.out.println("door with id " + id + " not found");
    return null; // otherwise we get a Java error
  }

  // this is needed by RequestRefresh
  public static ArrayList<Door> getAllDoors() {
    System.out.println(allDoors);
    return allDoors;
  }

  public static class DirectoryAreas {
  }
}
