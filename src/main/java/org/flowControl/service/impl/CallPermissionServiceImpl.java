package org.flowControl.service.impl;

import org.flowControl.exceptions.FlowControlException;
import org.flowControl.model.Command;
import org.flowControl.model.RequestDetails;
import org.flowControl.repository.CommandRepository;
import org.flowControl.service.CallPermissionService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class CallPermissionServiceImpl implements CallPermissionService {

    private final CommandRepository commandRepository;

    public CallPermissionServiceImpl(CommandRepository commandRepository) {
        this.commandRepository = commandRepository;
    }

    @Override
    public boolean isAllowed(String commandName) {
        Optional<Command> commandOptional = commandRepository.getCommandByName(commandName);
        if (commandOptional.isPresent()) {
            Command command = commandOptional.get();
            boolean isAllowed = isAvgResponseTimeInX(command) && isAllowedRequestInT1(command);
            if (!isAllowed)
                commandRepository.updateCommandRequestDetails(commandName,
                        updateRequestDetails(command.getRequestDetails(), command.getCommandConfig().getT1()));
            System.out.println(isAllowed);
            return isAllowed;
        } else {
            throw new FlowControlException("Command doesn't exist" + commandName);
        }
    }

    private static boolean isAvgResponseTimeInX(Command command) {
        double avgResponseTime =  command.getRequestDetails().getNoOfRequests() != 0 ?
                (double) command.getRequestDetails().getTotalResponseTime() / command.getRequestDetails().getNoOfRequests() : 0.00;
        return avgResponseTime < command.getCommandConfig().getX();
    }

    private RequestDetails updateRequestDetails(RequestDetails requestDetails, long t1) {
        requestDetails.setNotAllowedTill(System.currentTimeMillis() + t1);
        return requestDetails;
    }

    private boolean isAllowedRequestInT1(Command command) {
        long currentTime = System.currentTimeMillis();
        long previousTime = currentTime - (long) command.getCommandConfig().getT1();
        int noOfRequests = 0;
        List<Long> lastRequestTiming = command.getRequestDetails().getLastRequestsTimings();
        for (int i = lastRequestTiming.size() - 1; i >= 0; i--) {
            if (lastRequestTiming.get(i) >= previousTime)
                noOfRequests++;
            else
                break;
        }

        return noOfRequests < command.getCommandConfig().getR();
    }
}