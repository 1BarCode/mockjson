package com.mycompany.mockjson.post;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mycompany.mockjson.exception.ResourceNotFoundException;
import com.mycompany.mockjson.user.User;
import com.mycompany.mockjson.user.UserService;
import com.mycompany.mockjson.util.validation.Validation;

@Service
public class PostService {

    private static String guestUserId = "df043f72-0466-4fcc-9a15-111d50709f51";

    @Autowired
    private PostRepo postRepo;

    @Autowired
    private UserService userService;

    public Post createPost(Post post) throws IllegalArgumentException, ResourceNotFoundException {
        // get the user by id
        UUID userId = Validation.getUUIDFromString(guestUserId);
        User user = userService.getUserById(userId);

        // generate a slug from the title
        String slug = generateSlug(post.getTitle());
        post.setSlug(slug);

        // set associations
        user.addPost(post); // this also sets the user on the post entity

        return postRepo.save(post);
    }

    /**
     * Generate a slug from the title
     * 
     * @param title
     * @return a slug
     */
    public String generateSlug(String title) {
        // generate a slug from the title
        String slug = title.trim().toLowerCase().replaceAll("[^a-z0-9\\s]", "").replaceAll("\\s+", "-") + "-"
                + generateCustomUniqueHash();

        if (slug.length() > 255) {
            slug = slug.substring(0, 255);
        }

        return slug;
    }

    /**
     * Generate a custom 12-byte unique hash using timestamps
     * 
     * @return a custom 12-byte unique hash
     */
    private String generateCustomUniqueHash() {
        // generate a custom 12-byte unique hash using timestamps
        String uniqueHash = Long.toHexString(System.currentTimeMillis());
        return uniqueHash.substring(0, 12);

    }
}
