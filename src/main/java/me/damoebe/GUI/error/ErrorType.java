package me.damoebe.GUI.error;

public enum ErrorType {

    NONE("No Problem :)"),
    INVALID_SIZE("The height and width must be between 10 and 200."),
    NAME_ALREADY_USED("This name is already used."),
    PATH_DOES_NOT_EXIST("This path does not exist."),
    MISSING_PARAMETERS("You need to fill out all fields.");

    private final String message;

    ErrorType(String message){
        this.message = message;
    }

    public String getMessage(){
        return this.message;
    }
}
