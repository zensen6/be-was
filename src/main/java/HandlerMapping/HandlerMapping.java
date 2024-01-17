package HandlerMapping;

import Controller.User.UserController;
import DTO.Request;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import DTO.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.RequestHandler;


public class HandlerMapping {

    private Request requestDTO;
    private String filePath = "./src/main/resources/templates";
    private String staticfilePath = "./src/main/resources/static";
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    public HandlerMapping(Request requestDTO){
        this.requestDTO = requestDTO;
    }

    public Response Controller(){

        Response responseDTO = new Response();
        responseDTO.SetreturnType("text/html");

        byte[] body = null;
        try {
            String URI = requestDTO.GetURI();


            if("css".equals(URI.split("/")[1])){
                responseDTO.SetreturnType("text/css");
                body = Files.readAllBytes(new File(staticfilePath + "/css/" + URI.split("/")[2]).toPath());

            }else if("js".equals(URI.split("/")[1])){
                responseDTO.SetreturnType("application/javascript");
                body = Files.readAllBytes(new File(staticfilePath + "/js/" + URI.split("/")[2]).toPath());
            }else if("fonts".equals(URI.split("/")[1])){
                responseDTO.SetreturnType("application/font-woff");
                body = Files.readAllBytes(new File(staticfilePath + "/fonts/" + URI.split("/")[2]).toPath());
            }


            else if (URI.equals("/index.html")) {

                body = Files.readAllBytes(new File(filePath + "/index.html").toPath());

            }else if(URI.split("/")[1].equals("user")){


                UserController userController = new UserController(URI.split("/")[2]);
                body = userController.UserLogic();
            }

        }
        catch (IOException e) {
                logger.error(e.getMessage());
        }
        responseDTO.Setbody(body);
        return responseDTO;

    }



}
