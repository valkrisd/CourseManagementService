package com.niiazov.coursemanagement.repositories;

import com.niiazov.coursemanagement.models.CourseMaterial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseMaterialRepository extends JpaRepository<CourseMaterial, Integer> {
}
