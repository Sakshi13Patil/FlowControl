package org.flowControl.service;

import org.flowControl.config.CommandConfig;

public interface RegisterCommandService {
    void register(String commandName, CommandConfig commandConfig);
}
