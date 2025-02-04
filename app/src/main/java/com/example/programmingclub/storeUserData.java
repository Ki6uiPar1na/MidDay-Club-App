package com.example.programmingclub;

public class storeUserData {
    private String name, email, session, regNumber, codeforcesHandle, codechefHandle, leetcodeHandle, password;

    public storeUserData(String name, String email, String session, String regNumber,
                         String codeforcesHandle, String codechefHandle, String leetcodeHandle, String password) {
        this.name = name;
        this.email = email;
        this.session = session;
        this.regNumber = regNumber;
        this.codeforcesHandle = codeforcesHandle;
        this.codechefHandle = codechefHandle;
        this.leetcodeHandle = leetcodeHandle;
        this.password = password;
    }


    // Getters
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getSession() { return session; }
    public String getRegNumber() { return regNumber; }
    public String getCodeforcesHandle() { return codeforcesHandle; }
    public String getCodechefHandle() { return codechefHandle; }
    public String getLeetcodeHandle() { return leetcodeHandle; }
    public String getPassword() { return password; }

}
