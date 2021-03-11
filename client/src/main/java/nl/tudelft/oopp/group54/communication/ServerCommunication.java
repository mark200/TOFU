package nl.tudelft.oopp.group54.communication;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import nl.tudelft.oopp.group54.Datastore;
import nl.tudelft.oopp.group54.models.requestentities.CreateLectureRequest;
import nl.tudelft.oopp.group54.models.requestentities.JoinLectureRequest;
import nl.tudelft.oopp.group54.models.requestentities.PostQuestionRequest;
import nl.tudelft.oopp.group54.models.responseentities.CreateLectureResponse;
import nl.tudelft.oopp.group54.models.responseentities.JoinLectureResponse;
import nl.tudelft.oopp.group54.models.responseentities.PostQuestionResponse;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ServerCommunication {

    private static HttpClient client = HttpClient.newBuilder().build();
    private static Datastore ds = Datastore.getInstance();
//    private static RequestBuilder requestBuilder = new RequestBuilder();
    private static ObjectMapper objectMapper = new ObjectMapper();


    public static CreateLectureResponse postLecture(Long startTimestamp, String lectureName)
            throws IOException, InterruptedException {

        CreateLectureRequest clr = new CreateLectureRequest(lectureName, startTimestamp);
        String clrJson = objectMapper.writeValueAsString(clr);

        HttpRequest request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(clrJson))
                .header("content-type", "application/json")
                .uri(URI.create(ds.getServiceEndpoint()+"/lectures"))
                .build();

        HttpResponse<String> response = null;

        response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            System.out.println("Status: " + response.statusCode());
        }

        return objectMapper.readValue(response.body(), CreateLectureResponse.class);
    }

    public static JoinLectureResponse joinLecture(String userName, Long lectureId, Long joinId) throws IOException, InterruptedException {
        
    	JoinLectureRequest jlr = new JoinLectureRequest(userName, lectureId, joinId);
        String jlrJson =  objectMapper.writeValueAsString(jlr);
       
        HttpRequest request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(jlrJson))
                .header("content-type", "application/json")
                .uri(URI.create(ds.getServiceEndpoint()+"/lectures/j/" + lectureId + "/" + joinId))
                .build();
        
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        
        if (response.statusCode() != 200) {
            System.out.println("Status: " + response.statusCode());
        }

        return objectMapper.readValue(response.body(), JoinLectureResponse.class);
    }
    
    public static PostQuestionResponse postQuestion(String questionText) throws IOException, InterruptedException {
    	
    	PostQuestionRequest pqr = new PostQuestionRequest(questionText, ds.getUserId());
    	String pqrJson = objectMapper.writeValueAsString(pqr);
    	
    	
    	System.out.println("sent: " + pqrJson);
    	
    	HttpRequest request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(pqrJson))
                .header("content-type", "application/json")
                .uri(URI.create(ds.getServiceEndpoint()+"/lectures/" + ds.getLectureId() + "/questions"))
                .build();
    	
    	HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        
        if (response.statusCode() != 200) {
            System.out.println("Status: " + response.statusCode());
        }
        
        System.out.println("recieved: " + response.body());
    	
    	return objectMapper.readValue(response.body(), PostQuestionResponse.class);
    }
}
