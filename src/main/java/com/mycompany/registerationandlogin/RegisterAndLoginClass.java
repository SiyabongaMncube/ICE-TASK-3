package com.mycompany.registerationandlogin;




public class RegisterAndLoginClass {

    //Stored credentials (set on successful registration) 
    private String registeredUserName;
    private String registeredPassword;
    private String registeredCellPhone;

    //Username validation 
    /*
      Checks that the username contains an underscore and is ≤ 5 characters
      (excluding the underscore), as per common QuickChat requirements.
     */
    public boolean checkUserName(String userName) {
        return userName.contains("_") && userName.length() <= 5;
    }

    public String getUserNameMessage(String userName) {
        if (checkUserName(userName)) {
            return "Username successfully captured.";
        } else {
            return "Username is not correctly formatted. Please ensure that your username "
                    + "contains an underscore and is no more than five characters in length.";
        }
    }

    //Password validation
    /*
      Password must be ≥ 8 characters, contain a capital letter, a number,
      and a special character.
     */
    public boolean checkPasswordComplexity(String password) {
        if (password.length() < 8) return false;

        boolean hasUpper   = false;
        boolean hasDigit   = false;
        boolean hasSpecial = false;

        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c))              hasUpper   = true;
            else if (Character.isDigit(c))             hasDigit   = true;
            else if (!Character.isLetterOrDigit(c))    hasSpecial = true;
        }
        return hasUpper && hasDigit && hasSpecial;
    }

    public String getPasswordMessage(String password) {
        if (checkPasswordComplexity(password)) {
            return "Password successfully captured.";
        } else {
            return "Password is not correctly formatted. Please ensure that the password "
                    + "contains at least eight characters, a capital letter, a number, "
                    + "and a special character.";
        }
    }

    // Cell-number validation
    /*
      South African international format: starts with +27, total length 12.
     */
    public boolean checkCellPhoneNumber(String cellPhoneNumber) {
        return cellPhoneNumber.startsWith("+27") && cellPhoneNumber.length() == 12;
    }

    //Registration 
    public String registerUser(String userName, String password, String cellPhoneNumber) {
        if (!checkUserName(userName)) {
            return "Username is not correctly formatted; please ensure the username "
                    + "contains an underscore and is no more than five characters in length.";
        }
        if (!checkPasswordComplexity(password)) {
            return "Password is not correctly formatted; please ensure the password "
                    + "contains at least eight characters, a capital letter, a number, "
                    + "and a special character.";
        }
        if (!checkCellPhoneNumber(cellPhoneNumber)) {
            return "Cell phone number is incorrectly formatted or does not contain an "
                    + "international code.";
        }

        //All checks passed persist credentials
        this.registeredUserName  = userName;
        this.registeredPassword  = password;
        this.registeredCellPhone = cellPhoneNumber;
        return "User has been registered successfully.";
    }

    //Login
    public boolean loginUser(String enteredUserName, String enteredPassword) {
        return registeredUserName  != null && registeredUserName.equals(enteredUserName) && registeredPassword.equals(enteredPassword);
    }

    public String returnLoginStatus(boolean loginSuccessful) {
        if (loginSuccessful) {
            return "Welcome " + registeredUserName + ", it is great to see you again.";
        } else {
            return "Username or password incorrect, please try again.";
        }
    }

    //Getters (used by QuickChatApp after login)
    public String getRegisteredUserName()  { 
        return registeredUserName;  
    }
    
    public String getRegisteredCellPhone() {
        return registeredCellPhone; 
    }
}
    
    

