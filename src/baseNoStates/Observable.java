package baseNoStates;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;


public abstract class Observable {
    private List<Observer> observers = new ArrayList<Observer>();


    public void notifyObservers() {
        //Make a copy of list of observers because of an error when we remove an observer
        //in the list of observers while we are iterating in list observers.
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

    public List<Observer> getObservers() {
      return observers;
    }

}
