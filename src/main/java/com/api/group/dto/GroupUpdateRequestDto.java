package com.api.group.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class GroupUpdateRequestDto {
    private Long id;
    private String name;
    private String description;
    private List<Members> members;
}
