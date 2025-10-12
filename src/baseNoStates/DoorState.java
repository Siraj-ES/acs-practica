package baseNoStates;


//Clase abstracta que te com a fills els estats Locked, Unlocked, Unlocked_shortly i Propped.
//Cada clase filla haurà d'heretar tots els metodes d'aquesta clase.
public abstract class DoorState {
    protected Door door;
    protected String name;

    public DoorState(Door door) {
      this.door = door;
    }

    //Metode que retorna el name de l'estat per a que pugui funcionar el processRequest de door ja que
    //demana un parametre de tipus string.
    public abstract String getName();

    public abstract void open();
    public abstract void close();
    public abstract void lock();
    public abstract void unlock();
    public abstract void unlocked_shortly();
    public abstract void propped();



}
