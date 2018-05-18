package ru.gorshkov.department.Models.Enum;

public enum Post {
    Junior {
        @Override
        public String toString() {
            return "Junior";
        }
    }, Middle {
        @Override
        public String toString() {
            return "Middle";
        }
    }, Specialist {
        @Override
        public String toString() {
            return "Specialist";
        }
    }, Lead {
        @Override
        public String toString() {
            return "Lead";
        }
    };

    @Override
    public abstract String toString();
}
