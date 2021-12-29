package com.msd.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ColumnsDto implements Serializable {
    private String name;
    private String id;
    private List<IssueDto> tasks;

    public ColumnsDto(String name, String id, List<IssueDto> tasks) {
        this.name = name;
        this.id = id;
        this.tasks = tasks;
    }
}
