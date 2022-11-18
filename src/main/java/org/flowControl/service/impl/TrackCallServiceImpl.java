package org.flowControl.service.impl;

import org.flowControl.model.Command;
import org.flowControl.model.RequestDetails;
import org.flowControl.repository.CommandRepository;
import org.flowControl.service.TrackCallService;

import java.util.Optional;

public class TrackCallServiceImpl implements TrackCallService {
    private final CommandRepository commandRepository;

    public TrackCallServiceImpl(CommandRepository commandRepository) {
        this.commandRepository = commandRepository;
    }

    @Override
    public void tracking(String commandName, Integer responseTime) {
        long currentTime = System.currentTimeMillis();
        Optional<Command> command = commandRepository.getCommandByName(commandName);
        if (command.isPresent()) {
            RequestDetails requestDetails = command.get().getRequestDetails();
            if (requestDetails.getNotAllowedTill() < currentTime) {
                requestDetails.setNotAllowedTill(0L);
                requestDetails.setTotalResponseTime(requestDetails.getTotalResponseTime() + responseTime);
                requestDetails.setNoOfRequests(requestDetails.getNoOfRequests() + 1);
                requestDetails.getLastRequestsTimings().add(currentTime);
                commandRepository.updateCommandRequestDetails(commandName, requestDetails);
            }

        }
    }
}