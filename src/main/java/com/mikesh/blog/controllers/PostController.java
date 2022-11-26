package com.mikesh.blog.controllers;

import com.mikesh.blog.configuration.AppConstants;
import com.mikesh.blog.payloads.PostDTO;
import com.mikesh.blog.payloads.PostResponse;
import com.mikesh.blog.services.FileServices;
import com.mikesh.blog.services.PostServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping("/api")
public class PostController {
    @Autowired
    private PostServices postServices;

    @Autowired
    private FileServices fileServices;

    @Value("${project.image}")
    private String path;


    @GetMapping("/posts")
    public PostResponse getAllPosts(@RequestParam (value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
                                    @RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
                                    @RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY, required = false) String sortBy,
                                    @RequestParam(value = "sortDirection", defaultValue = AppConstants.SORT_DIRECTION, required = false) String sortDirection){
        return this.postServices.getAllPost(pageNumber,pageSize, sortBy, sortDirection);
    }

    @GetMapping("/user/{userId}/posts")
    public ResponseEntity<PostResponse> getPostByUser(@PathVariable Integer userId,
                                                       @RequestParam (value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
                                                       @RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
                                                      @RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY, required = false) String sortBy,
                                                      @RequestParam(value = "sortDirection", defaultValue = AppConstants.SORT_DIRECTION, required = false) String sortDirection){
        PostResponse getAllPostByUser = this.postServices.getAllPostByUser(userId, pageNumber, pageSize, sortBy, sortDirection );
        return new ResponseEntity<>(getAllPostByUser ,HttpStatus.OK);
    }

    @GetMapping("/category/{categoryId}/posts")
    public ResponseEntity<PostResponse> getPostByCategory(@PathVariable Integer categoryId,
                                                           @RequestParam (value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
                                                           @RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize){
        PostResponse getAllPostByCategory = this.postServices.getAllPostByCategory(pageNumber, pageSize, categoryId);
        return new ResponseEntity<>(getAllPostByCategory ,HttpStatus.OK);
    }

    @GetMapping("/posts/title/{keyword}")
    public ResponseEntity<PostResponse> searchedPosts(@PathVariable String keyword,
                                                      @RequestParam (value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
                                                       @RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize){
        PostResponse searchedPosts = this.postServices.searchPosts(keyword,pageNumber, pageSize);
        return new ResponseEntity<>(searchedPosts, HttpStatus.OK);
    }

    @GetMapping("/posts/{postId}")
    public ResponseEntity<PostDTO> getPostById(@PathVariable Integer postId){
        PostDTO getPostById= this.postServices.getPostById(postId);
        return new ResponseEntity<>(getPostById, HttpStatus.OK);
    }


    @PostMapping("/user/{userId}/category/{categoryId}/posts")
    public ResponseEntity<PostDTO> createPost(@RequestBody  PostDTO postDTO,
                                              @PathVariable Integer userId,
                                              @PathVariable Integer categoryId){

        PostDTO createPostDTO = this.postServices.createPost(postDTO,userId,categoryId);
        return  new ResponseEntity<>(createPostDTO, HttpStatus.CREATED);
    }

    @PutMapping("/posts/{postId}")
    public ResponseEntity<PostDTO> updatePost(@RequestBody PostDTO postDTO,
                                              @PathVariable Integer postId){
        PostDTO updatedPost = this.postServices.updatePost(postDTO, postId);
        return new ResponseEntity<>(updatedPost,HttpStatus.OK);
    }

    @DeleteMapping("/posts/{postId}")
    public void deletePost(@PathVariable Integer postId){
        this.postServices.deletePost(postId);
    }


    // Post image upload
    @PostMapping("/posts/image/upload/{postId}")
    public ResponseEntity<PostDTO> uploadPostImage(@RequestParam("image") MultipartFile image,
                                                         @PathVariable Integer postId) throws IOException {
        PostDTO postDTO = this.postServices.getPostById(postId);
        String fileName = this.fileServices.uploadImage(path,image);
        postDTO.setImageName(fileName);
        PostDTO updatedPost = this.postServices.updatePost(postDTO,postId);
        return new ResponseEntity<>(updatedPost,HttpStatus.OK);
    }

    // Get Post Image
    @GetMapping(value = "/post/image/{imageName}", produces = MediaType.IMAGE_JPEG_VALUE)
    public void getImage(@PathVariable("imageName") String imageName, HttpServletResponse response) throws IOException{

        InputStream resource = this.fileServices.getResource(path, imageName);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource, response.getOutputStream());
    }

}
