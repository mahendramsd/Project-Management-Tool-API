package com.msd.api.controller;

import com.msd.api.dto.request.AssignRequest;
import com.msd.api.dto.request.IssueRequest;
import com.msd.api.dto.request.IssueStateRequest;
import com.msd.api.dto.response.*;
import com.msd.api.service.IssueService;
import io.swagger.annotations.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;
import java.util.List;

@RestController
@RequestMapping("/issue")
@Api(value = "Issue API End Point")
public class IssueController {

    private final IssueService issueService;

    public IssueController(IssueService issueService) {
        this.issueService = issueService;
    }


    @ApiOperation(value = "Add Issue")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Content-Type", value = "application/json", paramType = "header"),
            @ApiImplicitParam(name = "Authorization", value = "Bearer Generated access token",
                    paramType = "header")})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Successfully"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403,
                    message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")})
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Void> addIssue(@ApiIgnore @RequestAttribute("user-id") Long userId,
            @RequestBody IssueRequest issueRequest) {
        issueService.createIssue(issueRequest,userId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @ApiOperation(value = "Assign API")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Content-Type", value = "application/json", paramType = "header"),
            @ApiImplicitParam(name = "Authorization", value = "Bearer Generated access token",
                    paramType = "header")})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Successfully"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403,
                    message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")})
    @PutMapping("/assign")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Void> assignUser(
            @ApiIgnore @RequestAttribute("user-id") Long userId,
            @RequestBody AssignRequest assignRequest) {
        issueService.assignIssue(assignRequest,userId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @ApiOperation(value = "Change Issue State", response = ProjectResponse.class,responseContainer = "List")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Content-Type", value = "application/json", paramType = "header"),
            @ApiImplicitParam(name = "Authorization", value = "Bearer Generated access token",
                    paramType = "header")})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Successfully"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403,
                    message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")})
    @PutMapping("/change")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Void> changeIssueState(
            @ApiIgnore @RequestAttribute("user-id") Long userId,
            @RequestBody IssueStateRequest issueStateRequest) {
        issueService.changeState(issueStateRequest,userId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @ApiOperation(value = "Get Issue",response = BoardResponse.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Content-Type", value = "application/json", paramType = "header"),
            @ApiImplicitParam(name = "Authorization", value = "Bearer Generated access token",
                    paramType = "header")})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Successfully"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403,
                    message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")})
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<BoardResponse> getIssues(@RequestParam("projectId") Long projectId,
                                                   @RequestParam("state") String state,
                                                   @RequestParam("issueType") String issueType,
                                                   @RequestParam("assignee") String assignee) {
        return ResponseEntity.ok(issueService.getIssues(projectId,state,issueType,assignee));
    }

    @ApiOperation(value = "Get Change logs", response = ChangeLogResponse.class, responseContainer = "List")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Content-Type", value = "application/json", paramType = "header"),
            @ApiImplicitParam(name = "Authorization", value = "Bearer Generated access token",
                    paramType = "header")})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Successfully"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403,
                    message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")})
    @GetMapping("/logs/{issueId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<ChangeLogResponse>> getChangeLogs(@PathVariable("issueId") Long issueId) {
        return ResponseEntity.ok(issueService.getChangeLogs(issueId));
    }

    @ApiOperation(value = "Get Issue", response = IssueResponse.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Content-Type", value = "application/json", paramType = "header"),
            @ApiImplicitParam(name = "Authorization", value = "Bearer Generated access token",
                    paramType = "header")})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Successfully"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403,
                    message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")})
    @GetMapping("/{issueId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<IssueResponse> getIssue(@PathVariable("issueId") Long issueId) {
        return ResponseEntity.ok(issueService.getIssue(issueId));
    }
}
