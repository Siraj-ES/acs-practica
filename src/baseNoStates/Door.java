package baseNoStates;

import baseNoStates.requests.RequestReader;
import org.json.JSONObject;

import java.util.Timer;
import java.util.TimerTask;

//He afegit atributs state, per saber l'estat de la porta(unlocked,locked, unlocked_shortly)
//També he afegit els atributs from i to per que la porta tingui la informació de quin space ve i a quin space dona.
public class Door {
  private final String id;
  private boolean closed; // physically
  private String state;
  private String from;
  private String to;


  public Door(String id, String state,  String from, String to) {
    this.id = id;
    this.state = state;
    closed = true;
    this.from = from;
    this.to = to;

  }


  public String getFrom() {
    return from;
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
      System.out.println("not authorized");
    }
    request.setDoorStateName(getStateName());
  }


  //Això s'ha de refactoritzar amb l'explicació del pdf de la sessió 1
  private void doAction(String action) {
    switch (action) {
      case Actions.OPEN:
        if (closed) {
          closed = false;
        } else {
          System.out.println("Can't open door " + id + " because it's already open");
        }
        break;
      case Actions.CLOSE:
        if (closed) {
          System.out.println("Can't close door " + id + " because it's already closed");
        } else {
          closed = true;
        }
        break;
      case Actions.LOCK:
        // TODO
        // fall through
        if(closed) {
          state = "locked";
        }
        break;

      case Actions.UNLOCK:
        // TODO
        // fall through
        if(closed) {
          closed = false;
        }
        state = "unlocked";
        break;

      case Actions.UNLOCK_SHORTLY:
        // TODO
        // fall through
        if (state.equals("locked")) {
          state = "unlocked_shortly";
          Timer timer = new Timer();
          TimerTask task = new TimerTask() {
            int sec = 0;

            public void run() {
              sec++;
              System.out.println("There are " + (10 - sec) + "seconds left to lock de door");
              if (sec == 10) {
                if (closed){
                  state = "locked";
                }
                else{
                  state = "propped";
                }
                timer.cancel();
                System.out.println("Locking...");
              }

            }
          };
          timer.scheduleAtFixedRate(task, 0, 1000);
        }
        else{
          state = "unlocked";
          System.out.println("Can't unlock door  " + id + " shortly because it's not closed or it's already unlocked");
        }

        break;
      default:
        assert false : "Unknown action " + action;
        System.exit(-1);
    }

  }

  public boolean isClosed() {
    return closed;
  }

  public String getId() {
    return id;
  }

  public String getStateName() {
    return state;
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
}
