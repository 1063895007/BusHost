package com.fp.bus.cherry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fp.bus.cherry.config.AppConfig;
import com.fp.bus.cherry.config.Library;
import com.fp.bus.cherry.config.PluginDefinition;
import com.fp.bus.cherry.exception.PluginException;
import com.fp.bus.cherry.util.StringUtils;
import com.fp.bus.cherry.xml.ConfigParser;

import java.io.File;
import java.util.List;

/**
 * ${DESCRIPTION}
 *
 * @author Ricky Fung
 */
public class DefaultPluginFactory extends AbstractPluginFactory {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private PluginClassLoader classLoader;
    private AppConfig config;

    private volatile boolean closed = false;

    public DefaultPluginFactory(String path){
        if(StringUtils.isEmpty(path)){
            throw new IllegalArgumentException("path can not be empty!");
        }
        if(path.startsWith("classpath:")){
            path = path.substring(10);
        }
        this.config = new ConfigParser().parse(path);
        init(this.config);
    }

    public DefaultPluginFactory(File file){
        this.config = new ConfigParser().parse(file);
        init(this.config);
    }


    @Override
    protected Class<?> loadPluginClass(String clazz) throws ClassNotFoundException {
        return classLoader.loadClass(clazz);
    }


    @Override
    public void close() {

        if(!closed){
            closed = true;
            pluginInstanceMap.clear();
            pluginNameMap.clear();
        }
    }

    private void init(AppConfig config) {
        //加载libs
    	 List<Library> libs = config.getLibs();
         if(libs!=null && libs.size()>0){
             for (Library lib : libs){
                 addExternalJar(lib.getDir());
             }
         }

         List<PluginDefinition> pds = config.getPlugins();
         if(pds!=null && pds.size()>0){
             for (PluginDefinition pd : pds) {
                 if(pluginNameMap.containsKey(pd.getName())){
                     throw new PluginException("Plugin name:"+pd.getName()+" already exists");
                 }
                 pluginNameMap.put(pd.getName(), pd);
             }
         }
    }

    private void addExternalJar(String basePath){
        if (StringUtils.isEmpty(basePath)) {
            throw new IllegalArgumentException("basePath can not be empty!");
        }
        File dir = new File(basePath);
        if(!dir.exists()){
            throw new IllegalArgumentException("basePath not exists:"+basePath);
        }
        if(!dir.isDirectory()){
            throw new IllegalArgumentException("basePath must be a directory:"+basePath);
        }
        if(classLoader==null){
            classLoader = new PluginClassLoader(basePath);
        } else {
            classLoader.addToClassLoader(basePath, null, false);
        }
    }

}
