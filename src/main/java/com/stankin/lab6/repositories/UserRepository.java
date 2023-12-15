package com.stankin.lab6.repositories;

import com.stankin.lab6.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
     default User getCurrentUser() {
        User user = new User("username_test");
        user.setId(1L);
        this.save(user);

        return user;
    }
}