package org.packman.client.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class AppUser {
    private String username;
    private int countPoints;

    @Override
    public String toString() {
        return username + ": " + countPoints;
    }
}
