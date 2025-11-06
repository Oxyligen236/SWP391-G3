package hrms.config;

public class Iconstant {

    public static String GOOGLE_CLIENT_ID = "58943187648-crqjkdmm3l59d2bk2qsfvgfn23buonso.apps.googleusercontent.com";
    public static String GOOGLE_CLIENT_SECRET = "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";
    public static String GOOGLE_REDIRECT_URI = "http://localhost:8080/hrms/authenticate";
    public static String GOOGLE_GRANT_TYPE = "authorization_code";
    public static String GOOGLE_LINK_GET_TOKEN = "https://oauth2.googleapis.com/token";
    public static String GOOGLE_LINK_GET_USER_INFO = "https://www.googleapis.com/oauth2/v1/userinfo?alt=json&access_token=";

    public static final int MIN_USERNAME_LENGTH = 4;
    public static final int MIN_PASSWORD_LENGTH = 6;

    public static double SALARY_MULTIPLY_FOR_OVERTIME = 1.5;
    public static double SALARY_MULTIPLY_FOR_HOLIDAY = 3.0;
}
