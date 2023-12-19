package org.packman.client.utils;

import org.packman.client.models.AppUser;

import java.io.PipedOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ParseUtil {
    public static List<AppUser> toListBestPlayers(String input) {
        List<AppUser> appUsers = new ArrayList<>();
        String[] userStrings = input.split(";");

        for (String userString : userStrings) {
            String[] userFields = userString.trim().split(",");

            if (userFields.length == 2) {
                String username = userFields[0].trim();
                int points = Integer.parseInt(userFields[1].trim());

                AppUser appUser = new AppUser(username, points);
                appUsers.add(appUser);
            } else {
                // Обработка некорректных данных
                //todo throw ex
            }
        }
        return appUsers;
    }

    public static List<int[]> toMap(String input) {
        List<int[]> resultList = new ArrayList<>();

        input = input.replaceAll("[\\[\\]\\s]", "");

        String[] arrayStrings = input.split(",");

        for (String arrayString : arrayStrings) {
            String[] values = arrayString.split(";");
            int[] array = Arrays.stream(values)
                    .mapToInt(Integer::parseInt)
                    .toArray();
            resultList.add(array);
        }

        return resultList;
    }

    public static String[] parseStrToArray(String input) {
        return input.split(" ");
       /* String[] result = new String[splitStr.length - 1];
        for (int i = 0; i < splitStr.length - 1; i++) {
            result[i] = splitStr[i + 1];
        }
        return result;*/
    }
}
