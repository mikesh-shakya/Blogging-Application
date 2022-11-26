package com.mikesh.blog.services;

import com.mikesh.blog.payloads.PostDTO;
import com.mikesh.blog.payloads.PostResponse;

import java.util.List;

public interface PostServices {

    // Create
    PostDTO createPost(PostDTO postDTO, Integer userId, Integer categoryId);

    // Read
    PostResponse getAllPost(Integer pageNumber, Integer noOfPosts, String sortBy, String sortDirection);
    PostResponse getAllPostByCategory(Integer categoryId, Integer pageNumber, Integer noOfPosts);
    PostResponse getAllPostByUser(Integer userId, Integer pageNumber, Integer noOfPosts,String sortBy, String sortDirection);
    PostDTO getPostById(Integer postId);

    // Update
    PostDTO updatePost(PostDTO postDTO, Integer postId);

    //Delete
    void deletePost(Integer postId);

    // Search
    PostResponse searchPosts(String keyword, Integer pageNumber, Integer noOfPosts);


}
