package Functions;

import db.Database;
import db.QnaDatabase;
import model.Qna;
import model.User;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class QnaFileBytes {

    private String fileURL;


    public static byte[] FilesreadAllBytes(String fileURL, String qnaId, User user) {

        byte[] body = new byte[0];

        StringBuilder content = new StringBuilder();


        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(fileURL), StandardCharsets.UTF_8))) {
            String line;
            boolean imbed = false;

            while ((line = reader.readLine()) != null) {

                if(line.contains("<h2 class=\"qna-title\">")){
                    String title = QnaDatabase.findQnaById(qnaId).getTitle();
                    content.append("<h2 class=\"qna-title\">").append(title).append("</h2>");
                    continue;
                }
                if(line.contains("<a href=\"/users/92/kimmunsu\" class=\"article-author-name\">")){
                    String writer = QnaDatabase.findQnaById(qnaId).getUser().getName();
                    String date = QnaDatabase.findQnaById(qnaId).getDate();
                    content.append("<a href=\"/users/").append(qnaId).append("\\/").append(writer).append("\" class=\"article-author-name\">").append(writer).append("</a>");
                    continue;
                }
                if(line.contains("2015-12-30 01:47")){
                    String date = QnaDatabase.findQnaById(qnaId).getDate();
                    content.append(date);
                    continue;
                }


                content.append(line).append(System.lineSeparator());


                if(line.contains("<div class=\"article-doc\">")){
                    String contents = QnaDatabase.findQnaById(qnaId).getContents();
                    content.append("<p>").append(contents).append("</p>");
                }

                if (user != null && line.contains("<ul class=\"nav navbar-nav navbar-right\">") && !imbed) {
                    content.append("<li>");
                    content.append("<div class=\"name\">").append(user.getName()).append(" 님 ").append("</div>");
                    content.append("</li>");
                    imbed = true;
                }

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return content.toString().getBytes(StandardCharsets.UTF_8);
    }
}