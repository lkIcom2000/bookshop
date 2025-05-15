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
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import java.util.HashMap;
import java.util.Map;

import java.util.List;

@RestController
@RequestMapping("/stands")
@Tag(name = "Stand Controller", description = "API for managing stands")
public class StandController {
    private static final Logger logger = LoggerFactory.getLogger(StandController.class);
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
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully created the stand"),
        @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    public ResponseEntity<?> createStand(
            @Valid @RequestBody Stand stand,
            BindingResult bindingResult) {
        try {
            logger.info("Received stand creation request - Raw object: {}", stand);

            // Check for validation errors
            if (bindingResult.hasErrors()) {
                Map<String, String> errors = new HashMap<>();
                for (FieldError error : bindingResult.getFieldErrors()) {
                    errors.put(error.getField(), error.getDefaultMessage());
                }
                logger.error("Validation errors: {}", errors);
                return ResponseEntity.badRequest().body(errors);
            }

            // Additional validation
            if (stand.getCustomerNumber() == null || stand.getCustomerNumber().trim().isEmpty()) {
                logger.error("Customer number is null or empty");
                return ResponseEntity.badRequest().body(Map.of("customerNumber", "Customer number is required"));
            }
            if (stand.getSquareMetres() == null || stand.getSquareMetres() <= 0) {
                logger.error("Square metres is invalid: {}", stand.getSquareMetres());
                return ResponseEntity.badRequest().body(Map.of("squareMetres", "Square metres must be greater than 0"));
            }
            if (stand.getFair() == null || stand.getFair().trim().isEmpty()) {
                logger.error("Fair is null or empty");
                return ResponseEntity.badRequest().body(Map.of("fair", "Fair is required"));
            }
            if (stand.getLocation() == null || stand.getLocation().trim().isEmpty()) {
                logger.error("Location is null or empty");
                return ResponseEntity.badRequest().body(Map.of("location", "Location is required"));
            }

            logger.info("All validation passed, creating stand with values - customerNumber: '{}', squareMetres: {}, fair: '{}', location: '{}'",
                stand.getCustomerNumber(),
                stand.getSquareMetres(),
                stand.getFair(),
                stand.getLocation());

            Stand createdStand = standService.createStand(stand);
            logger.info("Successfully created stand: {}", createdStand);
            return ResponseEntity.ok(createdStand);
        } catch (Exception e) {
            logger.error("Error creating stand: {}", e.getMessage(), e);
            return ResponseEntity.badRequest().body(Map.of("error", "Error creating stand: " + e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a stand", description = "Updates an existing stand with the provided details")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully updated the stand"),
        @ApiResponse(responseCode = "404", description = "Stand not found")
    })
    public ResponseEntity<Stand> updateStand(
            @Parameter(description = "ID of the stand to update") @PathVariable Long id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "Updated stand object",
                required = true,
                content = @io.swagger.v3.oas.annotations.media.Content(
                    schema = @Schema(implementation = Stand.class),
                    examples = @io.swagger.v3.oas.annotations.media.ExampleObject(
                        value = """
                        {
                          "customerNumber": "CUST001",
                          "squareMetres": 25.5,
                          "fair": "Book Fair 2024",
                          "location": "Hall A, Section 3"
                        }
                        """
                    )
                )
            )
            @RequestBody Stand stand) {
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