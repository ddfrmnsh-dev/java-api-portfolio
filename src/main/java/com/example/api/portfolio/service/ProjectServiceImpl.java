package com.example.api.portfolio.service;

import com.example.api.portfolio.dto.ProjectDto;
import com.example.api.portfolio.model.Project;
import com.example.api.portfolio.model.User;
import com.example.api.portfolio.repository.ProjectRepository;
import com.example.api.portfolio.repository.UserRepository;
import org.hibernate.annotations.DialectOverride;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProjectServiceImpl implements ProjectService {
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private UserRepository userRepository;



    @Override
    public Project getProjectById(Long id) {
        return projectRepository.findById(id).orElse(null);
    }

    @Override
    public List<Project> getAllProjects(){
        return projectRepository.findAll();
    }

    @Override
    public ProjectDto createProject(Long id, Project project){
        Optional<User> userOptional = userRepository.findById(id);
        if(userOptional.isPresent()){
           User user = userOptional.get();
           Project projects = new Project();
           projects.setName(project.getName());
           projects.setDescription(project.getDescription());
           projects.setSlug(project.getSlug().replaceAll(" ", "-" ));
           projects.setLinkWebsite(project.getLinkWebsite());
           projects.setUser(user);
           projectRepository.save(projects);

           ProjectDto projectDto = new ProjectDto();
           projectDto.setId(projects.getId());
           projectDto.setName(projects.getName());
           projectDto.setSlug(projects.getSlug());
           projectDto.setDescription(projects.getDescription());
           projectDto.setLinkWebsite(projects.getLinkWebsite());
           projectDto.setCreatedAt(projects.getCreatedAt());
           projectDto.setUpdatedAt(projects.getUpdatedAt());
           return projectDto;
        }
        return null;
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
