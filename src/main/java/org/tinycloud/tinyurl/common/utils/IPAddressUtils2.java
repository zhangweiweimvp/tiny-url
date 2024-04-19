package org.tinycloud.tinyurl.common.utils;

/**
 * @author zhangweiwei
 * @date 2024/04/19 17:06
 **/
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URL;
import java.util.Enumeration;

public class IPAddressUtils2 {

    public static String getLocalHost() {
        String ipLocalHost = null;
        try {
            Enumeration<NetworkInterface> allNetInterfaces = NetworkInterface.getNetworkInterfaces();
            while (allNetInterfaces.hasMoreElements()) {
                NetworkInterface netInterface = allNetInterfaces.nextElement();
                if (netInterface.isUp() && !netInterface.isLoopback()) {
                    Enumeration<InetAddress> addresses = netInterface.getInetAddresses();
                    while (addresses.hasMoreElements()) {
                        InetAddress address = addresses.nextElement();
                        if (address instanceof InetAddress && !address.isLoopbackAddress() && address.getHostAddress().indexOf(":") == -1) {
                            ipLocalHost = address.getHostAddress();
                            break;
                        }
                    }
                }
            }
        } catch (SocketException e) {
            // Log the exception properly or handle it according to your application's requirements
            e.printStackTrace();
        }

        if (ipLocalHost != null && !ipLocalHost.isEmpty()) {
            return ipLocalHost;
        } else {
            return "Unable to retrieve local IP address";
        }
    }

    public static String getPublicIPAddress() {
        try {
            URL whatIsMyIP = new URL("http://checkip.amazonaws.com");
            BufferedReader in = new BufferedReader(new InputStreamReader(whatIsMyIP.openStream()));

            String ipAddress = in.readLine().trim();
            in.close();
            return ipAddress;
        } catch (IOException e) {
            // Log the exception properly or handle it according to your application's requirements
            e.printStackTrace();
            return "Unable to retrieve public IP address";
        }
    }

    public static void main(String[] args) {
        System.out.println("Local IP Address: " + getLocalHost());
        System.out.println("Public IP Address: " + getPublicIPAddress());
    }
}