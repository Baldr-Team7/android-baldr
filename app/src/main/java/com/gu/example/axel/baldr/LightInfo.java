package com.gu.example.axel.baldr;

/**
 * Created by arasb on 2016-11-22.
 */

public class LightInfo {

        private String color;

        private String state;

        private String room;

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getRoom() {
            return room;
        }

        public void setRoom(String room) {
            this.room = room;
        }

        @Override
        public String toString() {
            return " [color = " + color + ", state = " + state + ", room = " + room + "]";
        }
    }


