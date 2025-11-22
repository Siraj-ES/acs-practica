package baseNoStates;

import java.util.ArrayList;
import java.util.List;

public class UserGroup {

  private final List<User> users;
  private final String name;
  private final List<Area> allowedAreas;
  private final List<String> allowedActions;
  private final Shedule shedule;

  // Constructor if we want to initialize a group without users.
  public UserGroup(String name,
                   Shedule shedule,
                   List<Area> allowedAreas,
                   List<String> allowedActions) {
    this.users = new ArrayList<>();
    this.name = name;
    this.shedule = shedule;
    this.allowedAreas = allowedAreas;
    this.allowedActions = allowedActions;
  }

  public List<String> getAllowedActions() {
    return allowedActions;
  }

  public List<Area> getAllowedAreas() {
    return allowedAreas;
  }

  public String getNameUserGroup() {
    return this.name;
  }

  public Shedule getShedule() {
    return this.shedule;
  }
}
