package util;

import play.i18n.Messages;
import play.mvc.Http;

/**
 * Flash message helper
 */
public class Message {
    public static final String MESSAGE = "message";
    public static final String MESSAGE_TYPE = "message_type";

    private static Http.Flash flash() {
        return Http.Context.current().flash();
    }

    public static void error(){
        danger(Messages.get("error.message"));
    }

    public static void success(){
        success(Messages.get("success.message"));
    }

    public static void danger(String text){
        flash().put(MESSAGE, text);
        flash().put(MESSAGE_TYPE, "danger");
    }

    public static void info(String text){
        flash().put(MESSAGE, text);
        flash().put(MESSAGE_TYPE, "info");
    }

    public static void warning(String text){
        flash().put(MESSAGE, text);
        flash().put(MESSAGE_TYPE, "warning");
    }

    public static void success(String text){
        flash().put(MESSAGE, text);
        flash().put(MESSAGE_TYPE, "success");
    }
}
