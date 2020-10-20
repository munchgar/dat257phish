package org.phish.classes;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class HouseType {

        private SimpleIntegerProperty houseID;
        private SimpleStringProperty houseName;

        public HouseType (int houseID ,String houseName){
            this.houseName = new SimpleStringProperty(houseName);
            this.houseID = new SimpleIntegerProperty(houseID);
        }
        public int getID(){
            return houseID.get();
        }

        public String getName(){
            return houseName.get();
        }
        @Override
        public String toString(){
            return houseName.get();
        }
    }


