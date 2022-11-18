package org.flowControl.service.impl;

import org.flowControl.config.CommandConfig;
import org.flowControl.exceptions.FlowControlException;
import org.flowControl.model.Command;
import org.flowControl.model.RequestDetails;
import org.flowControl.repository.CommandRepository;
import org.flowControl.service.RegisterCommandService;

import java.util.ArrayList;

public class RegisterCommandServiceImpl implements RegisterCommandService {
    private final CommandRepository commandRepository;

    public RegisterCommandServiceImpl(CommandRepository commandRepository) {
        this.commandRepository = commandRepository;
    }


    @Override
    public void register(String commandName, CommandConfig commandConfig) {

        Command command = Command.builder()
                .commandName(commandName)
                .commandConfig(commandConfig)
                .requestDetails(createRequestDetails())
                .build();
        try {
            commandRepository.createCommand(command);
        } catch (FlowControlException e) {
            System.out.println(e.getMessage());
        }

    }

    private RequestDetails createRequestDetails() {
        return RequestDetails
                .builder()
                .noOfRequests(0)
                .lastRequestsTimings(new ArrayList<>())
                .totalResponseTime(0)
                .noOfRequests(0)
                .build();
    }
}
