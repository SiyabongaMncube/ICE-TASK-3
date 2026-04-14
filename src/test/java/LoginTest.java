

import com.mycompany.registerationandlogin.RegisterAndLoginClass;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class LoginTest {
    
   @Test
   public void TestcheckUsernamecorrect(){
   
       RegisterAndLoginClass user = new RegisterAndLoginClass();
      String expected = "User registered successfully";
      assertTrue(user.checkUsername("Kyl_1"));
       
      
      
       
       
       
   }
    @Test
    public void TestcheckUsernameincorrect(){
       RegisterAndLoginClass user = new RegisterAndLoginClass();
       String expected = "The username is incorrect";
       assertFalse(user.checkUsername("kyle!!!!!"));
       
      
    }
    @Test
    public void TestcheckPasswordcomplexitycorrect(){
    RegisterAndLoginClass user = new RegisterAndLoginClass();
    String expected = "Password correct";
    assertTrue(user.checkPasswordComplexity("Ch&&sec@ke99"));
   
}
  @Test
public void   TestcheckPassowrdcomplexityincorrect(){
    RegisterAndLoginClass user = new RegisterAndLoginClass();
    String expected ="Password incorrect";
    assertFalse(user.checkPasswordComplexity("password"));
}
 @Test
 public void TestcheckCellPhoneNumbercorrect(){
  RegisterAndLoginClass user = new RegisterAndLoginClass();
  String expected = "Valid";
  assertTrue(user.checkCellPhoneNumber("+27"));
 }
 @Test
 public void TestcheckCellPhoneNumberincorrect(){
 RegisterAndLoginClass user = new RegisterAndLoginClass();
 String expected ="invalid";
 assertFalse(user.checkCellPhoneNumber("089665537"));
    
 }
  @Test
  public void TestLoginSuccess(){
    RegisterAndLoginClass user = new RegisterAndLoginClass();
    user.registerUser("Kyl_1", "Ch&&sec@ke99", "+27");
    assertEquals(true,user.registerUser("Kyl_1", "Ch&&sec@ke99", "+27"));
   
  }
public void TestLoginSuccessincorrect(){
    RegisterAndLoginClass user = new RegisterAndLoginClass();
   user.registerUser("Kyl_1", "Ch&&sec@ke99", "+27");
   assertNotEquals(false, user.registerUser("wrong","wrong"));
}

}
