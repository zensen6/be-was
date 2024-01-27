package Controller.User.UserLogic;

import Functions.FileBytes;
import HTTPModel.HttpStatus;
import HTTPModel.Request;
import HTTPModel.Response;
import HTTPModel.Session;
import db.Database;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserLogic {

    private static final Logger logger = LoggerFactory.getLogger(webserver.RequestHandler.class);

    private final String userFile = "./src/main/resources/templates/user";
    private String filePath = "./src/main/resources/templates";

    private HTTPModel.Session Session = new Session();

    byte [] body;
    public Response userForm(Request request, Response response, User logined_user){

        body = FileBytes.FilesreadAllBytes(filePath + "/user/form.html", logined_user, true);
        response.SetHttpStatus(HttpStatus.OK);
        response.Setbody(body);
        return response;

    }

    public Response userLoginSuccess(Request request, Response response, User logined_user){
        String body_str = request.GetBody();

        String userId = body_str.split("&")[0].split("=")[1];
        String password = body_str.split("&")[1].split("=")[1];

        User user = Database.findUserById(userId);

        if(user != null && password.equals(user.getPassword()) && userId.equals(user.getUserId())){ // 로그인 성공
            body = FileBytes.FilesreadAllBytes(filePath + "/index.html", logined_user, true);
            response.SetSid();
            response.SetSidSet();
            Session.addSession(response.getSid(),user);
            response.SetRedirectUrl(HttpStatus.REDIRECT, "/index.html");

            logger.debug("login SUCCEESSSS: ");


        }else{
            body = FileBytes.FilesreadAllBytes(userFile + "/login_failed.html", logined_user, true);
            response.SetHttpStatus(HttpStatus.OK);
        }
        response.Setbody(body);

        return response;
    }

    public Response userSignup(Request request, Response response, User logined_user){

        String body_str = request.GetBody();
        String userId = body_str.split("&")[0].split("=")[1];
        String password = body_str.split("&")[1].split("=")[1];
        String name = body_str.split("&")[2].split("=")[1];
        String email = body_str.split("&")[3].split("=")[1];

        User finduser = Database.findUserById(userId);

        if(finduser == null){
            User user = new User(userId, password, name, email);
            Database.addUser(user);

            body = FileBytes.FilesreadAllBytes(filePath + "/index.html", logined_user, true);

            response.SetRedirectUrl(HttpStatus.REDIRECT, "/index.html");

        }else{

            body = FileBytes.FilesreadAllBytes(userFile + "/signup_failed.html", logined_user, true);
            response.SetHttpStatus(HttpStatus.OK);
        }

        response.Setbody(body);
        return response;

    }


    public Response userLogin(Request request, Response response, User logined_user){
        body = FileBytes.FilesreadAllBytes(filePath + "/user/login.html", logined_user, true);
        response.SetHttpStatus(HttpStatus.OK);
        response.Setbody(body);
        return response;
    }

    public Response userList(Request request, Response response, User logined_user){
        if(logined_user == null){
            body = FileBytes.FilesreadAllBytes(filePath + "/user/login.html", null, true);
            response.SetRedirectUrl(HttpStatus.REDIRECT, "/user/login.html");
        }else {
            body = FileBytes.FilesreadAllBytes(filePath + "/user/list.html", logined_user, true);
            response.SetHttpStatus(HttpStatus.OK);
        }
        response.Setbody(body);
        return response;
    }


}
