package com.example.api.portfolio.service;

import com.example.api.portfolio.model.Project;
import com.example.api.portfolio.repository.ProjectRepository;
import org.hibernate.annotations.DialectOverride;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectServiceImpl implements ProjectService {
    @Autowired
    private ProjectRepository projectRepository;

    @Override
    public Project getProjectById(Long id) {
        return projectRepository.findById(id).orElse(null);
    }

    @Override
    public List<Project> getAllProjects(){
        return projectRepository.findAll();
    }

    @Override
    public Project createProject(Project project){
        Project projects = new Project();
        projects.setName(project.getName());
        return projectRepository.save(projects);
    }

    @Override
    public Project updateProject(Long id, Project projectDetails) {
        Project existProject = projectRepository.findById(id).orElse(null);
        if (existProject != null){
            existProject.setName(projectDetails.getName());
            return projectRepository.save(existProject);
        }
        return null;
    }

    @Override
    public void deleteProject(Long id){
        Project existProject = getProjectById(id);
        projectRepository.delete(existProject);
    }
}
