package com.mycompany.registerationandlogin;


public class RegisterAndLoginClass {
    private String firstName;
    private String lastName;
    private String userName;
    private String password;
    private String cellPhoneNumber;
    
    public boolean checkUsername(String username){//User validation
        return username.contains("_")&& username.length()<=5;
    }
    public boolean checkPasswordComplexity(String password){//Password validation
        boolean hasUpperCase = false;
        boolean hasNumber = false;
        boolean hasSpecial = false;
        
        for (char c : password.toCharArray()){
            if(Character.isUpperCase(c)) hasUpperCase = true;
            else if(Character.isDigit(c)) hasNumber = true;
            else if (!Character.isLetterOrDigit(c)) hasSpecial = true;
            
        }
        return password.length() >=8 && hasUpperCase && hasNumber && hasSpecial;
       
    }
    public boolean checkCellPhoneNumber(String cellPhoneNumber){//Cellphone Validation
        return cellPhoneNumber.startsWith("+27") && cellPhoneNumber.length() == 12;
    }
    public String registerUser(String username, String password,String cellphone){
        return registerUser(username, password);
    }

    public String registerUser(String username, String password) {//Password Check & Username Check
        if (!checkUsername(username)) {
            return "Username is not correctly formatted please ensure that your username contains an underscore and is no more than five characters in length.";
        }
        if (!checkPasswordComplexity(password)) {
            return "Password is not correctly formatted; please ensure that the password contains atleast eight characters, a capital letter, a number, and a special character";
        }
        if(!checkCellPhoneNumber(cellPhoneNumber)){
            return "Cell phone number incorrectly formatted or does not contain international code.";
        }
       this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.password = password;
        this.cellPhoneNumber = cellPhoneNumber;
        
        return"User registered successfully";
    }
    
     public boolean loginUser(String enteredUserName, String enteredPassword) {
        if (this.userName == null || this.password == null) {
            return false;
        }

        return this.userName.equals(enteredUserName) && this.password.equals(enteredPassword);
    }

    public String returnLoginStatus(boolean loginSuccessful) {
        if (loginSuccessful) {
            return "Welcome " + firstName + ", " + lastName + " it is great to see you again.";
        }

        return "Username or password incorrect, please try again.";
    }

    public String getUserNameMessage(String userName) {
        if (checkUsername(userName)) {
            return "Username successfully captured.";
        }else{
            return "Username is not correctly formatted; please ensure that your username contains an underscore and is no more than five characters in length.";
        }

        
    }

    public String getPasswordMessage(String password) {
        if (checkPasswordComplexity(password)) {
            return "Password successfully captured.";
        }else{
            return "Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.";
        }

        
    }

    public String getCellPhoneMessage(String cellPhoneNumber) {
        if (checkCellPhoneNumber(cellPhoneNumber)) {
            return "Cell number successfully captured.";
        }else{
            return "Cell number is incorrectly formatted or does not contain an international code; please correct the number and try again.";
        }

       
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getUserName() {
        return userName;
    }

    public String getCellPhoneNumber() {
        return cellPhoneNumber;
    }

    
    
}
