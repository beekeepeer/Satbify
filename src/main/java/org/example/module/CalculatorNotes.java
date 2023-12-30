package org.example.module;

public class CalculatorNotes {
    public static void main(String[] args) {

        int current_number = 32;
        System.out.print(current_number + ", ");
        while (current_number <= 107) {
            current_number += 12;
            System.out.print(current_number + ", ");
        }
    }
}