package baseNoStates.requests;

import baseNoStates.Actions;
import baseNoStates.Area;
import baseNoStates.DirectoryAreas;
import baseNoStates.Door;
import java.time.LocalDateTime;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;

/*
  This class represents an action request applied to an entire area
  (maybe a space or a partition). The access control system cannot act directly
  on an area, so RequestArea is responsible for converting this
  abstract request into multiple individual requests on each door that
  provides access to the spaces of the area.

  When a user requests to lock or unlock an area, this class:
  -locates the area in the model
  -obtains all the doors associated with it
  -creates a RequestReader for each door
  -processes each of these requests.
*/

public class RequestArea implements Request {
  private final String credential;
  private final String action;
  private final String areaId;
  private final LocalDateTime now;
  private final ArrayList<RequestReader> requests = new ArrayList<>();

  public RequestArea(String credential, String action, LocalDateTime now, String areaId) {
    this.credential = credential;
    this.areaId = areaId;

    assert action.equals(Actions.LOCK) || action.equals(Actions.UNLOCK)
        : "invalid action " + action + " for an area request";

    this.action = action;
    this.now = now;
  }

  @Override
  public JSONObject answerToJson() {
    JSONObject json = new JSONObject();
    json.put("action", action);
    json.put("areaId", areaId);

    // Convertim totes les respostes de les peticions individuals de porta a JSON
    JSONArray jsonRequests = new JSONArray();
    for (RequestReader rd : requests) {
      jsonRequests.put(rd.answerToJson());
    }
    json.put("requestsDoors", jsonRequests);

    return json;
  }

  @Override
  public String toString() {
    String requestsDoorsStr;
    if (requests.isEmpty()) {
      requestsDoorsStr = "";
    } else {
      requestsDoorsStr = requests.toString();
    }
    return "Request{"
        + "credential=" + credential
        + ", action=" + action
        + ", now=" + now
        + ", areaId=" + areaId
        + ", requestsDoors=" + requestsDoorsStr
        + "}";
  }



  public void process() {

    Area area = DirectoryAreas.findAreaById(areaId);

    if (area != null) {
      for (Door door : area.getDoorsGivingAccess()) {
        RequestReader requestReader = new RequestReader(credential, action, now, door.getId());
        requestReader.process();

        requests.add(requestReader);
      }
    }
  }

  public String getCredential() {
    return credential;
  }

  public String getAction() {
    return action;
  }

  public String getAreaId() {
    return areaId;
  }

  public LocalDateTime getNow() {
    return now;
  }

  public ArrayList<RequestReader> getRequests() {
    return requests;
  }
}
