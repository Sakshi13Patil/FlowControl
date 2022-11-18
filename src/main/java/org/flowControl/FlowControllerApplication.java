package org.flowControl;

import org.flowControl.controller.FlowController;
import org.flowControl.exceptions.FlowControlException;

import java.util.Scanner;

public class FlowControllerApplication {
    static final FlowController flowController = new FlowController();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String input = scanner.nextLine();
            if (input.equals("EXIT"))
                break;
            try {
                flowController.performOperation(input);
            } catch (FlowControlException e) {
                System.out.println("Error Occurred " + e.getMessage());
            }
        }
    }
}