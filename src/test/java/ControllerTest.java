
import Controller.User.UserController;
import db.Database;
import model.User;
import org.junit.jupiter.api.Test;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ControllerTest {


    @Test
    public void indexTest() throws Exception{

        String apiUrl = "http://localhost:8080/index.html";
        HttpClient httpClient = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(apiUrl))
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response.statusCode());

    }


    @Test
    public void signupEnterTest() throws Exception{

        String apiUrl = "http://localhost:8080/user/form.html";
        HttpClient httpClient = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(apiUrl))
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response.statusCode());

    }


    @Test
    public void signup() throws Exception{

        String apiUrl = "http://localhost:8080/user/create?userId=1&password=1234&name=a&email=a%40naver.com";
        HttpClient httpClient = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(apiUrl))
                .GET()
                .build();

       // UserController userController = new UserController("create");
        //byte[] body = userController.UserLogic();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());


        Collection<User> userList = Database.findAll();
        System.out.println(userList);

        //User user = Database.findUserById("1");

        assertEquals(1,userList.size());
        //assertEquals(user.getUserId(),1);
        //assertEquals(user.getName(),"a");
        //assertEquals(user.getEmail(),"a%40naver.com");
        //assertEquals(user.getPassword(),"1234");

    }

}
