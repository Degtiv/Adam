package space.deg.adam.utils;

public class RequestsUtils {
    public static String redirectPage(String page) {
        return "redirect:/" + page;
    }

    public static String getAdminPage(String page) {
        return "administrative/" + page;
    }

    public static String getGoalPage(String page) {
        return "goals/" + page;
    }

    public static String getTransactionPage(String page) {
        return "transactions/" + page;
    }

    public static String getRulePage(String page) {
        return "rules/" + page;
    }

    public static String getErrorPage(String page) {
        return "errors/" + page;
    }

    public static String getOverviewPage(String page) {
        return "overview/" + page;
    }

    public static String getSettingsPage(String page) {
        return "settings/" + page;
    }
}
