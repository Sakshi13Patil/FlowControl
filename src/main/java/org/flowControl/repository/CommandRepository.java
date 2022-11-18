package org.flowControl.repository;

import org.flowControl.exceptions.FlowControlException;
import org.flowControl.model.Command;
import org.flowControl.model.RequestDetails;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CommandRepository {

    List<Command> commands = new ArrayList<>();

    public void createCommand(Command command) {
        if (getCommandByName(command.getCommandName()).isEmpty()) {
            commands.add(command);
        } else {
            throw new FlowControlException("Command Name " + command.getCommandName() + " already exists. " +
                    "Cannot create duplicate config");
        }
    }

    public Optional<Command> getCommandByName(String name) {
        return commands.stream().filter(a -> a.getCommandName().equals(name)).findFirst();
    }

    public void updateCommandRequestDetails(String commandName, RequestDetails requestDetails) {
        Optional<Command> commandOptional = getCommandByName(commandName);
        if (commandOptional.isPresent()) {
            commandOptional.get().setRequestDetails(requestDetails);
        } else {
            throw new FlowControlException("Command Name " + commandName + " doesn't exists");
        }
    }
}
