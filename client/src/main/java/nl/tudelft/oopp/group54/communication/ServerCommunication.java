package nl.tudelft.oopp.group54.communication;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import nl.tudelft.oopp.group54.Datastore;
import nl.tudelft.oopp.group54.models.requestentities.BanIpRequest;
import nl.tudelft.oopp.group54.models.requestentities.CreateLectureRequest;
import nl.tudelft.oopp.group54.models.requestentities.DeleteQuestionRequest;
import nl.tudelft.oopp.group54.models.requestentities.EditQuestionRequest;
import nl.tudelft.oopp.group54.models.requestentities.JoinLectureRequest;
import nl.tudelft.oopp.group54.models.requestentities.LectureFeedbackRequest;
import nl.tudelft.oopp.group54.models.requestentities.PostAnswerRequest;
import nl.tudelft.oopp.group54.models.requestentities.PostPollRequest;
import nl.tudelft.oopp.group54.models.requestentities.PostQuestionRequest;
import nl.tudelft.oopp.group54.models.requestentities.VoteRequest;
import nl.tudelft.oopp.group54.models.responseentities.BanIpResponse;
import nl.tudelft.oopp.group54.models.responseentities.CreateLectureResponse;
import nl.tudelft.oopp.group54.models.responseentities.DeleteQuestionResponse;
import nl.tudelft.oopp.group54.models.responseentities.EditQuestionResponse;
import nl.tudelft.oopp.group54.models.responseentities.EndLectureResponse;
import nl.tudelft.oopp.group54.models.responseentities.GetAllQuestionsResponse;
import nl.tudelft.oopp.group54.models.responseentities.GetLectureFeedbackResponse;
import nl.tudelft.oopp.group54.models.responseentities.GetLectureMetadataResponse;
import nl.tudelft.oopp.group54.models.responseentities.JoinLectureResponse;
import nl.tudelft.oopp.group54.models.responseentities.LectureFeedbackResponse;
import nl.tudelft.oopp.group54.models.responseentities.PostAnswerResponse;
import nl.tudelft.oopp.group54.models.responseentities.PostPollResponse;
import nl.tudelft.oopp.group54.models.responseentities.PostQuestionResponse;
import nl.tudelft.oopp.group54.models.responseentities.VoteResponse;

public class ServerCommunication {

    private static HttpClient client = HttpClient.newBuilder().build();
    private static Datastore ds = Datastore.getInstance();
    //    private static RequestBuilder requestBuilder = new RequestBuilder();
    private static ObjectMapper objectMapper = new ObjectMapper();


    /**
     * Post lecture create lecture response.
     *
     * @param startTimestamp the start timestamp
     * @param lectureName    the lecture name
     * @return the create lecture response
     * @throws IOException          the io exception
     * @throws InterruptedException the interrupted exception
     */
    public static CreateLectureResponse postLecture(Long startTimestamp, String lectureName)
            throws IOException, InterruptedException {

        CreateLectureRequest clr = new CreateLectureRequest(lectureName, startTimestamp);
        String clrJson = objectMapper.writeValueAsString(clr);

        HttpRequest request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(clrJson))
                .header("content-type", "application/json")
                .uri(URI.create(ds.getServiceEndpoint() + "/lectures"))
                .build();

        HttpResponse<String> response = null;

        response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            System.out.println("Status: " + response.statusCode());
        }

        return objectMapper.readValue(response.body(), CreateLectureResponse.class);
    }

    /**
     * Join lecture join lecture response.
     *
     * @param userName  the user name
     * @param lectureId the lecture id
     * @param joinId    the join id
     * @return the join lecture response
     * @throws IOException          the io exception
     * @throws InterruptedException the interrupted exception
     */
    public static JoinLectureResponse joinLecture(String userName, Integer lectureId, String joinId) throws IOException,
            InterruptedException {

        JoinLectureRequest jlr = new JoinLectureRequest(userName, lectureId, joinId);
        String jlrJson = objectMapper.writeValueAsString(jlr);

        HttpRequest request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(jlrJson))
                .header("content-type", "application/json")
                .uri(URI.create(ds.getServiceEndpoint() + "/lectures/j/" + lectureId + "/" + joinId))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            System.out.println("Status: " + response.statusCode());
        }

        return objectMapper.readValue(response.body(), JoinLectureResponse.class);
    }

    /**
     * Post question post question response.
     *
     * @param questionText the question text
     * @return the post question response
     * @throws IOException          the io exception
     * @throws InterruptedException the interrupted exception
     */
    public static PostQuestionResponse postQuestion(String questionText) throws IOException, InterruptedException {

        PostQuestionRequest pqr = new PostQuestionRequest(questionText, ds.getUserId().toString());
        String pqrJson = objectMapper.writeValueAsString(pqr);

        HttpRequest request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(pqrJson))
                .header("content-type", "application/json")
                .uri(URI.create(ds.getServiceEndpoint() + "/lectures/" + ds.getLectureId() + "/questions"))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            System.out.println("Status: " + response.statusCode());
        }

        return objectMapper.readValue(response.body(), PostQuestionResponse.class);
    }

    /**
     * Gets all questions.
     *
     * @return the all questions
     * @throws IOException          the io exception
     * @throws InterruptedException the interrupted exception
     */
    public static GetAllQuestionsResponse getAllQuestions() throws IOException, InterruptedException {

        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(ds.getServiceEndpoint() + "/lectures/" + ds.getLectureId() + "/questions?userId="
                        + ds.getUserId()))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            System.out.println("Status: " + response.statusCode());
        }


        return objectMapper.readValue(response.body(), GetAllQuestionsResponse.class);
    }

    /**
     * Post answer post answer response.
     *
     * @param questionId the question id
     * @param answerText the answer text
     * @return the post answer response
     * @throws IOException          the io exception
     * @throws InterruptedException the interrupted exception
     */
    public static PostAnswerResponse postAnswer(String questionId, String answerText) throws IOException,
            InterruptedException {

        PostAnswerRequest par = new PostAnswerRequest(ds.getUserId().toString(), answerText);
        String parJson = objectMapper.writeValueAsString(par);


        HttpRequest request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(parJson))
                .header("content-type", "application/json")
                .uri(URI.create(ds.getServiceEndpoint() + "/lectures/" + ds.getLectureId() + "/questions/"
                        + questionId + "/answer"))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            System.out.println("Status: " + response.statusCode());
        }

        return objectMapper.readValue(response.body(), PostAnswerResponse.class);
    }

    /**
     * Delete question delete question response.
     *
     * @param questionId the question id
     * @return the delete question response
     * @throws IOException          the io exception
     * @throws InterruptedException the interrupted exception
     */
    public static DeleteQuestionResponse deleteQuestion(String questionId) throws IOException, InterruptedException {
        DeleteQuestionRequest requestBody = new DeleteQuestionRequest(String.valueOf(ds.getUserId()));

        String requestBodyJson = objectMapper.writeValueAsString(requestBody);

        HttpRequest request = HttpRequest.newBuilder()
                .DELETE()
                .header("content-type", "application/json")
                .uri(URI.create(ds.getServiceEndpoint() + "/lectures/"
                        + ds.getLectureId() + "/questions/" + questionId + "?userId=" + ds.getUserId()))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            System.out.println("Status:" + response.statusCode());
        }

        return objectMapper.readValue(response.body(), DeleteQuestionResponse.class);
    }
    
    /**
     * Edit question edit question response.
     * 
     * @param questionId the question id
     * @param newContent the new content
     * @return the edit question response
     * @throws IOException          the io exception
     * @throws InterruptedException the interrupted exception
     */
    public static EditQuestionResponse editQuestion(String questionId,
                                                    String newContent) throws IOException, InterruptedException {
        EditQuestionRequest eqr = new EditQuestionRequest(questionId, newContent);
        String eqrJson = objectMapper.writeValueAsString(eqr);
        
        HttpRequest request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(eqrJson))
                .header("content-type", "application/json")
                .uri(URI.create(ds.getServiceEndpoint() + "/lectures/" + ds.getLectureId()
                                + "/questions/edit?userId=" + ds.getUserId()))
                .build();
        
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            System.out.println("Status:" + response.statusCode());
            System.out.println(response.body());
        }

        return objectMapper.readValue(response.body(), EditQuestionResponse.class);
    }

    /**
     * End lecture end lecture response.
     *
     * @return the end lecture response
     * @throws IOException          the io exception
     * @throws InterruptedException the interrupted exception
     */
    public static EndLectureResponse endLecture() throws IOException, InterruptedException {

        HttpRequest request = HttpRequest.newBuilder()
                .PUT(HttpRequest.BodyPublishers.ofString(""))
                .header("content-type", "application/json")
                .uri(URI.create(ds.getServiceEndpoint() + "/lectures/e/"
                        + ds.getLectureId() + "?userId=" + ds.getUserId()))
                .build();


        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        return objectMapper.readValue(response.body(), EndLectureResponse.class);
    }


    /**
     * Vote on question vote response.
     *
     * @param questionId the question id
     * @return the vote response
     * @throws IOException          the io exception
     * @throws InterruptedException the interrupted exception
     */
    public static VoteResponse voteOnQuestion(Integer questionId) throws IOException, InterruptedException {
        VoteRequest vreq = new VoteRequest(ds.getUserId().toString(), questionId);
        String vreqJson = objectMapper.writeValueAsString(vreq);

        HttpRequest request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(vreqJson))
                .header("content-type", "application/json")
                .uri(URI.create(ds.getServiceEndpoint() + "/lectures/" + ds.getLectureId() + "/questions/"
                        + questionId + "/votes"))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            System.out.println("Status: " + response.statusCode());
        }

        return objectMapper.readValue(response.body(), VoteResponse.class);
    }

    /**
     * Ban ip ban ip response.
     *
     * @param questionId the question id
     * @param userIp     the user ip
     * @return the ban ip response
     * @throws IOException          the io exception
     * @throws InterruptedException the interrupted exception
     */
    public static BanIpResponse banIp(String questionId, String userIp) throws IOException, InterruptedException {



        BanIpRequest bir = new BanIpRequest(userIp);

        String birJson = objectMapper.writeValueAsString(bir);

        HttpRequest request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(birJson))
                .header("content-type", "application/json")
                .uri(URI.create(
                        ds.getServiceEndpoint()
                                + "/lectures/"
                                + ds.getLectureId()
                                + "/questions/"
                                + questionId
                                + "/ban"
                        )
                )
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            System.out.println("Status:" + response.statusCode());
        }

        return objectMapper.readValue(response.body(), BanIpResponse.class);
    }


    /**
     * Get metadata about the lecture.
     * @return - GetLectureMetadataResponse
     * @throws IOException -
     * @throws InterruptedException -
     */
    public static GetLectureMetadataResponse getLectureMetadata() throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .header("content-type", "application/json")
                .uri(URI.create(ds.getServiceEndpoint() + "/lectures/"
                        + ds.getLectureId()))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        return objectMapper.readValue(response.body(), GetLectureMetadataResponse.class);
    }

    /**
     * Get the current state of the total feedback given by the students for the current lecture.
     * @return GetLectureFeedbackResponse object with a map of aforementioned feedback.
     * @throws IOException If there is an issue in parsing the response.
     * @throws InterruptedException If there is an issue during the retrieval of the feedback.
     */
    public static GetLectureFeedbackResponse getLectureFeedback() throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .header("content-type", "application/json")
                .uri(URI.create(
                        ds.getServiceEndpoint()
                                + "/lectures/"
                                + ds.getLectureId()
                                + "/feedback?userId="
                                + ds.getUserId()
                ))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return objectMapper.readValue(response.body(), GetLectureFeedbackResponse.class);
    }

    /**
     * Add new feedback the current state of the total feedback given by the
     * students for the current lecture. If the user that is making use of this
     * method is a lecturer or moderator, the current state of the specific feedback
     * will be reset.
     * @param userId The userId of the user who is using this method.
     * @param lectureFeedbackCode The type of the feedback that is being sent.
     *                            Check LectureFeedbackCode for meaning.
     * @return A LectureFeedbackResponse which describes whether the operation was successful
     * @throws IOException If there is an issue during the parsing of the response.
     * @throws InterruptedException If there is an issue during the retrieval of a response.
     */
    public static LectureFeedbackResponse sendLectureFeedback(
            Long userId, Integer lectureFeedbackCode
    ) throws IOException, InterruptedException {
        LectureFeedbackRequest lfr = new LectureFeedbackRequest(userId, lectureFeedbackCode);
        String lfrJson = objectMapper.writeValueAsString(lfr);

        HttpRequest request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(lfrJson))
                .header("content-type", "application/json")
                .uri(URI.create(ds.getServiceEndpoint() + "/lectures/" + ds.getLectureId() + "/feedback"))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            System.out.println("Status: " + response.statusCode());
        }

        return objectMapper.readValue(response.body(), LectureFeedbackResponse.class);
    }


    /**
     * post Poll.
     * @param correctAnswer - the answer
     * @param optionCount - # options
     * @param title - title of poll
     * @return - A PostPollResponse which describes whether the operation was successful
     * @throws IOException - IO exception
     * @throws InterruptedException - interrupts
     */
    public static PostPollResponse postPoll(String correctAnswer, Integer optionCount, String title)
            throws IOException, InterruptedException {

        PostPollRequest postPollRequest = new PostPollRequest(optionCount, ds.getUserId().toString(), correctAnswer, title);
        String pprJson = objectMapper.writeValueAsString(postPollRequest);

        System.out.println(pprJson);
        HttpRequest request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(pprJson))
                .header("content-type", "application/json")
                .uri(URI.create(ds.getServiceEndpoint() + "/lectures/" + ds.getLectureId() + "/polls"))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            System.out.println("Status: " + response.statusCode());
        }

        System.out.println(objectMapper.readValue(response.body(), PostPollResponse.class).getMessage());

        return objectMapper.readValue(response.body(), PostPollResponse.class);
    }
}
