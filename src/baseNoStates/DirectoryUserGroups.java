package baseNoStates;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.time.*;
import java.util.Set;

public class DirectoryUserGroups {
  private static List<UserGroup> userGroups = new ArrayList<UserGroup>();

  public static void makeUserGroups(){
    DirectoryAreas.makeAllAreas();

    //Group Blank
    Shedule blankShedule = new Shedule(null,null,null,null,null);
    blankShedule.setValidDays(null);
    userGroups.add(new UserGroup("Group Blank", blankShedule, null, null));



    //Employees
    List<String> employeeActions = new ArrayList<>();
    employeeActions.add(Actions.OPEN);
    employeeActions.add(Actions.CLOSE);
    employeeActions.add(Actions.UNLOCK_SHORTLY);

    Area groundFloor = DirectoryAreas.findAreaById("ground_floor");
    Area floor1 = DirectoryAreas.findAreaById("floor1");
    Area exterior = DirectoryAreas.findAreaById("exterior");
    Area stairs = DirectoryAreas.findAreaById("stairs");

    List<Area> employeeAreas = new ArrayList<>();
    employeeAreas.add(groundFloor);
    employeeAreas.add(floor1);
    employeeAreas.add(exterior);
    employeeAreas.add(stairs);

    Set<DayOfWeek> validDaysE = new HashSet<DayOfWeek>();
    validDaysE.add(DayOfWeek.MONDAY);
    validDaysE.add(DayOfWeek.TUESDAY);
    validDaysE.add(DayOfWeek.WEDNESDAY);
    validDaysE.add(DayOfWeek.THURSDAY);
    validDaysE.add(DayOfWeek.FRIDAY);

    LocalTime startHour = LocalTime.of(9, 0);
    LocalTime endHour = LocalTime.of(17, 59);

    LocalDate startDate = LocalDate.of(2025,9,1);
    LocalDate endDate = LocalDate.of(2026,3,1);

    Shedule employeeShedule = new Shedule(validDaysE, startHour,endHour, startDate,endDate);
    userGroups.add(new UserGroup("Employees", employeeShedule, employeeAreas, employeeActions));


    //Managers
    List<String> managersActions = new ArrayList<>();
    managersActions.add(Actions.OPEN);
    managersActions.add(Actions.CLOSE);
    managersActions.add(Actions.LOCK);
    managersActions.add(Actions.UNLOCK);
    managersActions.add(Actions.UNLOCK_SHORTLY);


    Area parking = DirectoryAreas.findAreaById("parking");
    Area hall = DirectoryAreas.findAreaById("hall");
    Area building = DirectoryAreas.findAreaById("building");

    List<Area> managerAreas = new ArrayList<>();
    managerAreas.add(groundFloor);
    managerAreas.add(floor1);
    managerAreas.add(exterior);
    managerAreas.add(stairs);
    managerAreas.add(parking);
    managerAreas.add(hall);
    managerAreas.add(building);

    Set<DayOfWeek> validDaysM = new HashSet<DayOfWeek>();
    validDaysM.add(DayOfWeek.MONDAY);
    validDaysM.add(DayOfWeek.TUESDAY);
    validDaysM.add(DayOfWeek.WEDNESDAY);
    validDaysM.add(DayOfWeek.THURSDAY);
    validDaysM.add(DayOfWeek.FRIDAY);
    validDaysM.add(DayOfWeek.SATURDAY);

    LocalTime startHourManagers = LocalTime.of(8,0);
    LocalTime endHourManagers = LocalTime.of(20,0);
    Shedule managersShedule = new Shedule(validDaysM, startHourManagers,endHourManagers,startDate,endDate);
    userGroups.add(new UserGroup("Managers", managersShedule, managerAreas, managersActions));


    //Administrators
    List<String> adminActions = new ArrayList<>();
    adminActions.add(Actions.OPEN);
    adminActions.add(Actions.CLOSE);
    adminActions.add(Actions.LOCK);
    adminActions.add(Actions.UNLOCK);
    adminActions.add(Actions.UNLOCK_SHORTLY);



    Area basement = DirectoryAreas.findAreaById("basement");
    Area IT = DirectoryAreas.findAreaById("IT");
    Area corridor = DirectoryAreas.findAreaById("corridor");
    Area room1 = DirectoryAreas.findAreaById("room1");
    Area room2 = DirectoryAreas.findAreaById("room2");
    Area room3 = DirectoryAreas.findAreaById("room3");

    List<Area> adminAreas = new ArrayList<>();
    adminAreas.add(building);
    adminAreas.add(exterior);
    adminAreas.add(groundFloor);
    adminAreas.add(floor1);
    adminAreas.add(parking);
    adminAreas.add(stairs);
    adminAreas.add(basement);
    adminAreas.add(IT);
    adminAreas.add(corridor);
    adminAreas.add(room1);
    adminAreas.add(room2);
    adminAreas.add(room3);

    Set<DayOfWeek> validDaysA = new HashSet<DayOfWeek>();
    validDaysA.add(DayOfWeek.MONDAY);
    validDaysA.add(DayOfWeek.TUESDAY);
    validDaysA.add(DayOfWeek.WEDNESDAY);
    validDaysA.add(DayOfWeek.THURSDAY);
    validDaysA.add(DayOfWeek.FRIDAY);
    validDaysA.add(DayOfWeek.SATURDAY);
    validDaysA.add(DayOfWeek.SUNDAY);

    LocalTime startHourAdmins = LocalTime.of(0,0);
    LocalTime endHourAdmins = LocalTime.of(23,59);
    Shedule adminShedule = new Shedule(validDaysA, startHourAdmins,endHourAdmins,startDate,endDate);
    userGroups.add(new UserGroup("Admins", adminShedule, adminAreas, adminActions));
  }



  public static UserGroup findUserGroupByName(String userGroup) {
    for (UserGroup group : userGroups) {
      if (group.getNameUserGroup().equals(userGroup)) {
        return group;
      }
    }
    System.out.println("userGroup with name " + userGroup + " not found");
    return null; // otherwise we get a Java error
  }

}
