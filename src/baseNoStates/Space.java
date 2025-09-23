package baseNoStates;

import java.util.ArrayList;
import java.util.List;


class Space extends Area {

  public Space(String id) {
    super(id);
  }

  //A door he implementat getters de from i to d'un space per a que en aquest metode si
  //el space que li pasem per parametre es igual al space que te com a to la porta, l'afegeixi
  //a l'array doorsArea i retornar totes les portes d'aquella mateixa area.
  @Override
  public List<Door> getDoorsGivingAccess() {
    ArrayList<Door> doorsArea = new ArrayList<>();
    for (Door door : DirectoryDoors.getAllDoors()) {
      if (this.getId().equals(door.getTo())){
        doorsArea.add(door);
      }
    }
    return doorsArea;

  }

}
