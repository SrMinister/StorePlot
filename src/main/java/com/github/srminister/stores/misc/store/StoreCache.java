package com.github.srminister.stores.misc.store;

import com.github.srminister.stores.utils.Cache;

public class StoreCache extends Cache<Store> {

    public Store getByUser(String name) {
        return getCached(user -> user.getName().equalsIgnoreCase(name));
    }
}
