package nl.tudelft.oopp.group54.models.requestentities;

public class EditQuestionRequest {
    private String questionId;
    private String newContent;
    
    /**
     * Constructor for EditQuestionRequest.
     * 
     * @param questionId the question id
     * @param newContent the new content
     */
    public EditQuestionRequest(String questionId, String newContent) {
        this.questionId = questionId;
        this.newContent = newContent;
    }
    
    public EditQuestionRequest() {

    }

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public String getNewContent() {
        return newContent;
    }

    public void setNewContent(String newContent) {
        this.newContent = newContent;
    }
    
}
