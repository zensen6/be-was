package Functions;

import model.User;

import java.io.*;

public class FileBytes {

    private String fileURL;


    public static byte[] FilesreadAllBytes(String fileURL, User user, boolean isHtml){

        byte[] body = new byte[0];

        if(isHtml) {
            StringBuilder content = new StringBuilder();


            try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(fileURL), "UTF-8"))) {
                String line;
                boolean imbed = false;
                while ((line = reader.readLine()) != null) {

                    content.append(line).append(System.lineSeparator());
                    System.out.println("line:  " + line);
                    //line.contains("<a href=\"index.html\" class=\"navbar-brand\">SLiPP</a>")
                    if (user != null && line.contains("<ul class=\"nav navbar-nav navbar-right\">") && !imbed) {
                        System.out.println("User name : " + user.getName());
                        //content.append("<li class=\"name\">");
                        content.append("<li>");
                        content.append("<div class=\"name\">" + user.getName() + " 님 " +  "</div>");
                        content.append("</li>");
                        imbed = true;
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
                    //System.out.println("line:" + new String(buffer, 0, bytesRead, "UTF-8"));
                }

                return body = bos.toByteArray();
            } catch (IOException e) {
                e.printStackTrace();
            }


        }

        /*
        byte[] body = new byte[0];

        try (FileInputStream fis = new FileInputStream(fileURL);
             ByteArrayOutputStream bos = new ByteArrayOutputStream()) {

            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = fis.read(buffer)) != -1) {

                bos.write(buffer, 0, bytesRead);
                //System.out.println("line:" + new String(buffer, 0, bytesRead, "UTF-8"));
            }

            return body = bos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }

         */

        return body;
    }


}
