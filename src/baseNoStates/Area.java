package baseNoStates;

import java.util.List;

public abstract class Area implements Visitable {
  private final String id;


  public Area(String id) {
    this.id = id;
  }

  public String getId() {
    return id;
  }

  public abstract void addArea(Area area);

  public abstract List<Door> getDoorsGivingAccess();

  @Override
  public abstract void accept(Visitor visitor);
}

