package Controller.User;

import Controller.User.UserLogic.UserLogic;
import Functions.FileBytes;
import HTTPModel.HttpStatus;
import HTTPModel.Request;
import HTTPModel.Response;
import SessionManager.SessionManager;
import model.User;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class UserController {

    private SessionManager sessionManager = SessionManager.getInstance();
    private String URI;
    private Request request;

    private static final String filePath = "./src/main/resources/templates";

    private static Map<String, String> URLMapping;

    static {
        URLMapping = new HashMap<>();
        URLMapping.put("form.html", "userForm");
        URLMapping.put("loginPost", "userLoginPost");
        URLMapping.put("create", "userSignup");
        URLMapping.put("login.html", "userLogin");
        URLMapping.put("list", "userList");
        URLMapping.put("profile.html", "userProfile");
    }

    //private Session Session = new Session();

    private static final Logger logger = LoggerFactory.getLogger(webserver.RequestHandler.class);
    public UserController(String URI, Request request)
    {
        this.URI = URI;
        this.request = request;

    }

    public Response UserLogic(Request request) {

        byte[] body;
        Response response = new Response();
        String sid = request.GetSid();
        User logined_user = null;

        if(sid != null){
            logined_user = sessionManager.getSession().getSession(sid);
        }

        try {

            String functionName = URLMapping.get(URI);
            if(functionName == null){
                body = FileBytes.FilesreadAllBytes(filePath + "/404.html", null, true);
                response.Setbody(body);
                response.SetHttpStatus(HttpStatus.OK);

            }else {
                Class<?> userLogicClass = UserLogic.class;
                Method method = userLogicClass.getDeclaredMethod(functionName, Request.class, User.class);
                method.setAccessible(true);
                UserLogic userLogicInstance = new UserLogic();

                response = (Response) method.invoke(userLogicInstance, request, logined_user);
            }

        }catch(Exception e){
            logger.debug(String.valueOf(e));
        }
        return response;
    }

}
