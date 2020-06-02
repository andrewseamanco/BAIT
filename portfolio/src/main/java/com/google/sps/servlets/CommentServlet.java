package com.google.sps.servlets;

import com.google.sps.data.Comment;
import com.google.sps.data.Conversation;
import com.google.gson.Gson;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/comments")
public class CommentServlet extends HttpServlet {

  private Conversation conversation = new Conversation();

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    response.setContentType("application/json");
    String json = new Gson().toJson(conversation);
    response.getWriter().println(json);
  }

  @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    // If the user sends another POST request after the game is over, then start a new game.
    String username = getUsername(request);
    String comment = getComment(request);

    Comment comment = new Comment(username, comment, false);

    converse.addComment(comment);

    response.sendRedirect("/community.html");
    }

    private String getUsername(HttpServletRequest request) {
        String username = request.getParameter("username");

        //Sanatize input here
        return username;
    }

    private String getComment(HttpServletRequest request) {
        String comment = request.getParameter("comment");

        //Sanatize input here
        return comment;
    }

}
