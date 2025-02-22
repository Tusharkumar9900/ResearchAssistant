package com.research.Assistant.Service;

import java.util.Map;

// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.research.Assistant.Dto.GeminiAIResponse;
import com.research.Assistant.Model.ResearchRequest;



@Service
public class ResearchService {

    // Injecting API URL from application.properties.
    @Value("${gemini.api.url}")
    private String geminiAiUrl;

    // Injecting API Key from application.properties.
    @Value("${gemini.api.key}")
    private String geminiAiKey;

    // WebClient is used for making API calls.
    private final WebClient webClient;

    // ObjectMapper is used to convert JSON responses into Java objects.
    private final ObjectMapper objectMapper;


      // Constructor-based dependency injection for WebClient and ObjectMapper.
    public ResearchService(WebClient.Builder webClientBuilder, ObjectMapper objectMapper) {
        this.webClient = webClientBuilder.build();
        this.objectMapper = objectMapper;
    }

      // Method to process research requests and call Gemini AI API.
    public String processContent(ResearchRequest researchRequest) {
       //building the prompt
      String prompt = buildPrompt(researchRequest);

       // Constructs the JSON request body.
      Map<String, Object> requestBody = Map.of(
        "contents", new Object[]{
            Map.of("parts", new Object[]{
                Map.of("text", prompt)
            })
        }
      );
     // Sends HTTP POST request to Gemini AI API.
      String response = webClient.post()
        .uri(geminiAiUrl + geminiAiKey)  // URL including API key.
          // Alternative approach to construct URL dynamically.
    //     .uri(uriBuilder -> uriBuilder
    //     .path("/models/gemini-1.5-flash:generateContent")
    //     .queryParam("key", geminiAiKey)
    //     .build()
    // )
    // .header("Content-Type", "application/json")

    .bodyValue(requestBody)  // Attaches the JSON request body.
    .retrieve()  // Sends the request and waits for a response.
    .bodyToMono(String.class)  // Converts response body to String.
    .block();  // Blocks execution until response is received.
           

        // Extracts meaningful text from the API response.
        return extractTextFromResponse(response);
            }
        
            // Parses the API response and extracts the relevant text.
            private String extractTextFromResponse(String response) {
               try{
                // Converts JSON response into Java object using ObjectMapper.
                GeminiAIResponse geminiaiResponse = objectMapper.readValue(response, GeminiAIResponse.class);
                // Extracts the first candidate's text from the response.
                if(geminiaiResponse.getCandidates() != null && !geminiaiResponse.getCandidates().isEmpty()){
                    GeminiAIResponse.Candidate firstCandidate = geminiaiResponse.getCandidates().get(0);
                    if(firstCandidate.getContent() != null && firstCandidate.getContent().getParts() != null && !firstCandidate.getContent().getParts().isEmpty()){
                        return firstCandidate.getContent().getParts().get(0).getText();
                    }
                }
                return "No response from GeminiAI"; // Returns fallback message if response is empty.
               }catch(Exception e){
                // Returns error message in case of an exception.
                return "Error occurred while processing the request" + e.getMessage();
               }
            }
        
       // Builds a prompt string based on the operation type provided in the request.
       private String buildPrompt(ResearchRequest researchRequest) {
       StringBuilder prompt = new StringBuilder();
       // Determines the operation and creates an appropriate prompt.
       switch(researchRequest.getOperation()){
        case "summarize":
            prompt.append("Provide Concise Summarize: ");
            break;
        case "Suggest":
            prompt.append("Suggest a related title based on following content: ");
            break;
        default:
            throw new IllegalArgumentException("Unknown operation: " + researchRequest.getOperation());   
       }
        // Appends user-provided content to the prompt.
       prompt.append(researchRequest.getContent());
       return prompt.toString();
    }  
}


/*
 * Service Layer (@Service)

This class is a Spring Service responsible for handling business logic related to processing user requests.
It interacts with an external Gemini AI API to process content.
Dependency Injection

WebClient: Used for making API requests.
ObjectMapper: Converts JSON responses into Java objects.
@Value: Injects API URL and Key from the application properties.
Key Methods & Their Responsibilities:

processContent(ResearchRequest researchRequest)

Builds a prompt based on user input.
Sends an HTTP POST request to Gemini AI.
Extracts and returns AI-generated text from the response.
extractTextFromResponse(String response)

Parses the API response and extracts meaningful text.
buildPrompt(ResearchRequest researchRequest)

Generates a text prompt for AI based on the operation type.

--------------------------
Handles user requests (ResearchRequest).
Builds a prompt dynamically.
Calls Gemini AI API using WebClient.
Extracts relevant text from the AI response.
Returns AI-generated text as a result.

 */
