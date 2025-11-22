package baseNoStates;

import baseNoStates.requests.Request;
import baseNoStates.requests.RequestArea;
import baseNoStates.requests.RequestReader;
import baseNoStates.requests.RequestRefresh;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.StringTokenizer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



// Based on
// https://www.ssaurel.com/blog/create-a-simple-http-web-server-in-java
// http://www.jcgonzalez.com/java-socket-mini-server-http-example

/*
  HTTP server that acts as a connector between the client (simulator)
  and the ACS model. It listens for GET requests on port 8080 and translates them
  into Request objects that can be RequestReader, RequestArea or RequestRefresh.

  Each incoming connection is handled in a SocketThread
  that reads the request line, manually parses the parameters, and
  builds the appropriate Request.
  Once the Request is created, the server delegates its processing to the process() method of the Request itself,
  converts the response to JSON using answerToJson(), and sends this JSON as the HTTP response.
 */

public class WebServer {
  private static final Logger log = LoggerFactory.getLogger(WebServer.class);

  private static final int PORT = 8080; // port to listen connection
  private static final DateTimeFormatter formatter =
      DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");

  public WebServer() {
    try {
      ServerSocket serverConnect = new ServerSocket(PORT);
      log.info("Server started. Listening for connections on port {} ...", PORT);
      // we listen until user halts server execution
      while (true) {
        // each client connection will be managed in a dedicated Thread
        new SocketThread(serverConnect.accept());
        // create dedicated thread to manage the client connection
      }
    } catch (IOException e) {
      log.error("Server Connection error : {}", e.getMessage());
    }
  }





  private static class SocketThread extends Thread {
    // as an inner class, SocketThread sees WebServer attributes
    private static final Logger log = LoggerFactory.getLogger(SocketThread.class);

    private final Socket insocked; // client connection via Socket class

    SocketThread(Socket insocket) {
      this.insocked = insocket;
      this.start();
    }

    @Override
    public void run() {
      // we manage our particular client connection
      BufferedReader in;
      PrintWriter out;
      String resource;

      try {
        // we read characters from the client via input stream on the socket
        in = new BufferedReader(new InputStreamReader(insocked.getInputStream()));
        // we get character output stream to client
        out = new PrintWriter(insocked.getOutputStream());
        // get first line of the request from the client
        String input = in.readLine();
        // we parse the request with a string tokenizer

        log.debug("socketthread : {}", input);

        StringTokenizer parse = new StringTokenizer(input);
        String method = parse.nextToken().toUpperCase(); // we get the HTTP method of the client
        if (!method.equals("GET")) {
          log.warn("501 Not Implemented : {} method.", method);
        } else {
          // what comes after "localhost:8080"
          resource = parse.nextToken();
          log.debug("input {}", input);
          log.debug("method {}", method);
          log.debug("resource {}", resource);

          parse = new StringTokenizer(resource, "/[?]=&");
          int i = 0;
          String[] tokens = new String[20]; // more than the actual number of parameters
          while (parse.hasMoreTokens()) {
            tokens[i] = parse.nextToken();
            log.debug("{} {}", i, tokens[i]);
            i++;
          }

          // Here is where we send the request and get the answer inside it
          Request request = makeRequest(tokens);
          if (request != null) {
            String typeRequest = tokens[0];
            log.info("created request {} {}", typeRequest, request);
            request.process();
            log.info("processed request {} {}", typeRequest, request);
            // Make the answer as a JSON string, to be sent to the Javascript client
            String answer = makeJsonAnswer(request);
            log.debug("answer\n{}", answer);
            // Here we send the response to the client
            out.println(answer);
            out.flush(); // flush character output stream buffer

            WebServer.logs(request);
          }
        }

        in.close();
        out.close();
        insocked.close(); // we close socket connection
      } catch (Exception e) {
        log.error("Exception : {}", e.toString());
      }
    }

    private Request makeRequest(String[] tokens) {
      // always return request because it contains the answer for the Javascript client
      log.debug("tokens : ");
      for (String token : tokens) {
        log.debug("{}, ", token);
      }
      log.debug("");

      Request request;
      // assertions below evaluated to false won't stop the webserver, just print an
      // assertion error, maybe because the webserver runs in a socked thread
      switch (tokens[0]) {
        case "refresh":
          request = new RequestRefresh();

          break;
        case "reader":
          request = makeRequestReader(tokens);
          break;
        case "area":
          request = makeRequestArea(tokens);

          break;
        case "get_children":
          // TODO: this is to be implemented when programming the mobile app in Flutter
          // in order to navigate the hierarchy of partitions, spaces and doors
          assert false : "request get_children is not yet implemented";
          request = null;
          System.exit(-1);
          break;
        default:
          // just in case we change the user interface or the simulator
          assert false : "unknown request " + tokens[0];
          request = null;
          System.exit(-1);
      }
      return request;
    }

    private RequestReader makeRequestReader(String[] tokens) {
      String credential = tokens[2];
      String action = tokens[4];
      LocalDateTime dateTime = LocalDateTime.parse(tokens[6], formatter);
      String doorId = tokens[8];
      return new RequestReader(credential, action, dateTime, doorId);
    }

    private RequestArea makeRequestArea(String[] tokens) {
      String credential = tokens[2];
      String action = tokens[4];
      LocalDateTime dateTime = LocalDateTime.parse(tokens[6], formatter);
      String areaId = tokens[8];
      return new RequestArea(credential, action, dateTime, areaId);
    }

    private String makeHeaderAnswer() {
      String answer = "";
      answer += "HTTP/1.0 200 OK\r\n";
      answer += "Content-type: application/json\r\n";
      answer += "Access-Control-Allow-Origin: *\r\n";
      // SUPERIMPORTANT to avoid the CORS problem :
      // "Cross-Origin Request Blocked: The Same Origin Policy disallows reading
      // the remote resource..."
      answer += "\r\n"; // blank line between headers and content, very important !
      return answer;
    }

    private String makeJsonAnswer(Request request) {
      String answer = makeHeaderAnswer();
      answer += request.answerToJson().toString();
      return answer;
    }

  }


  public static void logs(Request request) {

    if (request instanceof RequestRefresh) {
      log.info("refresh");
    }

    if (request instanceof RequestReader) {
      RequestReader rr = (RequestReader) request;

      log.info(
          "RequestReader\n"
              + "userName " + rr.getUserName() + "\n"
              + "action " + rr.getAction() + "\n"
              + "datetime " + rr.getNow() + "\n"
              + "doorId " + rr.getDoorId() + "\n"
              + "authorized " + rr.isAuthorized() + "\n"
              + "reasons " + rr.getReasons()
      );
    }

    if (request instanceof RequestArea) {
      RequestArea ra = (RequestArea) request;

      // usuari pel log (simple, sense comprovar res)
      User u = DirectoryUsers.findUserByCredential(ra.getCredential());
      String name = u.getName();

      // area està autoritzada si totes les portes ho estan
      boolean authorized = true;
      for (RequestReader rr : ra.getRequests()) {
        if (!rr.isAuthorized()) {
          authorized = false;
          break;
        }
      }

      // concatenar els motius tal com són (simple)
      ArrayList<String> all = new ArrayList<>();
      for (RequestReader rr : ra.getRequests()) {
        all.addAll(rr.getReasons());
      }

      log.info(
          "RequestArea\n"
              + "userName " + name + "\n"
              + " action " + ra.getAction() + "\n"
              + " datetime " + ra.getNow() + "\n"
              + "areaId " + ra.getAreaId() + "\n"
              + " authorized " + authorized + "\n"
              + " reasons " + all
      );
    }
  }


}
