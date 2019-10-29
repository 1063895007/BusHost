package com.fp.bus.Observer;

import java.util.Observable;
import java.util.Observer;

/**
 * 观察者
 * 
 * @author wwwfp
 *
 */
public class HostObserverServer implements Observer {
	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		System.out.println("观察者" + 3 + "收到插件返回的消息：" + arg);
	}

}
