package com.example.api.portfolio.service;

import com.example.api.portfolio.dto.ProjectDto;
import com.example.api.portfolio.model.Project;

import java.util.List;

public interface ProjectService {

    List<Project> getAllProjects();
    Project getProjectById(Long id);
    ProjectDto createProject(Long id, Project project);
    Project updateProject(Long id, Project project);
    void deleteProject(Long id);
}
