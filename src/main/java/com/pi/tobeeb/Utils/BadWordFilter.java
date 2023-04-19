package com.pi.tobeeb.Utils;

import com.pi.tobeeb.Entities.Post;
import com.pi.tobeeb.Entities.Comment;


public class BadWordFilter {
    public static final String badWords =   "badword1,badword2,badword3";
    public static void filter(Post post) {
        String[] badWordsArray = badWords.split(",");
        for (String word : badWordsArray) {
            post.setNamePost(post.getNamePost().replaceAll(word, "***"));
            post.setContentPost(post.getContentPost().replaceAll(word, "***"));
        }
    }


        public static void filter(Comment comment) {
            String[] badWordsArray = badWords.split(",");
            for (String word : badWordsArray) {
                comment.setContent(comment.getContent().replaceAll(word, "***"));
            }
        }
}