package com.google.sps.servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

/** Handles request sent to /funny */
@WebServlet("/funny")
public class FunnyServlet extends HttpServlet {
  private static final ArrayList<String> jokeArray = new ArrayList<String>();
  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    if(jokeArray.isEmpty()) {
        addJokes();
    }
    String json = toGson(jokeArray);
    response.setContentType("application/json;");
    response.getWriter().println(json);
  }
  /**
   * potentially useless, but will be kept for now
   * @param joke joke to be added
   */
  private void addJoke(String joke) {
    jokeArray.add(joke);
  }
  /**
   * Adds jokes, maybe later expansion to make it add them from an array?
   */
  private void addJokes() {
      addJoke("Why did the chicken cross the road? To cross over.");
      addJoke("Never play chess with a British person. Their queen never dies.");
      addJoke("The Scottish military has eliminated the draft by issuing pants to all its officers.");
      addJoke("The Scottish joke kilt me.");
      addJoke("My pony can't neigh because he's a little hoarse.");
      addJoke("If you don't know what an enigma is, and I don't know what an enigma is, then it's an absolute mystery.");
      addJoke("Pressing F5 is so refreshing.");
      addJoke("Oh. That moment when you realize who sang 'Take On Me'");
  }

  private String toGson(ArrayList<String> input) {
        Gson gson = new Gson();
        String json = gson.toJson(input);
        return json;
  }
}
