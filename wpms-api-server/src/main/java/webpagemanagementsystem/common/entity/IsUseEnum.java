package webpagemanagementsystem.common.entity;


import com.fasterxml.jackson.annotation.JsonCreator;

public enum IsUseEnum {
    U, N, D;

    @JsonCreator
    public static IsUseEnum fromValue(String value) {
        for (IsUseEnum e : IsUseEnum.values()) {
            if (e.name().equalsIgnoreCase(value)) {
                return e;
            }
        }
        throw new IllegalArgumentException("Unknown enum value: " + value);
    }
}
