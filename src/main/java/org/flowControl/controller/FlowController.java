package org.flowControl.controller;

import org.flowControl.config.CommandConfig;
import org.flowControl.exceptions.FlowControlException;
import org.flowControl.repository.CommandRepository;
import org.flowControl.service.CallPermissionService;
import org.flowControl.service.RegisterCommandService;
import org.flowControl.service.TrackCallService;
import org.flowControl.service.impl.CallPermissionServiceImpl;
import org.flowControl.service.impl.RegisterCommandServiceImpl;
import org.flowControl.service.impl.TrackCallServiceImpl;

import static org.flowControl.constants.ApplicationConstants.*;

public class FlowController {
    private final CommandRepository commandRepository = new CommandRepository();
    private final CallPermissionService callPermissionService = new CallPermissionServiceImpl(commandRepository);
    private final RegisterCommandService registerCommandService = new RegisterCommandServiceImpl(commandRepository);
    private final TrackCallService trackCallService = new TrackCallServiceImpl(commandRepository);
    /**
     * Peforms Operations as per CLI
     *
     * @param input Command Allowed in following format :
     * 1. REGISTER COMMAND_NAME X Y T R T1
     * 2. IS_ALLOWED COMMAND_NAME
     * 3. TRACKING COMMAND_NAME TIME
     */

    public void performOperation(String input) {
        String[] inputArray = input.split(" ");
        if (REGISTER.name().equals(inputArray[0])) {
            registerCommandService.register(inputArray[1], new CommandConfig(Integer.parseInt(inputArray[2]),
                    Integer.parseInt(inputArray[3]),
                    Integer.parseInt(inputArray[4]),
                    Integer.parseInt(inputArray[5]),
                    Integer.parseInt(inputArray[6])));
        }

        else if( IS_ALLOWED.name().equals(inputArray[0])) {
            callPermissionService.isAllowed(inputArray[1]);
        }

        else if( TRACKING.name().equals(inputArray[0])) {
            trackCallService.tracking(inputArray[1], Integer.parseInt(inputArray[2]));
        }
        else {
            throw new FlowControlException("Command not found "+input);
        }
    }
}
