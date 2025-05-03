package dk.au.standservice.controller;

import dk.au.standservice.model.Stand;
import dk.au.standservice.service.StandService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/stands")
@Tag(name = "Stand Controller", description = "API for managing stands")
public class StandController {
    private final StandService standService;

    @Autowired
    public StandController(StandService standService) {
        this.standService = standService;
    }

    @GetMapping("/test")
    @Operation(summary = "Test endpoint", description = "Returns a test message to verify the API is working")
    public String test() {
        return "Test endpoint is working!";
    }

    @GetMapping
    @Operation(summary = "Get all stands", description = "Retrieves a list of all stands")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved all stands")
    public List<Stand> getAllStands() {
        return standService.getAllStands();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get stand by ID", description = "Retrieves a specific stand by its ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved the stand"),
        @ApiResponse(responseCode = "404", description = "Stand not found")
    })
    public ResponseEntity<Stand> getStandById(@Parameter(description = "ID of the stand to retrieve") @PathVariable Long id) {
        return standService.getStandById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Create a new stand", description = "Creates a new stand with the provided details")
    @ApiResponse(responseCode = "200", description = "Successfully created the stand")
    public Stand createStand(@Parameter(description = "Stand object to create") @RequestBody Stand stand) {
        return standService.createStand(stand);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a stand", description = "Updates an existing stand with the provided details")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully updated the stand"),
        @ApiResponse(responseCode = "404", description = "Stand not found")
    })
    public ResponseEntity<Stand> updateStand(
            @Parameter(description = "ID of the stand to update") @PathVariable Long id,
            @Parameter(description = "Updated stand object") @RequestBody Stand stand) {
        Stand updatedStand = standService.updateStand(id, stand);
        return updatedStand != null ? ResponseEntity.ok(updatedStand) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a stand", description = "Deletes a stand by its ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully deleted the stand"),
        @ApiResponse(responseCode = "404", description = "Stand not found")
    })
    public ResponseEntity<Void> deleteStand(@Parameter(description = "ID of the stand to delete") @PathVariable Long id) {
        standService.deleteStand(id);
        return ResponseEntity.ok().build();
    }
} 