package com.niiazov.coursemanagement.repositories;

import com.niiazov.coursemanagement.models.LessonMaterial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LessonMaterialRepository extends JpaRepository<LessonMaterial, Integer> {
}
