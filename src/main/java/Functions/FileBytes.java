package Functions;

import db.Database;
import model.User;

import java.io.*;
import java.util.List;

public class FileBytes {

    private String fileURL;


    public static byte[] FilesreadAllBytes(String fileURL, User user, boolean isHtml){

        byte[] body = new byte[0];

        if(isHtml) {
            StringBuilder content = new StringBuilder();


            try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(fileURL), "UTF-8"))) {
                String line;
                boolean imbed = false;
                int currentUser = 0;
                while ((line = reader.readLine()) != null) {

                    if(imbed && line.contains("<li><a href=\"user/login.html\" role=\"button\">")){
                        continue;
                    }
                    content.append(line).append(System.lineSeparator());
                    //System.out.println("line:  " + line);



                    if (user != null && line.contains("<ul class=\"nav navbar-nav navbar-right\">") && !imbed) {
                        content.append("<li>");
                        content.append("<div class=\"name\">" + user.getName() + " 님 " +  "</div>");
                        content.append("</li>");
                        imbed = true;
                    }


                    ////////// user list



                    if(user != null && line.contains("</tr>")){
                        List<User> userList =  Database.findAll();

                        currentUser++;
                        System.out.println(currentUser);
                        if (currentUser == 3) {

                            for(User signedUser : userList) {
                                content.append("<tr>");
                                content.append("<th scope=\"row\">" + currentUser++ + "</th>");
                                content.append("<td>" +signedUser.getUserId() + "</td>");
                                content.append("<td>" +signedUser.getName() + "</td>");
                                content.append("<td>" +signedUser.getEmail() + "</td>");
                                content.append("<td>");
                                content.append("<a href=\"#\" class=\"btn btn-success\" role=\"button\">수정</a>");
                                content.append("</td>");
                                content.append("</tr>");
                            }
                        }

                    }




                }
                return content.toString().getBytes("UTF-8");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        else{

            try (FileInputStream fis = new FileInputStream(fileURL);
                 ByteArrayOutputStream bos = new ByteArrayOutputStream()) {

                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = fis.read(buffer)) != -1) {
                    bos.write(buffer, 0, bytesRead);
                }
                return body = bos.toByteArray();
            } catch (IOException e) {
                e.printStackTrace();
            }


        }

        return body;
    }


}
