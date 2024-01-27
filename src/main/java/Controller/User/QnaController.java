package Controller.User;

import Controller.User.QnaLogic.QnaLogic;
import HTTPModel.Request;
import HTTPModel.Response;
import HTTPModel.Session;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class QnaController {

    private String URI;

    private Request request;

    private HTTPModel.Session Session = new Session();

    private static final Logger logger = LoggerFactory.getLogger(webserver.RequestHandler.class);

    public QnaController(String URI, Request request)
    {
        this.URI = URI;
        this.request = request;
    }

    public Response QnaLogic(Request request){

        Response response = new Response();
        String sid = request.GetSid();

        User logined_user = null;

        if(sid != null){
            logined_user = Session.getSession(sid);
        }

        try{

            if("show.html".equals(URI)){
                response = QnaLogic.qnaShow(request, logined_user);
            }else if("form.html".equals(URI)){
                response = QnaLogic.qnaForm(request, logined_user);
            }

        }catch(Exception e){


        }
        return response;


    }

}
