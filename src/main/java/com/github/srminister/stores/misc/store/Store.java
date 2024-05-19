package com.github.srminister.stores.misc.store;

import com.github.srminister.stores.utils.TimeFormatter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Store {

    private final String name;
    private long time;

    private int stars;
    private int visits;
    private String location;

    public String getTimeFormat() {
        return TimeFormatter.format(time - System.currentTimeMillis());
    }

    public boolean isExpired() {
        return time <= System.currentTimeMillis();
    }

    public void addStars(int stars) {
        this.stars += stars;
    }

    public void addVisits(int visits) {
        this.visits += visits;
    }

}
