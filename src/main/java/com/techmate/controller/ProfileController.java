package com.techmate.controller;

import com.techmate.dto.ProfileResponse;
import com.techmate.dto.UpdatePasswordRequest;
import com.techmate.dto.UpdateProfileRequest;
import com.techmate.service.ProfileService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/profile")
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;

    @GetMapping
    public ResponseEntity<ProfileResponse> getProfile() {
        return ResponseEntity.ok(profileService.getProfile());
    }

    @PutMapping
    public ResponseEntity<ProfileResponse> updateName(@Valid @RequestBody UpdateProfileRequest request) {
        return ResponseEntity.ok(profileService.updateName(request));
    }

    @PutMapping("/password")
    public ResponseEntity<Void> updatePassword(@Valid @RequestBody UpdatePasswordRequest request) {
        profileService.updatePassword(request);
        return ResponseEntity.ok().build();
    }
}