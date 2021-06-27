package com.telerikacademy.oop.WIM.models.contracts.actions;

import com.telerikacademy.oop.WIM.models.contracts.items.Comment;

import java.util.List;

public interface Commentable {

    List<Comment> getComments();

    String getCommentsToString();

    void addComment(Comment newComment);
}
