package baseNoStates;

import java.util.ArrayList;
import java.util.List;

public abstract class Area {
  private String id;


  public Area(String id) {
    this.id = id;
  }
  public String getId() {
    return id;
  }

  public abstract List<Door> getDoorsGivingAccess();
}

