package services;

public class StringService {

    public static String convertIds(String[] ids) {
        StringBuilder builder = new StringBuilder();
        for (String id : ids) {
            builder.append("'").append(id).append("', ");
        }
        builder.setLength(builder.length() - 2);
        return builder.toString();
    }
}
