package net.hyren.stores.data;

import lombok.Builder;
import lombok.Data;
import net.hyren.stores.data.store.Stores;
import net.hyren.stores.misc.TimeUtils;

@Data
@Builder
public class User {

    private final String username;
    private long time;
    private Stores stores;

    public String getTime() {
        return TimeUtils.format(time - System.currentTimeMillis());
    }

    public boolean isExpired() {
        return time <= System.currentTimeMillis();
    }
}
