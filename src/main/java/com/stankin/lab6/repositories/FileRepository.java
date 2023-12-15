package com.stankin.lab6.repositories;

import com.stankin.lab6.models.File;
import com.stankin.lab6.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FileRepository extends JpaRepository<File, Long> {
    List<File> findAllByUserId(Long userId);
}
