package com.fp.bus;

import java.util.HashMap;
import java.util.Map;
import java.util.Observer;
import com.fp.bus.Observed.server.impl.ObservableServer;
import com.fp.bus.Observer.HostObserverServer;
import com.fp.bus.cherry.DefaultPluginFactory;
import com.fp.bus.cherry.PluginFactory;

public class test {
	public static void main(String[] args) {
		test test = new test();
		test.test();
	}

	public void test() {
		// 声明被观察者
		ObservableServer observableServer = new ObservableServer();
		PluginFactory pluginFactory = new DefaultPluginFactory("classpath:plugins.xml");
		Observer ZhangSan = null;
		Observer LiSi = null;
		Observer WangWu = null;
		try {
			// 创建观察者实例
			ZhangSan = (Observer) pluginFactory.getPlugin("observerServer");
			// 注册观察者
			observableServer.addObserver(ZhangSan);
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			// 创建观察者实例
			LiSi = (Observer) pluginFactory.getPlugin("observerServer2");
			// 注册观察者
			observableServer.addObserver(LiSi);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// try {
		// // 创建观察者实例
		// WangWu = (Observer) pluginFactory.getPlugin("observerServer3");
		// // 注册观察者
		// observableServer.addObserver(WangWu);
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("bus", "我们是最好的!");
		map.put("hostObserver", new HostObserverServer());
		// 发送消息
		observableServer.setInfomation(map);

		System.out.println("----------------------------------------------");
		// 删除观察者
		observableServer.deleteObserver(ZhangSan);
		// 发送消息
		observableServer.setInfomation("JAVA是世界上最好用的语言！");
		// 关闭
		pluginFactory.close();
	}

}
