package com.example.programmingclub;

public class storeUserData {
    private String name, email, session, regNumber, codeforcesHandle, codechefHandle, leetcodeHandle;

    public storeUserData(String name, String email, String session, String regNumber,
                         String codeforcesHandle, String codechefHandle, String leetcodeHandle) {
        this.name = name;
        this.email = email;
        this.session = session;
        this.regNumber = regNumber;
        this.codeforcesHandle = codeforcesHandle;
        this.codechefHandle = codechefHandle;
        this.leetcodeHandle = leetcodeHandle;
    }


    // Getters
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getSession() { return session; }
    public String getRegNumber() { return regNumber; }
    public String getCodeforcesHandle() { return codeforcesHandle; }
    public String getCodechefHandle() { return codechefHandle; }
    public String getLeetcodeHandle() { return leetcodeHandle; }
}
