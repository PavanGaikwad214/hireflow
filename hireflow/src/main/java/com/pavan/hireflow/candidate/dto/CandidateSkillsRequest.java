package com.pavan.hireflow.candidate.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CandidateSkillsRequest {

    private List<String> skills;
}
