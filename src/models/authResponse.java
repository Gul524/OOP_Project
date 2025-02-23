package models;

public class authResponse {
    boolean success ;
    String  message;

     public authResponse(boolean success, String message){
         this.success = success;
         this.message = message;
     }

    public boolean getSuccess() {
        return success;
    }
    public String getMessage(){
         return message;
    }
}
