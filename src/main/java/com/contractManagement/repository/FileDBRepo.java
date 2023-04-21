package com.contractManagement.repository;

import com.contractManagement.entity.FileDB;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileDBRepo extends JpaRepository<FileDB, String> {
}
