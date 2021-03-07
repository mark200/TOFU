package nl.tudelft.oopp.group54.communication;

import com.fasterxml.jackson.databind.ObjectMapper;

import nl.tudelft.oopp.group54.Datastore;
import nl.tudelft.oopp.group54.models.requestentities.CreateLectureRequest;
import nl.tudelft.oopp.group54.models.responseentities.CreateLectureResponse;

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

}
