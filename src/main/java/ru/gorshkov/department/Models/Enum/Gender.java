package ru.gorshkov.department.Models.Enum;

public enum Gender {
    MALE {
        @Override
        public String toString() {
            return "MALE";
        }
    },
    FEMALE {
        @Override
        public String toString() {
            return "FEMALE";
        }
    };

    @Override
    public abstract String toString();
}
