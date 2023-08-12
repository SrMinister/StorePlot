package net.hyren.stores.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import net.hyren.stores.data.store.Stores;
import net.hyren.stores.misc.TimeUtils;

@Data
@AllArgsConstructor
public class User {

    private final String name;
    private long time;
    private Stores stores;

    public String getTimeFormat() {
        return TimeUtils.format(time - System.currentTimeMillis());
    }

    public boolean isExpired() {
        return time <= System.currentTimeMillis();
    }
}
