# **ResearchAssistant - AI-Powered Research Tool**

ResearchAssistant is a Spring Boot application that leverages Google's Gemini AI API to assist users in processing text-based research tasks such as summarization and suggestions. It provides an API that integrates with a Chrome extension to fetch user-selected content from web pages and process it using AI.

## Features
Summarizes large chunks of text using AI. <br>
Suggests related titles based on input content. <br>
API-based architecture to support multiple client applications.<br>
Chrome extension integration for easy text selection and processing.<br>
Uses Spring Boot, WebClient, and Jackson for efficient backend operations.<br>

## Technologies Used
Spring Boot (Java) <br>
WebClient (for API calls) <br>
Jackson (for JSON parsing) <br>
Lombok (for reducing boilerplate code) <br>
Google Gemini AI API (for AI-based processing) <br>
Chrome Extension (Frontend integration) <br>

## Installation and Setup
Prerequisites <br>
Java 17+ <br>
Maven <br>
Google Gemini API Key <br>

## Configure API Key
Update the application.properties file:
```
gemini.api.url=https://api.example.com/gemini
gemini.api.key=YOUR_GEMINI_API_KEY
```

## Build and Run the Application
```
mvn clean install
mvn spring-boot:run
```

## Chrome Extension Integration
The Chrome extension fetches selected text from web pages and sends it to the ResearchAssistant backend for processing. Ensure your backend is running before using the extension. <br>
Future Enhancements <br>
Add authentication for API requests. <br>
Support for multiple AI models. <br>
Extend functionalities with document analysis and citations. <br>

## Contributing
Feel free to open issues or submit pull requests to improve this project.
