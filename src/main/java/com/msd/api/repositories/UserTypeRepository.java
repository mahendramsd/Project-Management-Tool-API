package com.msd.api.repositories;

import com.msd.api.domain.UserType;
import com.msd.api.dto.response.UserResponse;
import com.msd.api.util.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserTypeRepository extends JpaRepository<UserType,Long> {

}
