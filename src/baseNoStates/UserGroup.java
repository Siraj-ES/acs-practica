package baseNoStates;

import java.util.ArrayList;
import java.util.List;
public class UserGroup {
    List<User> users = new ArrayList<User>();
    String name;
    List<Area> allowedAreas = new ArrayList<>();
    List<String> allowedActions = new ArrayList<>();
    Shedule shedule;

    //Constructor if we want to inicializate a group without users.
    public UserGroup(String name, Shedule shedule, List<Area> allowedAreas, List<String> allowedActions) {
      this.users = new ArrayList<>();
      this.name = name;
      this.shedule = shedule;
      this.allowedAreas = allowedAreas;
      this.allowedActions = allowedActions;
    }


    public void addUser(User u){
      users.add(u);
    }

    public void addAllowedArea(Area a){
      allowedAreas.add(a);
    }

    public List<String> getAllowedActions(){
      return allowedActions;
    }

    public List<Area> getAllowedAreas(){
      return allowedAreas;
    }


    public void removeUser(User u){
      users.remove(u);
    }

    public List<User> getUsers() {
      return users;
    }

    public String getNameUserGroup(){
      return this.name;
    }

    public Shedule getShedule() {
      return this.shedule;
    }








}
