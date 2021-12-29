package com.msd.api.controller;

import com.msd.api.dto.request.ProjectRequest;
import com.msd.api.dto.response.ProjectResponse;
import com.msd.api.service.ProjectService;
import io.swagger.annotations.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/project")
@Api(value = "Project API End Point")
public class ProjectController {


    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @ApiOperation(value = "Add Project", response = ProjectResponse.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Content-Type", value = "application/json", paramType = "header"),
            @ApiImplicitParam(name = "bearer-access-token", value = "Bearer Generated access token",
                    paramType = "header")})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Successfully Deposit to Bank Account"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403,
                    message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")})
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ProjectResponse> addProject(@RequestBody ProjectRequest projectRequest) {
        return ResponseEntity.ok(projectService.addProject(projectRequest));
    }

    @ApiOperation(value = "View Projects", response = ProjectResponse.class,responseContainer = "List")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Content-Type", value = "application/json", paramType = "header"),
            @ApiImplicitParam(name = "bearer-access-token", value = "Bearer Generated access token",
                    paramType = "header")})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Successfully Withdraw in Bank Account"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403,
                    message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")})
    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<ProjectResponse>> viewAllProject() {
        return ResponseEntity.ok(projectService.getAll());
    }
}
