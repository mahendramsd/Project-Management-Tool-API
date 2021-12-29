package com.msd.api.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.msd.api.dto.ColumnsDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;


@Data
@EqualsAndHashCode
public class BoardResponse implements Serializable {

    private String name;
    private List<ColumnsDto> columns;

}
