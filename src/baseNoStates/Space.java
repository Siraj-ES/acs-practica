package baseNoStates;

import java.util.ArrayList;
import java.util.List;


public class Space extends Area {

  public Space(String id) {
    super(id);
  }

  @Override
  public void addArea(Area area) {}

  //In the Door class, I implemented getters for the "from" and "to" spaces so that in this method,
  //if the space passed as a parameter is equal to the space that the door has as its “to” space,
  //it adds the door to the doorsArea array and returns all the doors from that same area.

  @Override
  public List<Door> getDoorsGivingAccess() {
    GetDoorsGivingAcces v = new GetDoorsGivingAcces();
    accept(v);
    return v.getDoors();

  }

  @Override
  public void accept(Visitor visitor) {
    visitor.visitSpace(this);
  }

}
