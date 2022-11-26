package database.exceptions;

public class KeyNotFoundException extends RuntimeException {
    private final String key;
    public KeyNotFoundException(){
       key= "";
    }

    public KeyNotFoundException(String key){
        this.key = key;
    }

    public String getKey(){
        return key;
    }
}
