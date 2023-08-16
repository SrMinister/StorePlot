package com.github.srminister.stores.data;

import net.hyren.core.misc.cache.Cache;

import java.util.List;
import java.util.stream.Collectors;

public class UserCache extends Cache<User> {

    public User getByUsername(String name) {
        return getCached(user -> user.getName().equalsIgnoreCase(name));
    }

    public List<User> getRanked() {
       return getCachedElements().stream()
                .sorted((o1, o2) -> Double.compare(o2.getStores().getStars(), o1.getStores().getStars()))
                .filter(location -> location.getStores().getLocation() != null)
                .collect(Collectors.toList());
    }

}
