package Controller.User;

import DTO.HttpStatus;
import DTO.Request;
import DTO.Response;
import com.sun.net.httpserver.HttpServer;
import db.Database;
import model.User;

import javax.xml.crypto.Data;
import java.io.File;
import java.nio.file.Files;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;




public class UserController {

    private String URI;

    private Request request;

    private byte[] body;

    private static final Logger logger = LoggerFactory.getLogger(webserver.RequestHandler.class);
    public UserController(String URI, Request request)
    {
        this.URI = URI;
        this.request = request;
    }

    private String userFile = "./src/main/resources/templates/user";
    private String filePath = "./src/main/resources/templates";



    public Response UserLogic() {

        Response response = new Response();
        try {
            if ("form.html".equals(URI)) {
                body = Files.readAllBytes(new File(filePath + "/user/form.html").toPath());
                response.SetHttpStatus(HttpStatus.OK);

            }else if("login".equals(URI)){
                String body_str = request.GetBody();
                logger.debug(body_str);
                String userId = body_str.split("&")[0].split("=")[1];
                String password = body_str.split("&")[1].split("=")[1];

                logger.debug("userId:" + userId);
                logger.debug("password:" + password);
                User user = Database.findUserById(userId);
                if(user != null && user.getPassword().equals(password)){ // 로그인 성공
                    logger.debug("logged in");
                    body = Files.readAllBytes(new File(filePath + "/index.html").toPath());
                    response.SetRedirectUrl(HttpStatus.REDIRECT, "/index.html");
                }else{
                    body = Files.readAllBytes(new File(userFile + "/login_failed.html").toPath());
                    response.SetHttpStatus(HttpStatus.OK);
                }

            }else if("create".equals(URI.substring(0,6))){

                String []args = URI.split("&");
                String userId = args[0].split("\\?")[1].split("=")[1];
                String password = args[1].split("=")[1];
                String name = args[2].split("=")[1];
                String email = args[3].split("=")[1];
                User user = new User(userId, password, name, email);

                User finduser = Database.findUserById(userId);
                if(finduser == null){
                    Database.addUser(user);
                    body = Files.readAllBytes(new File(userFile + "/login.html").toPath());

                    response.SetRedirectUrl(HttpStatus.REDIRECT, "/user/login.html");

                }else{
                    body = Files.readAllBytes(new File(userFile + "/signup_failed.html").toPath());

                    response.SetHttpStatus(HttpStatus.OK);
                }

            }else if("login.html".equals(URI)){
                body = Files.readAllBytes(new File(filePath + "/user/login.html").toPath());

                response.SetHttpStatus(HttpStatus.OK);

            }
        }catch(Exception e){

        }
        response.Setbody(body);
        return response;
    }

}
