package com.example.api.portfolio.controller;

import com.example.api.portfolio.dto.ApiResponse;
import com.example.api.portfolio.dto.MetaResponse;
import com.example.api.portfolio.dto.ProjectDto;
import com.example.api.portfolio.model.Project;
import com.example.api.portfolio.model.UserPrincipal;
import com.example.api.portfolio.repository.ProjectRepository;
import com.example.api.portfolio.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ProjectController {
    @Autowired
    private ProjectService projectService;
    @Autowired
    private ProjectRepository projectRepository;

    @PostMapping("/project")
    public ApiResponse<?> createProject(@RequestBody Project project, Authentication authentication){
        try {
            UserPrincipal up = (UserPrincipal) authentication.getPrincipal();

            ProjectDto projects = projectService.createProject(up.getId(), project);
            MetaResponse meta = new MetaResponse("Success create project", HttpStatus.OK.value(), "success");
            return new ApiResponse<>(meta, projects);
        } catch (Exception e){
            MetaResponse meta = new MetaResponse(e.getMessage(), HttpStatus.OK.value(), "failed");
            return new ApiResponse<>(meta, null);
        }
    }
}
