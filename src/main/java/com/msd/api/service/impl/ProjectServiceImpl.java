package com.msd.api.service.impl;

import com.msd.api.domain.Project;
import com.msd.api.dto.request.ProjectRequest;
import com.msd.api.dto.response.ProjectResponse;
import com.msd.api.repositories.ProjectRepository;
import com.msd.api.service.ProjectService;
import com.msd.api.util.Status;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProjectServiceImpl implements ProjectService {

    private static final Logger logger = LoggerFactory.getLogger(ProjectServiceImpl.class);

    private final ProjectRepository projectRepository;

    public ProjectServiceImpl(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @Override
    public ProjectResponse addProject(ProjectRequest projectRequest) {
        Project project = new Project();
        project.setName(projectRequest.getName());
        project.setCode(projectRequest.getCode());
        project.setStatus(Status.ACTIVE);
        projectRepository.save(project);
        project.setProjectId(project.getCode().concat("-").concat(project.getId().toString()));
        projectRepository.save(project);
        if (logger.isDebugEnabled())
            logger.debug("Project Created");
        return new ProjectResponse(project);
    }

    @Override
    public List<ProjectResponse> getAll() {
        return projectRepository.findAllByStatus(Status.ACTIVE).stream().map(ProjectResponse::new)
                .collect(Collectors.toList());
    }
}
