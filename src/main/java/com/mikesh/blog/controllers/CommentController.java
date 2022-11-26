package com.mikesh.blog.controllers;

import com.mikesh.blog.payloads.APIResponse;
import com.mikesh.blog.payloads.CommentDTO;
import com.mikesh.blog.services.CommentServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class CommentController {

    @Autowired
    private CommentServices commentServices;

    @PostMapping("/post/{postId}/comments")
    public ResponseEntity<CommentDTO> createComment(@RequestBody CommentDTO commentDTO,
                                                    @PathVariable Integer postId){
        CommentDTO createdComment = this.commentServices.createComment(commentDTO,postId);
        return new ResponseEntity<>(createdComment, HttpStatus.CREATED);
    }

    @PutMapping("/comments/{commentId}")
    public ResponseEntity<CommentDTO> updateComment(@RequestBody CommentDTO commentDTO,
                                                    @PathVariable Integer commentId){
        CommentDTO updatedComment = this.commentServices.updateComment(commentDTO,commentId);
        return new ResponseEntity<>(updatedComment,HttpStatus.OK);
    }

    @DeleteMapping("comments/{commentId}")
    public ResponseEntity<APIResponse> deleteComment (@PathVariable Integer commentId){
        this.commentServices.deleteComment(commentId);
        return new ResponseEntity<>(new APIResponse("Comment Deleted Successfully...", true), HttpStatus.OK);
    }
}
