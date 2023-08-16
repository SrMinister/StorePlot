package com.github.srminister.stores.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import com.github.srminister.stores.data.store.Stores;
import com.github.srminister.stores.misc.TimeUtils;

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
