package com.pavan.hireflow.company.controller;

import com.pavan.hireflow.auth.entity.User;
import com.pavan.hireflow.company.dto.RecruiterNoteRequest;
import com.pavan.hireflow.company.dto.RecruiterNoteResponse;
import com.pavan.hireflow.company.service.RecruiterNoteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/recruiter/applications")
@PreAuthorize("hasRole('RECRUITER')")
public class RecruiterNoteController {

    private final RecruiterNoteService recruiterNoteService;

    public RecruiterNoteController(RecruiterNoteService recruiterNoteService) {
        this.recruiterNoteService = recruiterNoteService;
    }

    @PostMapping("/{applicationId}/notes")
    public ResponseEntity<RecruiterNoteResponse> addNote(
            @AuthenticationPrincipal User user,
            @PathVariable Long applicationId,
            @RequestBody RecruiterNoteRequest recruiterNoteRequest
    ){
        RecruiterNoteResponse response = recruiterNoteService.addNote(
                user.getId(),
                applicationId,
                recruiterNoteRequest
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{applicationId}/notes")
    public ResponseEntity<List<RecruiterNoteResponse>> getNotes(
            @AuthenticationPrincipal User user,
            @PathVariable Long applicationId
    ){
        List<RecruiterNoteResponse> response = recruiterNoteService.getNotes(user.getId(),applicationId);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


    @PutMapping("/notes/{noteId}")
    public ResponseEntity<RecruiterNoteResponse> updateNote(
            @AuthenticationPrincipal User user,
            @PathVariable Long noteId,
            @RequestBody RecruiterNoteRequest recruiterNoteRequest
    ){
        RecruiterNoteResponse response = recruiterNoteService.updateNotes(
                user.getId(),
                noteId,
                recruiterNoteRequest
        );
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/notes/{noteId}")
    public ResponseEntity<Void> deleteNote(
            @AuthenticationPrincipal User user,
            @PathVariable Long noteId
    ){
        recruiterNoteService.deleteNote(
                user.getId(),
                noteId
        );

        return ResponseEntity.noContent().build();
    }
}
