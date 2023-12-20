package org.packman.client.utils;

import org.packman.client.models.AppUser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParseUtil {
    public static List<AppUser> toListBestPlayers(String input) {
        List<AppUser> userList = new ArrayList<>();

        Pattern pattern = Pattern.compile("\\[([^\\]]+)\\]");
        Matcher matcher = pattern.matcher(input);

        while (matcher.find()) {
            String arrayString = matcher.group(1);
            String[] arrays = arrayString.split(", ");
            for (String str : arrays) {
                String[] parts = str.split(",");
                String username = parts[0];
                int countPoints = Integer.parseInt(parts[1]);
                AppUser user = new AppUser(username, countPoints);
                userList.add(user);
            }
        }
        return userList;
    }

    public static List<int[]> toMap(String input) {
        List<int[]> resultList = new ArrayList<>();

        Pattern pattern = Pattern.compile("\\[([^\\]]+)\\]");
        Matcher matcher = pattern.matcher(input);

        while (matcher.find()) {
            String arrayString = matcher.group(1);
            String[] arrays = arrayString.split(", ");
            for (String str : arrays) {
                int[] array = Arrays.stream(str.split(","))
                        .mapToInt(Integer::valueOf)
                        .toArray();
                resultList.add(array);
            }
        }
        return resultList;
    }

    public static String[] parseStrToArray(String input) {
        return input.split("\\s{2}");
    }//todo!!Pattern
}
