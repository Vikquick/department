package ru.gorshkov.department.Models.Enum;

public enum Post {
    POST1 {
        @Override
        public String toString() {
            return "POST1";
        }
    }, POST2 {
        @Override
        public String toString() {
            return "POST2";
        }
    }, POST3 {
        @Override
        public String toString() {
            return "POST3";
        }
    }, POST4 {
        @Override
        public String toString() {
            return "POST4";
        }
    };

    @Override
    public abstract String toString();
}
