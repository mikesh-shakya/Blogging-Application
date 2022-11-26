package com.mikesh.blog.services.implementation;

import com.mikesh.blog.entities.Comment;
import com.mikesh.blog.entities.Post;
import com.mikesh.blog.exceptions.ResourceNotFoundException;
import com.mikesh.blog.payloads.CommentDTO;
import com.mikesh.blog.repositories.CommentRepository;
import com.mikesh.blog.repositories.PostRepository;
import com.mikesh.blog.repositories.UserRepository;
import com.mikesh.blog.services.CommentServices;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImplementation implements CommentServices {

    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CommentDTO createComment(CommentDTO commentDTO, Integer postId) {

        Post post = this.postRepository.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post", "Post ID", postId));
        Comment comment = this.modelMapper.map(commentDTO, Comment.class);
        comment.setPost(post);
        Comment savedComment = this.commentRepository.save(comment);
        return this.modelMapper.map(savedComment, CommentDTO.class);
    }

    @Override
    public void deleteComment(Integer commentId) {

        Comment comment = this.commentRepository.findById(commentId).orElseThrow(()-> new ResourceNotFoundException("Comment", "Comment ID", commentId));
        this.commentRepository.delete(comment);
    }

    @Override
    public CommentDTO updateComment(CommentDTO commentDTO, Integer commentId) {

        Comment comment = this.commentRepository.findById(commentId).orElseThrow(()-> new ResourceNotFoundException("Comment", "Comment ID", commentId));
        comment.setCommentContent(commentDTO.getCommentContent());
        Comment updatedComment = this.commentRepository.save(comment);
        return this.modelMapper.map(updatedComment, CommentDTO.class);
    }
}
