package com.contractManagement.repository;

import com.contractManagement.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ImageRepo extends JpaRepository<Image, Long> {
	Optional<Image> findByName(String name);
}
