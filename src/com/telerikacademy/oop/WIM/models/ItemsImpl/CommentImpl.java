package com.telerikacademy.oop.WIM.models.ItemsImpl;

import com.telerikacademy.oop.WIM.models.common.Validator;
import com.telerikacademy.oop.WIM.models.contracts.items.Comment;

import static com.telerikacademy.oop.WIM.models.common.Constants.*;
import static com.telerikacademy.oop.WIM.models.common.Utils.capitaliseFirst;

public class CommentImpl implements Comment {

    private String content;
    private String author;

    public CommentImpl(String content, String author) {
        setContent(content);
        setAuthor(author);
    }

    @Override
    public String getContent() {
        return content;
    }

    @Override
    public String getAuthor() {
        return author;
    }

    @Override
    public String toString() {
        return String.format(
                "%s  wrote: '%s'%n",
                capitaliseFirst(getAuthor()),
                getContent());
    }

    //private
    private void setContent(String content) {
        Validator.validateNotNull(content, COMMENT_CONTEND);
        this.content = Validator.trimmedStringInBounds(content,
                COMMENT_CONTEND,
                CONTENT_MIN_SIZE,
                CONTENT_MAX_SIZE);
    }

    private void setAuthor(String author) {
        Validator.validateNotNull(author, COMMENT_AUTHOR);
        this.author = Validator.trimmedStringInBounds(author,
                COMMENT_AUTHOR,
                USERNAME_MIN_SIZE,
                USERNAME_MAX_SIZE);
    }
}
