package com.mycompany.mockjson.post;

import org.springframework.stereotype.Service;

@Service
public class PostService {

    public String generateSlug(String title) {
        // generate a slug from the title
        String slug = title.trim().toLowerCase().replaceAll("[^a-z0-9\\s]", "").replaceAll("\\s+", "-") + "-"
                + generateCustomUniqueHash();

        if (slug.length() > 255) {
            slug = slug.substring(0, 255);
        }

        return slug;
    }

    private String generateCustomUniqueHash() {
        // generate a custom 12-byte unique hash using timestamps
        String uniqueHash = Long.toHexString(System.currentTimeMillis());
        return uniqueHash.substring(0, 12);
    
    }
}
