package com.pavan.hireflow.candidate.controller;

import com.pavan.hireflow.auth.entity.User;
import com.pavan.hireflow.candidate.dto.ProjectRequest;
import com.pavan.hireflow.candidate.dto.ProjectResponse;
import com.pavan.hireflow.candidate.service.ProjectService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/candidate/project")
@PreAuthorize("hasRole('CANDIDATE')")
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }


    @PostMapping
    public ResponseEntity<ProjectResponse> addProject(
            @AuthenticationPrincipal User user,
            @RequestBody ProjectRequest projectRequest) {

        ProjectResponse response = projectService.addProject(user.getId(), projectRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<ProjectResponse>> getAllProjects(
            @AuthenticationPrincipal User user
    ) {
        return ResponseEntity.ok(projectService.getAllProjects(user.getId()));
    }

    @PutMapping("/{projectId}")
    public ResponseEntity<ProjectResponse> updateProject(
            @AuthenticationPrincipal User user,
            @PathVariable Long projectId,
            @RequestBody ProjectRequest projectRequest
    ){
        ProjectResponse response = projectService.updateProject(user.getId(), projectId, projectRequest);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{projectId}")
    public ResponseEntity<Void> deleteProject(
            @AuthenticationPrincipal User user,
            @PathVariable Long projectId
    ){
        projectService.deleteProject(user.getId(), projectId);

        return ResponseEntity.noContent().build();
    }
}
