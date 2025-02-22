package com.research.Assistant.Model;

import lombok.Data;

@Data // @Data annotation generates all required methods like getters, setters, equals, hashCode, and toString.
public class ResearchRequest {
    
    // This variable stores the text/content that needs to be processed (e.g., summarized).
    private String content;

    // This variable specifies the type of operation to be performed on the content 
    // (e.g., "summarize", "analyze", etc.).
    private String operation;

}
