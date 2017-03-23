package sdu.wocl.exception;

public class FileExistException extends Exception{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public FileExistException() {
	super();
    }

    @Override
    public String getMessage() {
	return "This type is already exist";
    }

}
