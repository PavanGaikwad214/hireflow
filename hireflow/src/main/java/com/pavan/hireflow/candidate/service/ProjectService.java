package com.pavan.hireflow.candidate.service;

import com.pavan.hireflow.candidate.dto.ProjectRequest;
import com.pavan.hireflow.candidate.dto.ProjectResponse;
import com.pavan.hireflow.candidate.entity.CandidateProfile;
import com.pavan.hireflow.candidate.entity.Project;
import com.pavan.hireflow.candidate.repository.CandidateProfileRepository;
import com.pavan.hireflow.candidate.repository.ProjectRepository;
import com.pavan.hireflow.common.exception.CandidateProfileNotFoundException;
import com.pavan.hireflow.common.exception.ProjectNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final CandidateProfileRepository candidateProfileRepository;

    public ProjectService(ProjectRepository projectRepository, CandidateProfileRepository candidateProfileRepository) {
        this.projectRepository = projectRepository;
        this.candidateProfileRepository = candidateProfileRepository;
    }

    public ProjectResponse addProject(Long userId, ProjectRequest projectRequest) {

        CandidateProfile candidateProfile = candidateProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new CandidateProfileNotFoundException("Candidate Profile Not Found"));

        Project project = new Project();

        project.setCandidateProfile(candidateProfile);
        project.setTitle(projectRequest.getTitle());
        project.setDescription(projectRequest.getDescription());
        project.setProjectUrl(projectRequest.getProjectUrl());
        project.setGithubUrl(projectRequest.getGithubUrl());
        project.setStartDate(projectRequest.getStartDate());
        project.setEndDate(projectRequest.getEndDate());

        Project savedProject = projectRepository.save(project);

        return mapToResponse(savedProject);
    }

    public List<ProjectResponse> getAllProjects(Long userId) {

        CandidateProfile candidateProfile = candidateProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new CandidateProfileNotFoundException("Candidate Profile Not Found"));

        return projectRepository.findByCandidateProfile_Id(candidateProfile.getId())
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public ProjectResponse updateProject(Long userId, Long projectId, ProjectRequest projectRequest) {

        CandidateProfile candidateProfile = candidateProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new CandidateProfileNotFoundException("Candidate Profile Not Found"));

        Project project = projectRepository
                .findByIdAndCandidateProfile_Id(projectId, candidateProfile.getId())
                .orElseThrow(() -> new ProjectNotFoundException("Project Not Found"));

        project.setTitle(projectRequest.getTitle());
        project.setDescription(projectRequest.getDescription());
        project.setProjectUrl(projectRequest.getProjectUrl());
        project.setGithubUrl(projectRequest.getGithubUrl());
        project.setStartDate(projectRequest.getStartDate());
        project.setEndDate(projectRequest.getEndDate());


        Project updatedProject = projectRepository.save(project);
        return mapToResponse(updatedProject);
    }

    public void deleteProject(Long userId, Long projectId) {

        CandidateProfile candidateProfile = candidateProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new CandidateProfileNotFoundException("Candidate Profile Not Found"));

        Project project = projectRepository
                .findByIdAndCandidateProfile_Id(projectId, candidateProfile.getId())
                .orElseThrow(() -> new ProjectNotFoundException("Project Not Found"));

         projectRepository.delete(project);
    }

    private ProjectResponse mapToResponse(Project project) {

        return new ProjectResponse(
                project.getId(),
                project.getTitle(),
                project.getDescription(),
                project.getProjectUrl(),
                project.getGithubUrl(),
                project.getStartDate(),
                project.getEndDate()
        );
    }
}
