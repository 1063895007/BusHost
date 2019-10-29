package com.fp.bus.cherry;


import java.util.List;
import java.util.Observer;

import com.fp.bus.cherry.config.PluginDefinition;
import com.fp.bus.cherry.exception.PluginException;

/**
 * ${DESCRIPTION}
 *
 * @author Ricky Fung
 */
public interface PluginFactory {

	Observer getPlugin(String name) throws PluginException;

    PluginDefinition getPluginDefinition(String name);

    List<String> getPluginNames();

    boolean hasPlugin(String name);

    void close();
}
