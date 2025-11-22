package baseNoStates;

import java.util.ArrayList;
import java.util.List;

public class Partition extends Area {
  private final ArrayList<Area> children;

  public Partition(String id) {
    super(id);
    this.children = new ArrayList<>();
  }

  @Override
  public void addArea(Area area) {
    children.add(area);
  }



  @Override
  public List<Door> getDoorsGivingAccess() {
    GetDoorsGivingAcces v = new GetDoorsGivingAcces();
    accept(v);
    return v.getDoors();
  }

  @Override
  public void accept(Visitor visitor) {
    visitor.visitPartition(this);
    for (Area child : children) {
      child.accept(visitor);
    }

  }

}


