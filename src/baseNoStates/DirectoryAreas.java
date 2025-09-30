package baseNoStates;


import java.util.ArrayList;
import java.util.Arrays;


public class DirectoryAreas {
  private String id;
  private static ArrayList<Area> allAreas;

  public DirectoryAreas(String id, ArrayList<Area> allAreas) {
    this.id = id;
    this.allAreas = allAreas;
  }


  public static void makeAllAreas() {
    Area parking = new Space("parking");
    Area stairs = new Space("stairs");
    Area room1 = new Space("room1");
    Area room2 = new Space("room2");
    Area hall = new Space("hall");
    Area restroom = new Space("rest room");
    Area building = new Partition("building");
    Area exterior = new Partition("exterior");
    Area room3 = new Space("room3");
    Area IT = new Space("IT");
    Area corridor = new Space("corridor");
    Area basement = new Partition("basement");
    Area groundFloor = new Partition("ground_floor");
    Area floor1 = new Partition("floor1");

    allAreas = new ArrayList<>(Arrays.asList(parking, stairs, room1, room2, hall, restroom, building, exterior, room3, IT, corridor, basement, groundFloor, floor1));

    building.addArea(groundFloor);
    building.addArea(floor1);
    building.addArea(basement);


    basement.addArea(parking);

    groundFloor.addArea(room1);
    groundFloor.addArea(room2);
    groundFloor.addArea(hall);

    floor1.addArea(room3);
    floor1.addArea(corridor);
    floor1.addArea(IT);
  }


  public static Area findAreaById(String areaId) {
    for (Area area : allAreas) {
      if (area.getId().equals(areaId)) {
        return area;
      }

    }
    return null;
  }

}
