import java.util.ArrayList;
import java.util.List;

public class Transfer {
    public static List<Builder> StringsToBuilders(List<String> lines) {

        if (lines == null || lines.isEmpty()) return null;
        List<Builder> builders = new ArrayList<>();

        for (String line : lines) {
            String[] words = line.split(",");
            if (words.length != 4) return null;
            Builder builder = new Builder();

            try {
                builder.setBuilderId(Integer.parseInt(words[0].trim()));
                builder.setReadiness(Integer.parseInt(words[3].trim()));
            } catch (NumberFormatException e) {
                return null;
            }

            builder.setDate(words[2].trim());
            builder.setObjectName(words[1].trim());
            builders.add(builder);
        }
        return builders;
    }

    public static List<String> BuilderToString(List<Builder> builders) {

        if (builders == null || builders.isEmpty()) return null;
        List<String> lines = new ArrayList<>();

        for (Builder builder : builders)
            lines.add(String.format("%5d, %10s, %10s, %5d", builder.getBuilderId(), builder.getObjectName(), builder.getDate(), builder.getReadiness()));
        return lines;
    }
}
