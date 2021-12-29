package com.msd.api.dto.request;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@EqualsAndHashCode
public class ProjectRequest implements Serializable {

    private String name;
    private String code;
}
