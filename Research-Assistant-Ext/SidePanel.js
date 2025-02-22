// Wait until the DOM is fully loaded before executing the script
document.addEventListener('DOMContentLoaded', () => {

    // Retrieve saved research notes from Chrome's local storage
    chrome.storage.local.get(['researchNotes'], function(result) {
       // If research notes exist in storage, set them in the textarea field
        if (result.researchNotes) {
        document.getElementById('notes').value = result.researchNotes;
       } 
    });

     // Add event listener to the "Summarize" button to trigger the summarizeText function
     document.getElementById('summarizeBtn').addEventListener('click', summarizeText);

     // Add event listener to the "Save Notes" button to trigger the saveNotes function
     document.getElementById('saveNotesBtn').addEventListener('click', saveNotes);
});


// Function to summarize the selected text
async function summarizeText() {
    try {
        // Get the active tab in the current window
        const [tab] = await chrome.tabs.query({ active:true, currentWindow: true});
       
        // Execute a script on the active tab to get the selected text
        const [{ result }] = await chrome.scripting.executeScript({
            target: {tabId: tab.id}, // Target the active tab
            function: () =>{ window.getSelection().toString()
                 // Get the selected text from the webpage
                return { result: selection || '' }; // Ensure non-null return
            }
        });

        // If no text is selected, show an error message
        if (!result) {
            showResult('Please select some text first');
            return;
        }

        // Send the selected text to the backend API for summarization
        const response = await fetch('http://localhost:8080/api/research/process', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ content: result, operation: 'summarize'})
        });

        // If the API request fails, throw an error
        if (!response.ok) {
            throw new Error(`API Error: ${response.status}`);
        }

        // Get the summarized text response from the API
        const text = await response.text();
        // Display the summarized result, replacing new lines with `<br>` for better formatting
        showResult(text.replace(/\n/g,'<br>'));

    } catch (error) {
        showResult('Error: ' + error.message);
    }
}

// Function to save notes in Chrome's local storage
async function saveNotes() {
     // Get the value from the notes textarea
    const notes = document.getElementById('notes').value;
     // Save the notes to Chrome's local storage
    chrome.storage.local.set({ 'researchNotes': notes}, function() {
        alert('Notes saved successfully');
    });
}

// Function to display results in the HTML page
function showResult(content) {
    // Insert the result content inside a styled div element
    document.getElementById('results').innerHTML = `<div class="result-item"><div class="result-content">${content}</div></div>`;
}