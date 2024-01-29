package Controller.User;

import Controller.User.QnaLogic.QnaLogic;
import Functions.FileBytes;
import HTTPModel.HttpStatus;
import HTTPModel.Request;
import HTTPModel.Response;
import HTTPModel.Session;
import SessionManager.SessionManager;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class QnaController {


    private SessionManager sessionManager = SessionManager.getInstance();

    private String URI;

    private static final String filePath = "./src/main/resources/templates";
    private Request request;


    private static final Logger logger = LoggerFactory.getLogger(webserver.RequestHandler.class);

    public QnaController(String URI, Request request)
    {
        this.URI = URI;
        this.request = request;
    }

    public Response QnaLogic(Request request){
        byte[] body;

        Response response = new Response();
        String sid = request.GetSid();

        User logined_user = null;

        if(sid != null){
            logined_user = sessionManager.getSession().getSession(sid);
        }

        try{
            if(URI.contains("show")){
                response = QnaLogic.qnaShow(request, logined_user);
            }else if("form.html".equals(URI)){
                response = QnaLogic.qnaForm(request, logined_user);
            }else if("post".equals(URI)){
                response = QnaLogic.qnaPost(request, logined_user);
            }else{
                body = FileBytes.FilesreadAllBytes(filePath + "/404.html", null, true);
                response.Setbody(body);
                response.SetHttpStatus(HttpStatus.OK);
            }

        }catch(Exception e){
            logger.debug(String.valueOf(e));

        }
        return response;


    }

}
