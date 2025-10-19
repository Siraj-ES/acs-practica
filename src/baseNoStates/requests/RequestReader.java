package baseNoStates.requests;

import baseNoStates.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public class RequestReader implements Request {
  private final String credential; // who
  private final String action;     // what
  private final LocalDateTime now; // when
  private final String doorId;     // where
  private String userName;
  private boolean authorized;
  private final ArrayList<String> reasons; // why not authorized
  private String doorStateName;
  private boolean doorClosed;

  public RequestReader(String credential, String action, LocalDateTime now, String doorId) {
    this.credential = credential;
    this.action = action;
    this.doorId = doorId;
    reasons = new ArrayList<>();
    this.now = now;
  }

  public void setDoorStateName(String name) {
    doorStateName = name;
  }

  public String getAction() {
    return action;
  }

  public boolean isAuthorized() {
    return authorized;
  }

  public void addReason(String reason) {
    reasons.add(reason);
  }


  @Override
  public String toString() {
    if (userName == null) {
      userName = "unknown";
    }
    return "Request{"
        + "credential=" + credential
        + ", userName=" + userName
        + ", action=" + action
        + ", now=" + now
        + ", doorID=" + doorId
        + ", closed=" + doorClosed
        + ", authorized=" + authorized
        + ", reasons=" + reasons
        + "}";
  }

  public JSONObject answerToJson() {
    JSONObject json = new JSONObject();
    json.put("authorized", authorized);
    json.put("action", action);
    json.put("doorId", doorId);
    json.put("closed", doorClosed);
    json.put("state", doorStateName);
    json.put("reasons", new JSONArray(reasons));
    return json;
  }

  // see if the request is authorized and put this into the request, then send it to the door.
  // if authorized, perform the action.
  public void process() {
    User user = DirectoryUsers.findUserByCredential(credential);
    Door door = DirectoryDoors.findDoorById(doorId);
    assert door != null : "door " + doorId + " not found";
    authorize(user, door);
    // this sets the boolean authorize attribute of the request
    door.processRequest(this);
    // even if not authorized we process the request, so that if desired we could log all
    // the requests made to the server as part of processing the request
    doorClosed = door.isClosed();
  }

  // the result is put into the request object plus, if not authorized, why not,
  // only for testing
  private void authorize(User user, Door door) {
    if (user == null) {
      authorized = false;
      addReason("User doesn't exist");
      return;
    }

    UserGroup userGroup = user.getUserGroup();
    if (userGroup == null) {
      authorized = false;
      addReason("User has no group assigned");
      return;
    }
    // Get schedule info
    Shedule schedule = userGroup.getShedule();
    LocalDateTime now = Clock.getInstance().getDate();
    LocalDate currentDate = now.toLocalDate();
    LocalTime currentTime = now.toLocalTime();
    DayOfWeek currentDay = now.getDayOfWeek();

    //Check valid day
    if (schedule.getValidDays() == null || !schedule.getValidDays().contains(currentDay)) {
      authorized = false;
      addReason("Not a valid day for this group");
      return;
    }
    //Check date range
    if (currentDate.isBefore(schedule.getStartDate()) || currentDate.isAfter(schedule.getEndDate())) {
      authorized = false;
      addReason("Date not within schedule");
      return;
    }

    //Check hour range
    if (currentTime.isBefore(schedule.getStartHour()) || currentTime.isAfter(schedule.getEndHour())) {
      authorized = false;
      addReason("Time not within schedule");
      return;
    }

    // Check door access
    boolean doorAllowed = false;
    for (Area area : userGroup.getAllowedAreas()) {
      if (area.getDoorsGivingAccess().contains(door)) {
        doorAllowed = true;
        break;
      }
    }
    if (!doorAllowed) {
      authorized = false;
      addReason("Door not allowed for this group");
      return;
    }

    // Check allowed action
    if (!userGroup.getAllowedActions().contains(action)) {
      authorized = false;
      addReason("Action not allowed for this group");
      return;
    }


    authorized = true;
  }
}
