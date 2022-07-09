package com.survivingcodingbootcamp.blog.controller;

import com.survivingcodingbootcamp.blog.model.Hashtag;
import com.survivingcodingbootcamp.blog.model.Post;
import com.survivingcodingbootcamp.blog.model.Topic;
import com.survivingcodingbootcamp.blog.repository.HashtagRepository;
import com.survivingcodingbootcamp.blog.repository.PostRepository;
import com.survivingcodingbootcamp.blog.repository.TopicRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/posts")
public class PostController {
    private PostRepository postRepo;
    private HashtagRepository hashtagRepo;
    private TopicRepository topicRepo;

    public PostController(PostRepository postRepo,HashtagRepository hashtagRepo,TopicRepository topicRepo) {
        this.postRepo = postRepo;
        this.hashtagRepo = hashtagRepo;
        this.topicRepo = topicRepo;
    }

    @GetMapping("/{id}")
    public String displaySinglePost(@PathVariable long id, Model model) {
        model.addAttribute("post", postRepo.findById(id).get());
        return "single-post-template";
    }

    @PostMapping("/{id}/addHashtag")
    public String addHashtag(@PathVariable long id, @RequestParam String hashtag) {
        Post post = postRepo.findById(id).get();
        postRepo.save(post);

        Optional<Hashtag> hashtag1 = hashtagRepo.findByNameIgnoreCase(hashtag);
        if (hashtag1.isPresent()) {
            hashtag1.get().addPost(post);
            hashtagRepo.save(hashtag1.get());
        } else {
            Hashtag hashtag2 = new Hashtag(hashtag);
            hashtag2.addPost(post);
            hashtagRepo.save(hashtag2);
        }
        return "redirect:/posts/" + id;
    }

    @PostMapping("{id}/addnewpost")
    public String addNewPost(@PathVariable long id, @RequestParam String title, @RequestParam String author, @RequestParam String content) {
        Optional<Topic> tempTopic = topicRepo.findById(id);
        Optional<Post> newPost = postRepo.findByTitleIgnoreCase(title);

        if (tempTopic.isPresent()) {
            Post tempPost;
            if (newPost.isPresent()) {
                tempPost = newPost.get();
            } else {
                tempPost = new Post(title, tempTopic.get(), content, author);
            }
            postRepo.save(tempPost);
        }
        return "redirect:/topics/" + id;
    }
}
