package util;

public class EnvironmentConstants {
    static final String ALLOWED_DEV_EMAILS = "ALLOWED_DEV_EMAILS";
    static final String ENV = "ENVIRONMENT";
    static final String ENV_DEVELOPMENT = "DEVELOPMENT";
    static final String ENV_PRODUCTION = "PRODUCTION";

    public static boolean isProductionEnvironment() {
        return System.getenv(EnvironmentConstants.ENV).equals(EnvironmentConstants.ENV_PRODUCTION);
    }
}
