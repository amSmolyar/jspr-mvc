package ru.netology.repository;

import ru.netology.exception.NotFoundException;
import ru.netology.model.Post;

import java.util.*;

// Stub
public class PostRepository {
    private Map<Long, Post> postMap;
    private Long idCnt = (long) 1;

    public PostRepository() {
        postMap = new HashMap<>();
    }

    public List<Post> all() {
        List<Post> list = new ArrayList<>();
        for (Post post : postMap.values()) {
            if (!post.isRemoved())
                list.add(post);
        }
        return list;
    }

    public Optional<Post> getById(long id) {
        return (postMap.containsKey(id) && (!postMap.get(id).isRemoved())) ? Optional.of(postMap.get(id)) : Optional.empty();
    }

    public synchronized Optional<Post> save(Post post) {
        if ((post.getId() == 0) || (!postMap.containsKey(post.getId()))) {
            Post newPost = new Post(idCnt, post.getContent());
            postMap.put(idCnt, newPost);
            idCnt++;
            return Optional.of(newPost);
        }

        if (!postMap.get(post.getId()).isRemoved()) {
            postMap.put(post.getId(), post);
            return Optional.of(post);
        } else
            return Optional.empty();
    }

    public synchronized boolean removeById(long id) {
        if (postMap.containsKey(id)) {
            if (!postMap.get(id).isRemoved()) {
                postMap.get(id).setRemoved(true);
                return true;
            }
        }
        return false;
    }
}
