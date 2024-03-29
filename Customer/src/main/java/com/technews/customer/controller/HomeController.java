package com.technews.customer.controller;

import com.technews.library.dto.CategoryDto;
import com.technews.library.dto.PostDto;
import com.technews.library.model.Category;
import com.technews.library.model.Post;
import com.technews.library.service.CategoryService;
import com.technews.library.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private PostService postService;

    @Autowired
    private CategoryService categoryService;



    @GetMapping("/index")
    public String index(Model model){
        List<Category> categories = categoryService.findAll();
        List<PostDto> postDtos = postService.findALl();
        model.addAttribute("categories", categories);
        model.addAttribute("posts", postDtos);
        return "mainpage";
    }

    @GetMapping("/posts")
    public String posts(Model model){
        List<CategoryDto> categoryDtoList = categoryService.getCategoryAndPost();
        List<Post> posts = postService.getAllPosts();
        model.addAttribute("categories", categoryDtoList);
        model.addAttribute("posts", posts);
        return "mainpage";
    }

    @GetMapping("/find-post/{id}")
    public String findPostById(@PathVariable("id") Long id, Model model){
//        List<Category> categories = categoryService.findAll();
        Post post = postService.getPostById(id);
        Long categoryId = post.getCategory().getId();
        List<Category> cate = categoryService.findAll();
//        List<Post> posts = postService.getRelatedPosts(categoryId);
        model.addAttribute("post", post);
        model.addAttribute("cate", cate);
        return "articlepage";
    }

    @GetMapping("/posts-in-category/{id}")
    public String getPostsInCategory(@PathVariable("id") Long id, Model model){
        Category category = categoryService.findById(id);
        Long categoryId = category.getId();
        List<CategoryDto> categories = categoryService.getCategoryAndPost();
        List<Post> posts = postService.getPostsInCategory(categoryId);
        List<Category> cate = categoryService.findAll();

        model.addAttribute("cate", cate);
        model.addAttribute("category", category);
        model.addAttribute("categories", categories);
        model.addAttribute("posts", posts);
        return "posts-in-category";
    }




    @GetMapping("/post")
    public String post(Model model) {

        List<PostDto> postDtoList = postService.findALl();
        List<Category> cate = categoryService.findAll();
        model.addAttribute("cate", cate);
        model.addAttribute("title", "Manage Post");
        model.addAttribute("posts", postDtoList);
        model.addAttribute("size", postDtoList.size());
        return "result-post";
    }

    @GetMapping("/posts/{pageNo}")
    public String postsPage(@PathVariable("pageNo") int pageNo, Model model){
        Page<PostDto> posts = postService.pagePosts(pageNo);
        List<Category> cate = categoryService.findAll();
        model.addAttribute("cate", cate);
        model.addAttribute("title", "Manage Post");
        model.addAttribute("size", posts.getSize());
        model.addAttribute("totalPages", posts.getTotalPages());
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("posts", posts);
        return "result-post";
    }



    @GetMapping("/search-results/{pageNo}")
    public String searchPosts(@PathVariable("pageNo")int pageNo,
                              @RequestParam("keyword")String keyword,
                              Model model){

        Page<PostDto> posts = postService.searchPosts(pageNo, keyword);
        List<Category> cate = categoryService.findAll();
        model.addAttribute("cate", cate);
        model.addAttribute("title", "Search Result");
        model.addAttribute("posts", posts);
        model.addAttribute("keyword", keyword);
        model.addAttribute("size", posts.getSize());
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", posts.getTotalPages());
        return "result-post";
    }

}
