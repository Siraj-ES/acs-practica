package baseNoStates;

import java.util.ArrayList;

public final class DirectoryUsers {
  private static final ArrayList<User> users = new ArrayList<>();

  public static void makeUsers() {

    // users without any privilege, just to keep temporally users instead of deleting them,
    // this is to withdraw all permissions but still to keep user data to give back
    // permissions later

    DirectoryUserGroups.makeUserGroups();

    UserGroup groupBlank = DirectoryUserGroups.findUserGroupByName("Group Blank");
    User user1 = new User("Bernat", "12345");
    User user2 = new User("Blai", "77532");
    user1.setUserGroup(groupBlank);
    user2.setUserGroup(groupBlank);

    users.add(user1);
    users.add(user2);


    // employees :
    // Sep. 1 this year to Mar. 1 next year
    // week days 9-17h
    // just shortly unlock
    // ground floor, floor1, exterior, stairs (this, for all), that is, everywhere but the parking
    UserGroup employees = DirectoryUserGroups.findUserGroupByName("Employees");
    User user3 = new User("Ernest", "74984");
    User user4 = new User("Eulalia", "43295");
    user3.setUserGroup(employees);
    user4.setUserGroup(employees);

    users.add(user3);
    users.add(user4);


    // managers :
    // Sep. 1 this year to Mar. 1 next year
    // week days + saturday, 8-20h
    // all actions
    // all spaces
    UserGroup managers = DirectoryUserGroups.findUserGroupByName("Managers");
    User user5 = new User("Manel", "95783");
    User user6 = new User("Marta", "05827");
    user5.setUserGroup(managers);
    user6.setUserGroup(managers);

    users.add(user5);
    users.add(user6);


    // admin :
    // always=Jan. 1 this year to 2100
    // all days of the week
    // all actions
    // all spaces
    UserGroup admin = DirectoryUserGroups.findUserGroupByName("Admins");
    User user7 = new User("Ana", "11343");
    user7.setUserGroup(admin);

    users.add(user7);
  }

  public static User findUserByCredential(String credential) {
    for (User user : users) {
      if (user.getCredential().equals(credential)) {
        return user;
      }
    }
    System.out.println("user with credential " + credential + " not found");
    return null; // otherwise we get a Java error
  }

}
