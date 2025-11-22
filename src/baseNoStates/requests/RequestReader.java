package baseNoStates.requests;

import baseNoStates.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;



public class RequestReader implements Request {
  private final String credential;
  private final String action;
  private final LocalDateTime now;
  private final String doorId;

  private String userName;
  private boolean authorized;
  private final ArrayList<String> reasons;
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


  public void process() {
    User user = DirectoryUsers.findUserByCredential(credential);
    Door door = DirectoryDoors.findDoorById(doorId);

    assert door != null : "door " + doorId + " not found";

    authorize(user, door);

    door.processRequest(this);

    doorClosed = door.isClosed();
  }

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

    Shedule schedule = userGroup.getShedule();
    LocalDateTime now = Clock.getInstance().getDate();
    LocalDate currentDate = now.toLocalDate();
    LocalTime currentTime = now.toLocalTime();
    DayOfWeek currentDay = now.getDayOfWeek();

    if (schedule.getValidDays() == null || !schedule.getValidDays().contains(currentDay)) {
      authorized = false;
      addReason("Not a valid day for this group");
      return;
    }

    if (currentDate.isBefore(schedule.getStartDate()) || currentDate.isAfter(schedule.getEndDate())) {
      authorized = false;
      addReason("Date not within schedule");
      return;
    }

    if (currentTime.isBefore(schedule.getStartHour()) || currentTime.isAfter(schedule.getEndHour())) {
      authorized = false;
      addReason("Time not within schedule");
      return;
    }

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

    if (!userGroup.getAllowedActions().contains(action)) {
      authorized = false;
      addReason("Action not allowed for this group");
      return;
    }

    authorized = true;
  }

  public String getUserName() {
    return userName;
  }

  public String getCredential() {
    return credential;
  }

  public LocalDateTime getNow() {
    return now;
  }

  public String getDoorId() {
    return doorId;
  }

  public ArrayList<String> getReasons() {
    return reasons;
  }
}
