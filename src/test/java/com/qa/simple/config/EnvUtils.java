package utils;

public class EnvUtils {
    private EnvUtils() {}

    public static boolean isCi() {
        return System.getenv("CI") != null
                || System.getenv("GITHUB_ACTIONS") != null
                || System.getenv("GITLAB_CI") != null;
    }
}