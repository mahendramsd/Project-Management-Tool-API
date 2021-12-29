package com.msd.api.service;

import com.msd.api.dto.request.ProjectRequest;
import com.msd.api.dto.response.ProjectResponse;

import java.util.List;

public interface ProjectService {

    ProjectResponse addProject(ProjectRequest projectRequest);

    List<ProjectResponse> getAll();
}
