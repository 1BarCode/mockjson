package com.mycompany.mockjson.post;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepo extends JpaRepository<Post, UUID> {

}
