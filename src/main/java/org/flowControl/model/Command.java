package org.flowControl.model;

import lombok.Builder;
import lombok.Data;
import org.flowControl.config.CommandConfig;

@Builder
@Data
public class Command {

    private String commandName;
    private CommandConfig commandConfig;
    private RequestDetails requestDetails;
}
