package Controller.User.QnaLogic;

import Functions.FileBytes;
import HTTPModel.HttpStatus;
import HTTPModel.Request;
import HTTPModel.Response;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class QnaLogic {

    private static final Logger logger = LoggerFactory.getLogger(webserver.RequestHandler.class);

    private static final String qnaFile = "./src/main/resources/templates/qna";
    private static final String filePath = "./src/main/resources/templates";



    public static Response qnaShow(Request request, User logined_user){

        byte [] body;
        body = FileBytes.FilesreadAllBytes(qnaFile + "/show.html", logined_user, true);
        Response response = new Response(HttpStatus.OK);
        response.Setbody(body);
        return response;
    }


    public static Response qnaForm(Request request, User logined_user){

        byte [] body;
        body = FileBytes.FilesreadAllBytes(qnaFile + "/form.html", logined_user, true);
        Response response = new Response(HttpStatus.OK);
        response.Setbody(body);
        return response;
    }

}
