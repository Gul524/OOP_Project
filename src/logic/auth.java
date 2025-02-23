package logic;
import models.authResponse;
import java.lang.String;

public class auth {

    public authResponse Login(String username , String password){
        if(username.equals("admin")  && password.equals("123")){
          return new authResponse(true , "Login Successful!");
        }
        if(!(username.equals("admin"))  && !(password.equals("123"))){
            return new authResponse(false , "Invalid username and password!");
        }
        if(!username.equals("admin")){
            return new authResponse(false , "Invalid username");
        }
        else {
            return new authResponse(false , "Invalid password");
        }

    }
}
