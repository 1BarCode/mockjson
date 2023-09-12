package com.mycompany.mockjson.auth.permission;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class PermissionNameConverter implements AttributeConverter<PermissionName, String> {

    @Override
    public String convertToDatabaseColumn(PermissionName permissionName) {
        return permissionName.getPermission();
    }

    @Override
    public PermissionName convertToEntityAttribute(String permissionName) {
        return PermissionName.fromString(permissionName);
    }
}
