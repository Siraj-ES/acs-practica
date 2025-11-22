package baseNoStates;

import java.util.ArrayList;
import java.util.List;

public abstract class Observable {

  private final List<Observer> observers = new ArrayList<>();

  public void notifyObservers() {
    // Make a copy of observers because removing observers while iterating
    // can cause a ConcurrentModificationException.
    List<Observer> copy = new ArrayList<>(observers);
    for (Observer o : copy) {
      o.update();
    }
  }

  public void addObserver(Observer observer) {
    observers.add(observer);
  }

  public void removeObserver(Observer observer) {
    observers.remove(observer);
  }
}
