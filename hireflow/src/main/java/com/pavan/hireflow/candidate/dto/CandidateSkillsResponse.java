package com.pavan.hireflow.candidate.dto;

import com.pavan.hireflow.candidate.entity.Skill;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;


@Getter
@AllArgsConstructor
public class CandidateSkillsResponse {

    private Set<String> skills;
}
