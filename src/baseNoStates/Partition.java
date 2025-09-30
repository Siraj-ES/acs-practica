package baseNoStates;

import java.util.ArrayList;
import java.util.List;

class Partition extends Area {
  private ArrayList<Area> children;

  public Partition(String id) {
    super(id);
    this.children = new ArrayList<Area>();
  }

  @Override
  public void addArea(Area area) {
    children.add(area);
  }


  //Aqui el que faig es crear un array doorsArea on ficaré les portes de cada area dins de cada partition.
  //Si dins d'un Partition hi ha un space, es cridará a la funció getDoorsGivingAcces de la clase Space.
  //En canvi si es un altre Partition es tornará a cridar aquesta mateixa funció fins que trobi un Space
  @Override
  public List<Door> getDoorsGivingAccess() {
    ArrayList<Door> doorsArea = new ArrayList<>();
    for (Area child : children) {
      doorsArea.addAll(child.getDoorsGivingAccess());

    }
    return doorsArea;
  }
}


