package com.mikesh.blog.services;

import com.mikesh.blog.payloads.CommentDTO;

public interface CommentServices {
    CommentDTO createComment(CommentDTO commentDTO, Integer postId);
    void deleteComment(Integer commentId);

    CommentDTO updateComment(CommentDTO commentDTO, Integer commentId);
}
