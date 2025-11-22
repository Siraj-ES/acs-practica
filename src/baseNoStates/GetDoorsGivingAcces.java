package baseNoStates;

import java.util.ArrayList;
import java.util.List;

public class GetDoorsGivingAcces implements Visitor {

  private final List<Door> doors = new ArrayList<>();

  @Override
  public void visitPartition(Partition p) {

  }

  @Override
  public void visitSpace(Space s) {
    for (Door d : DirectoryDoors.getAllDoors()) {
      if (s.getId().equals(d.getTo())) {
        doors.add(d);
      }
    }
  }

  public List<Door> getDoors() {
    return doors;
  }
}
