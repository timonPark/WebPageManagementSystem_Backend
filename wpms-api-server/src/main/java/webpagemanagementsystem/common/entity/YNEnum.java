package webpagemanagementsystem.common.entity;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum YNEnum {
    Y, N;

    @JsonCreator
    public static YNEnum fromValue(String value) {
        for (YNEnum e : YNEnum.values()) {
            if (e.name().equalsIgnoreCase(value)) {
                return e;
            }
        }
        throw new IllegalArgumentException("Unknown enum value: " + value);
    }

}
