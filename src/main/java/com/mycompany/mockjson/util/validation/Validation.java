package com.mycompany.mockjson.util.validation;

import java.util.UUID;

public class Validation {
    public static UUID getUUIDFromString(String id) throws IllegalArgumentException {
        return UUID.fromString(id);
    }
}
