package com.msd.api.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.msd.api.domain.Project;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@EqualsAndHashCode
public class ProjectResponse implements Serializable {

    private Long id;
    private String projectId;
    private String name;
    private String code;

    public ProjectResponse(Project project) {
        this.id = project.getId();
        this.projectId = project.getProjectId();
        this.name = project.getName();
        this.code = project.getCode();
    }
}
