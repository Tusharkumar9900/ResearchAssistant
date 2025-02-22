package com.research.Assistant.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.research.Assistant.Model.ResearchRequest;
import com.research.Assistant.Service.ResearchService;

import lombok.AllArgsConstructor;


/**
 * ResearchController is a REST API controller that handles research-related requests.
 * It provides an endpoint to process content and return results.
 */
@RestController // Marks this class as a REST controller in Spring Boot
@RequestMapping("/api/research") // Defines the base URL path for this controller
@CrossOrigin(origins="*") // Allows cross-origin requests from any domain (CORS policy)
@AllArgsConstructor // Lombok annotation to generate a constructor with all required dependencies
public class ResearchController {
    
     // Injecting ResearchService as a dependency
    private final ResearchService researchService;

     /**
     * Handles HTTP POST requests to process research content.
     * 
     * @param researchRequest - JSON request body containing content to be processed.
     * @return ResponseEntity<String> - The processed result wrapped in an HTTP response.
     */
    @PostMapping("/process") // Maps this method to HTTP POST requests at "/api/research/process"
    public ResponseEntity<String> processContent(@RequestBody ResearchRequest researchRequest) {
         // Calls the service method to process the content
        String result = researchService.processContent(researchRequest);
         // Returns the processed result with HTTP 200 (OK) status
        return ResponseEntity.ok(result);
    }
}


/*
 * 1️⃣ Annotations
@RestController → Marks the class as a Spring Boot REST API controller.
@RequestMapping("/api/research") → Sets the base URL for all endpoints in this controller.
@CrossOrigin(origins="*") → Enables Cross-Origin Resource Sharing (CORS), allowing frontend applications from different origins to access this API.
@AllArgsConstructor → Auto-generates a constructor to inject dependencies (eliminates the need for @Autowired).
2️⃣ Dependency Injection
private final ResearchService researchService;
The ResearchService is injected as a dependency to handle business logic.
Since @AllArgsConstructor is used, Spring Boot automatically injects the dependency when the class is instantiated.
3️⃣ POST API Endpoint (/process)
@PostMapping("/process") → Defines a POST endpoint at /api/research/process.
@RequestBody ResearchRequest researchRequest
Extracts the JSON request body into a ResearchRequest object.
The ResearchRequest class should contain fields like content and operation.
4️⃣ Response Handling
String result = researchService.processContent(researchRequest);
Calls the processContent method from ResearchService, which processes the request.
return ResponseEntity.ok(result);
Returns the processed result wrapped in an HTTP 200 (OK) response.
This controller provides a simple API endpoint to process research content and return the results.
 */