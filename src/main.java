import logic.*;
public class main {
    public static void main(String[] args) {
        //testing , use of model in java ;
      var i =  new logic.auth().Login("admin","13");
        System.out.println(i.getSuccess());
        System.out.println(i.getMessage());
       
    }
    
}
