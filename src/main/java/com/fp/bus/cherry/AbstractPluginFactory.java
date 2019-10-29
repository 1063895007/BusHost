package com.fp.bus.cherry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observer;
import java.util.concurrent.ConcurrentHashMap;
import com.fp.bus.cherry.config.PluginDefinition;
import com.fp.bus.cherry.exception.PluginException;

/**
 * ${DESCRIPTION}
 *
 * @author Ricky Fung
 */
public abstract class AbstractPluginFactory implements PluginFactory {
    protected final Map<String, PluginDefinition> pluginNameMap = new HashMap<String, PluginDefinition>();    //key涓簆lugin-name

    protected final ConcurrentHashMap<String, Observer> pluginInstanceMap = new ConcurrentHashMap<String, Observer>();

    @Override
    public Observer getPlugin(String name) throws PluginException {

    	Observer oserver = pluginInstanceMap.get(name);
        if(oserver==null){
            PluginDefinition pd = getPluginDefinition(name);
            if(pd==null){
                throw new PluginException("not found plugin:"+name+" definition");
            }
            oserver = getPlugin(name, pd);
        }
        return oserver;
    }

    private Observer getPlugin(String name, PluginDefinition pd) {
    	Observer observer = (Observer) pluginInstanceMap.get(name);
        if(observer==null){
            synchronized (this){
            	observer = createPluginInstance(pd);
            	Observer old = pluginInstanceMap.putIfAbsent(name, observer);
                if(old!=null){
                	observer = old;
                }
            }
        }
        return observer;
    }

    private Observer createPluginInstance(PluginDefinition pd) throws PluginException {
        try {
            Class<?> clazz = loadPluginClass(pd.getClazz());
            return (Observer) clazz.newInstance();
        } catch (ClassNotFoundException e) {
            throw new PluginException("not found plugin class:"+pd.getClazz(), e);
        } catch (InstantiationException e) {
            throw new PluginException("construct plugin instance error", e);
        } catch (IllegalAccessException e) {
            throw new PluginException("construct plugin instance error", e);
        }
    }

    protected abstract Class<?> loadPluginClass(String clazz) throws ClassNotFoundException;
    
    @Override
    public PluginDefinition getPluginDefinition(String name) {
        return pluginNameMap.get(name);
    }

    @Override
    public List<String> getPluginNames() {
        return new ArrayList<String>(pluginNameMap.keySet());
    }

    @Override
    public boolean hasPlugin(String name) {
        return getPluginDefinition(name)!=null;
    }
}
