package ro.agilehub.javacourse.car.hire.user.controller.error;

public class EmailNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public EmailNotFoundException(String msg) {
        super(msg);
    }
}
