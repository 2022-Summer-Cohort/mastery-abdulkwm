package com.survivingcodingbootcamp.blog.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.ArrayList;
import java.util.Collection;

@Entity
public class Hashtag {
    @Id
    @GeneratedValue
    private long id;
    private String name;

    @ManyToMany
    Collection<Post> posts;

    public Hashtag(String name) {
        this.name = name;
        this.posts = new ArrayList<>();
    }

    public Hashtag() {
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void addPost(Post post) {
        posts.add(post);
    }

    public Collection<Post> getPosts() {
        return posts;
    }
}
