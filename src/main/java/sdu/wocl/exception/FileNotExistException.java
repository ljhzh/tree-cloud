package sdu.wocl.exception;

public class FileNotExistException extends Exception{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public FileNotExistException() {
	super();
    }

    @Override
    public String getMessage() {
	return "This type has not been created";
    }

}
