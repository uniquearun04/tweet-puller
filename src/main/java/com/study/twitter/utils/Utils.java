package com.study.twitter.utils;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

public class Utils {
	
	public static String getMyIP(){
		
		String returnIp = null;
	    try {
	        Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
	        while (interfaces.hasMoreElements()) {
	            NetworkInterface iface = interfaces.nextElement();
	            // filters out 127.0.0.1 and inactive interfaces
	            if (iface.isLoopback() || !iface.isUp() || iface.isVirtual() || iface.getDisplayName().contains("VMnet") || iface.getDisplayName().contains("Pseudo-Interface")){
	            	continue;
	            }

	            Enumeration<InetAddress> addresses = iface.getInetAddresses();
	            while(addresses.hasMoreElements()) {
	                InetAddress addr = addresses.nextElement();
	                String ip = addr.getHostAddress();
	                System.out.println(iface.getDisplayName() + "->" + ip);
	                String[] split = ip.split(".");
	                if(split.length == 4){
	                	returnIp = ip;
	                }
	            }
//	            System.out.println("iface: "+iface+"==================");
	        }
	    } catch (SocketException e) {
	        throw new RuntimeException(e);
	    }
	    
//	    System.out.println(returnIp);
	    
	    return returnIp;
	}

}
