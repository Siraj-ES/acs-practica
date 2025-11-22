package baseNoStates;

import baseNoStates.requests.RequestReader;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
  Door is a class that represents all the doors of the building within the ACS system.
  Door is the central point where the State pattern is applied. Instead of directly deciding
  what an action like lock, unlock or unlocked_shortly implies, it delegates this
  behavior to the DoorState object. In this way, the behavior
  of the same action depends on the state in which the door is at each moment.

  The door keeps a reference to the single Clock of the system so that the states
  that depend on time such as Unlocked_shortly can know
  when they must end. This allows programming doors that only stay open
  for a limited time without the Door code having to directly manage
  the temporal details.
 */

public class Door {

  private static final Logger log = LoggerFactory.getLogger(Door.class);

  private final String id;
  private boolean closed;
  private DoorState state;
  // Removed because of checkstyle
  // private final String from;
  private final String to;
  private final Clock clock;

  public Door(String id, String from, String to, Clock clock) {
    this.id = id;
    closed = true;
    // this.from = from;
    this.to = to;
    this.state = new Unlocked(this);
    this.clock = clock;
  }

  public String getTo() {
    return to;
  }

  public void processRequest(RequestReader request) {
    // it is the Door that process the request because the door has and knows
    // its state, and if closed or open
    if (request.isAuthorized()) {
      String action = request.getAction();
      doAction(action);
    } else {
      log.warn("Request NOT authorized for door {}", id);
    }
    request.setDoorStateName(state.getName());
  }

  private void doAction(String action) {
    switch (action) {
      case Actions.OPEN:
        state.open();
        break;
      case Actions.CLOSE:
        state.close();
        break;
      case Actions.LOCK:
        state.lock();
        break;
      case Actions.UNLOCK:
        state.unlock();
        break;
      case Actions.UNLOCK_SHORTLY:
        state.unlocked_shortly();
        break;
      default:
        assert false : "Unknown action " + action;
        System.exit(-1);
    }
  }

  public boolean isClosed() {
    return closed;
  }

  public void setClosed(boolean closed) {
    this.closed = closed;
  }

  public String getId() {
    return id;
  }

  public String getStateName() {
    return state.getName();
  }

  public void setState(DoorState state) {
    this.state = state;
  }

  @Override
  public String toString() {
    return "Door{"
        + ", id='" + id + '\''
        + ", closed=" + closed
        + ", state=" + getStateName()
        + "}";
  }

  public JSONObject toJson() {
    JSONObject json = new JSONObject();
    json.put("id", id);
    json.put("state", getStateName());
    json.put("closed", closed);
    return json;
  }

  public Clock getClock() {
    return clock;
  }
}
