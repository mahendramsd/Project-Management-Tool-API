package com.msd.api.repositories;

import com.msd.api.domain.Project;
import com.msd.api.util.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectRepository extends JpaRepository<Project,Long> {

    List<Project> findAllByStatus(Status active);

    Optional<Project> findByIdAndStatus(Long projectId, Status active);
}
