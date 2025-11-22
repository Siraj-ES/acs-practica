package baseNoStates;

public class FindAreaById implements Visitor {

  private Area area = null;
  private final String areaId;

  public FindAreaById(String areaId) {
    this.areaId = areaId;
  }

  @Override
  public void visitPartition(Partition p) {
    if (p.getId().equals(areaId)) {
      area = p;
    }
  }

  @Override
  public void visitSpace(Space s) {
    if (s.getId().equals(areaId)) {
      area = s;
    }
  }

  public Area getArea() {
    return area;
  }
}
