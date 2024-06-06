package com.example.myapplication;

public class Booking {

        private String userName;
        private int numberOfPeople;

        private String startTime;
        private String endTime;
        private int bookedTable;

        // Constructor
        public Booking(String userName, int numberOfPeople, String startTime, String endTime, int bookedTable) {
            this.userName = userName;
            this.numberOfPeople = numberOfPeople;
            this.startTime = startTime;
            this.endTime = endTime;
            this.bookedTable = bookedTable;
        }

        // Getters and Setters
        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public int getNumberOfPeople() {
            return numberOfPeople;
        }

        public void setNumberOfPeople(int numberOfPeople) {
            this.numberOfPeople = numberOfPeople;
        }

        public String getStartTime() {
            return startTime;
        }

        public void setStartTime(String startTime) {
            this.startTime = startTime;
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }

        public int getBookedTable() {
            return bookedTable;
        }

        public void setBookedTable(int bookedTable) {
            this.bookedTable = bookedTable;
        }
    }


