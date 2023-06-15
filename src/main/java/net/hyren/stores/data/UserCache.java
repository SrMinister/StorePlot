package net.hyren.stores.data;

import net.hyren.core.misc.cache.Cache;

import java.util.List;
import java.util.stream.Collectors;

public class UserCache extends Cache<User> {

    public User getByUsername(String username) {
        return getCached(user -> user.getUsername().equalsIgnoreCase(username));
    }

    public List<User> getRanked() {
       return getCachedElements().stream()
                .sorted((o1, o2) -> Double.compare(o2.getStores().getStars(), o1.getStores().getStars()))
                .filter(location -> location.getStores().getLocation() != null)
                .collect(Collectors.toList());
    }

}
