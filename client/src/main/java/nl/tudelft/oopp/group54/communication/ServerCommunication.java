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
import nl.tudelft.oopp.group54.models.requestentities.JoinLectureRequest;
import nl.tudelft.oopp.group54.models.requestentities.PostAnswerRequest;
import nl.tudelft.oopp.group54.models.requestentities.PostQuestionRequest;
import nl.tudelft.oopp.group54.models.requestentities.VoteRequest;

import nl.tudelft.oopp.group54.models.responseentities.BanIpResponse;
import nl.tudelft.oopp.group54.models.responseentities.CreateLectureResponse;
import nl.tudelft.oopp.group54.models.responseentities.DeleteQuestionResponse;
import nl.tudelft.oopp.group54.models.responseentities.EndLectureResponse;
import nl.tudelft.oopp.group54.models.responseentities.GetAllQuestionsResponse;
import nl.tudelft.oopp.group54.models.responseentities.JoinLectureResponse;
import nl.tudelft.oopp.group54.models.responseentities.PostAnswerResponse;
import nl.tudelft.oopp.group54.models.responseentities.PostQuestionResponse;
import nl.tudelft.oopp.group54.models.responseentities.VoteResponse;

public class ServerCommunication {

    private static HttpClient client = HttpClient.newBuilder().build();
    private static Datastore ds = Datastore.getInstance();
    // private static RequestBuilder requestBuilder = new RequestBuilder();
    private static ObjectMapper objectMapper = new ObjectMapper();


    /**
     * Posts a new lecture.
     * @param startTimestamp time of new lecture
     * @param lectureName name of new lecture
     * @return a response
     * @throws IOException something happened with input/output
     * @throws InterruptedException something interrupted the method
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
     * Joins a new lecture.
     * @param userName name of User who is joining
     * @param lectureId ID of lecture
     * @param joinId link that also describes the User's role
     * @return a response
     * @throws IOException something happened with input/output
     * @throws InterruptedException something interrupted the method
     */
    public static JoinLectureResponse joinLecture(String userName, Integer lectureId, String joinId)
            throws IOException, InterruptedException {

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
     * Posts a question in a lecture.
     * @param questionText content
     * @return a response
     * @throws IOException something happened with input/output
     * @throws InterruptedException something interrupted the method
     */
    public static PostQuestionResponse postQuestion(String questionText) throws IOException, InterruptedException {

        PostQuestionRequest pqr = new PostQuestionRequest(questionText, ds.getUserId().toString(), ds.getUserIp().toString());
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
     * Returns all questions from server.
     * @return a response
     * @throws IOException something happened with input/output
     * @throws InterruptedException something interrupted the method
     */
    public static GetAllQuestionsResponse getAllQuestions() throws IOException, InterruptedException {

        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(ds.getServiceEndpoint() + "/lectures/"
                        + ds.getLectureId()
                        + "/questions?userId="
                        + ds.getUserId()))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            System.out.println("Status: " + response.statusCode());
        }


        return objectMapper.readValue(response.body(), GetAllQuestionsResponse.class);
    }

    /**
     * Lecturer/Moderator posts an answer.
     * @param questionId used to identify the question
     * @param answerText content of answer text
     * @return a response
     * @throws IOException something happened with input/output
     * @throws InterruptedException something interrupted the method
     */
    public static PostAnswerResponse postAnswer(String questionId, String answerText) throws IOException, InterruptedException {

        PostAnswerRequest par = new PostAnswerRequest(ds.getUserId().toString(), answerText);
        String parJson = objectMapper.writeValueAsString(par);


        HttpRequest request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(parJson))
                .header("content-type", "application/json")
                .uri(URI.create(ds.getServiceEndpoint() + "/lectures/"
                        + ds.getLectureId()
                        + "/questions/"
                        + questionId
                        + "/answer"))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            System.out.println("Status: " + response.statusCode());
        }

        return objectMapper.readValue(response.body(), PostAnswerResponse.class);
    }

    /**
     * Deletes a question.
     * @param questionId used to identify a question
     * @return a response
     * @throws IOException something happened with input/output
     * @throws InterruptedException something interrupted the method
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
     * Ends a lecture.
     * @return a response
     * @throws IOException something happened with input/output
     * @throws InterruptedException something interrupted the method
     */
    public static EndLectureResponse endLecture() throws IOException, InterruptedException {

        HttpRequest request = HttpRequest.newBuilder()
                .PUT(HttpRequest.BodyPublishers.ofString(""))
                .header("content-type", "application/json")
                .uri(URI.create(ds.getServiceEndpoint()
                        + "/lectures/e/"
                        + ds.getLectureId()
                        + "?userId="
                        + ds.getUserId()))
                .build();


        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        return objectMapper.readValue(response.body(), EndLectureResponse.class);
    }


    /**
     * Vote on question.
     * @param questionId ID of question
     * @return a response
     * @throws IOException something happened with input/output
     * @throws InterruptedException something interrupted the method
     */
    public static VoteResponse voteOnQuestion(Integer questionId) throws IOException, InterruptedException {
        VoteRequest vreq = new VoteRequest(ds.getUserId().toString(), questionId);
        String vreqJson = objectMapper.writeValueAsString(vreq);

        HttpRequest request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(vreqJson))
                .header("content-type", "application/json")
                .uri(URI.create(ds.getServiceEndpoint() + "/lectures/"
                        + ds.getLectureId()
                        + "/questions/"
                        + questionId
                        + "/votes"))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            System.out.println("Status: " + response.statusCode());
        }

        return objectMapper.readValue(response.body(), VoteResponse.class);
    }

    /**
     * Bans a User by question.
     * @param questionId ID of the question
     * @return a response
     * @throws IOException something happened with input/output
     * @throws InterruptedException something interrupted the method
     */
    public static BanIpResponse banIp(String questionId) throws IOException, InterruptedException {
        BanIpRequest bir = new BanIpRequest(ds.getUserIp().toString());

        String birJson = objectMapper.writeValueAsString(bir);

        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .header("content-type", "application/json")
                .uri(URI.create(ds.getServiceEndpoint() + "/lectures/"
                        + ds.getLectureId() + "/questions/" + questionId))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            System.out.println("Status:" + response.statusCode());
        }

        return objectMapper.readValue(response.body(), BanIpResponse.class);
    }
}
