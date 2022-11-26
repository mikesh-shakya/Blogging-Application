package com.mikesh.blog.services.implementation;

import com.mikesh.blog.entities.Category;
import com.mikesh.blog.entities.Post;
import com.mikesh.blog.entities.User;
import com.mikesh.blog.exceptions.ResourceNotFoundException;
import com.mikesh.blog.payloads.PostDTO;
import com.mikesh.blog.payloads.PostResponse;
import com.mikesh.blog.repositories.CategoryRepository;
import com.mikesh.blog.repositories.PostRepository;
import com.mikesh.blog.repositories.UserRepository;
import com.mikesh.blog.services.PostServices;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostServiceImplementation implements PostServices {

    @Autowired
    private PostRepository postRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public PostDTO createPost(PostDTO postDTO, Integer userId, Integer categoryId) {
        User user = userRepository.findById(userId)
                .orElseThrow(()-> new ResourceNotFoundException("User", "User Id", userId));
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "Category Id", categoryId));

        Post post = this.modelMapper.map(postDTO, Post.class);
        post.setImageName("default.png");
        post.setAddDate(new Date());
        post.setUser(user);
        post.setCategory(category);
        Post addedPost = this.postRepository.save(post);
        return this.modelMapper.map(addedPost, PostDTO.class);
    }

    @Override
    public PostResponse getAllPost(Integer pageNumber, Integer noOfPosts, String sortBy, String sortDirection) {
        Sort sort = (sortDirection.equalsIgnoreCase("dsc"))?Sort.by(sortBy).descending():Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(pageNumber, noOfPosts, sort);
        Page<Post> postPage = this.postRepository.findAll(pageable);
        List<Post> allPost = postPage.getContent();
        List<PostDTO> allPostDTO = allPost.stream().map((post) -> this.modelMapper.map(post, PostDTO.class)).collect(Collectors.toList());

        PostResponse postResponse = new PostResponse();
        postResponse.setContent(allPostDTO);
        postResponse.setPageNumber(postPage.getNumber());
        postResponse.setPageSize(postPage.getSize());
        postResponse.setTotalElements(postPage.getTotalElements());
        postResponse.setTotalPages(postPage.getTotalPages());
        postResponse.setLastPage(postPage.isLast());

        return postResponse;
    }

    @Override
    public PostResponse getAllPostByCategory(Integer pageNumber, Integer noOfPosts, Integer categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "Category Id", categoryId));

        Pageable pageable = PageRequest.of(pageNumber, noOfPosts);
        Page<Post> postPage = this.postRepository.findByCategory(category,pageable);
        List<Post> postByCategory = postPage.getContent();
        List<PostDTO> postByCategoryDTO = postByCategory.stream().map((post) -> this.modelMapper.map(post, PostDTO.class)).collect(Collectors.toList());

        PostResponse postResponse = new PostResponse();
        postResponse.setContent(postByCategoryDTO);
        postResponse.setPageNumber(postPage.getNumber());
        postResponse.setPageSize(postPage.getSize());
        postResponse.setTotalElements(postPage.getTotalElements());
        postResponse.setTotalPages(postPage.getTotalPages());
        postResponse.setLastPage(postPage.isLast());
        return postResponse;
    }

    @Override
    public PostResponse getAllPostByUser(Integer userId, Integer pageNumber, Integer noOfPosts, String sortBy, String sortDirection) {
        User user = userRepository.findById(userId)
                .orElseThrow(()-> new ResourceNotFoundException("User", "User Id", userId));
        Sort sort = (sortDirection.equalsIgnoreCase("dsc"))?Sort.by(sortBy).descending():Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(pageNumber, noOfPosts, sort);
        Page<Post> postPage = this.postRepository.findByUser(user, pageable);
        List<Post> postByUser = postPage.getContent();
        List<PostDTO> postByUserDTO = postByUser.stream().map(post -> this.modelMapper.map(post, PostDTO.class)).collect(Collectors.toList());

        PostResponse postResponse = new PostResponse();
        postResponse.setContent(postByUserDTO);
        postResponse.setPageNumber(postPage.getNumber());
        postResponse.setPageSize(postPage.getSize());
        postResponse.setTotalElements(postPage.getTotalElements());
        postResponse.setTotalPages(postPage.getTotalPages());
        postResponse.setLastPage(postPage.isLast());
        return postResponse;
    }

    @Override
    public PostDTO getPostById(Integer postId) {
        Optional<Post> post = this.postRepository.findById(postId);
        return this.modelMapper.map(post, PostDTO.class);
    }

    @Override
    public PostDTO updatePost(PostDTO postDTO, Integer postId) {
        Post post = postRepository.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post", "Post Id", postId));

        post.setTitle(postDTO.getTitle());
        post.setDescription(postDTO.getDescription());
        post.setImageName(postDTO.getImageName());
        post.setAddDate(postDTO.getAddDate());
        post.setCategory(this.modelMapper.map(postDTO.getCategory(), Category.class));
        Post updatedPost = postRepository.save(post);

        return this.modelMapper.map(updatedPost, PostDTO.class);
    }

    @Override
    public void deletePost(Integer postId) {
        Post post = postRepository.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post", "Post Id", postId));
        this.postRepository.deleteById(postId);
    }

    @Override
    public PostResponse searchPosts(String keyword,Integer pageNumber, Integer noOfPosts) {
        Pageable pageable = PageRequest.of(pageNumber, noOfPosts);
        Page<Post> postPage = this.postRepository.findByTitleContaining(keyword, pageable);
        List<Post> searchedPosts = postPage.getContent();
        List<PostDTO> searchedPostsDTO = searchedPosts.stream().map(post -> this.modelMapper.map(post, PostDTO.class)).collect(Collectors.toList());

        PostResponse postResponse = new PostResponse();
        postResponse.setContent(searchedPostsDTO);
        postResponse.setPageNumber(postPage.getNumber());
        postResponse.setPageSize(postPage.getSize());
        postResponse.setTotalElements(postPage.getTotalElements());
        postResponse.setTotalPages(postPage.getTotalPages());
        postResponse.setLastPage(postPage.isLast());
        return postResponse;
    }
}
