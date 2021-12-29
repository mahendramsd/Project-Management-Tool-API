package com.msd.api.repositories;

import com.msd.api.domain.ChangeLog;
import com.msd.api.domain.Issue;
import com.msd.api.util.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChangeLogRepository extends JpaRepository<ChangeLog,Long> {

    List<ChangeLog> findByIssueAndStatusOrderByCreatedDateAsc(Issue issue, Status status);
}
