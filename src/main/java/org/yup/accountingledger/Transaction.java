package org.yup.accountingledger;

import java.io.BufferedWriter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;


public class Transaction {
        private String type;
        private LocalDate date;
        private LocalTime time ;
        private double amount;
        private String vendor;


        public Transaction( LocalDate thisDate, LocalTime thisTime, String transactionDetail,double parseDouble, String vendor) {
            this.date = thisDate;
            this.time = thisTime;
            this.type = transactionDetail;
            this.amount = parseDouble;
            this.vendor = vendor;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public LocalDate getDate() {
            return date;
        }

        public void setDate(LocalDate date) {
            this.date = date;
        }

        public double getAmount() {
            return amount;
        }

        public void setAmount(double amount) {
            this.amount = amount;
        }

        public String getVendor() {
            return vendor;
        }

        public void setVendor(String vendor) {
            this.vendor = vendor;
        }

        // how to create an instance or single transaction
    }
