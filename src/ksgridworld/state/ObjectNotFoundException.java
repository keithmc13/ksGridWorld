package ksgridworld.state;

/**
 * Created by sparr on 6/15/17.
 */
public class ObjectNotFoundException extends RuntimeException {
    private static final String DEFAULT_MESSAGE = "Burlap failed to find the requested object";
    public ObjectNotFoundException(){
        super(DEFAULT_MESSAGE);
    }
    public ObjectNotFoundException(String message){
        super(message);
    }
}

