package com.sict.soft.smarthome;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;

import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;

/**
 * Created by Administrator on 2017/6/18.
 */

public class Blueto {

    static OutputStream outStream1 = null;
    static BluetoothAdapter mBluetoothAdapter = null;
    static BluetoothSocket btSocket1 = null;

    static final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    static String address = "98:D3:31:70:8A:C8"; // <==要连接的蓝牙设备MAC地址

    //static final UUID MY_UUID = UUID
    //		.fromString("00001101-0000-1000-8000-00805F9B34FB");
    //static String address = "00:12:11:28:07:67"; // MAC地址

    public Blueto()  {


    }

    static void ting() {

        try {
            byte[] CAR_STOP = new byte[] { (byte) 0x55, (byte) 0xAA,
                    (byte) 0x01, (byte) 0x00, (byte) 0x00, (byte) 0x00,
                    (byte) 1 % 256, (byte) 0xBB };
            outStream1.write(CAR_STOP);

        } catch (IOException e) {
        }
    }

    static void qianjin() {

        try {
            byte[] CAR_GO80 = new byte[] { (byte) 0x55, (byte) 0xAA,
                    (byte) 0x02, (byte) 0x64, (byte) 0x50, (byte) 0x00,
                    (byte) 0xB6, (byte) 0xBB };
            outStream1.write(CAR_GO80);

        } catch (IOException e) {
        }
    }

    static void jqian() {

        try {
            byte[] CAR_GO80 = new byte[] { (byte) 0x55, (byte) 0xAA,
                    (byte) 0x91, (byte) 0x64, (byte) 0x50, (byte) 0x00,
                    (byte) 0xB6, (byte) 0xBB };
            outStream1.write(CAR_GO80);

        } catch (IOException e) {
        }
    }

    //
    static void renwu() {

        try {
            byte[] START = new byte[] { (byte) 0x55, (byte) 0xAA, (byte) 0x99,
                    (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 153 % 256,
                    (byte) 0xBB };

            outStream1.write(START);

        } catch (IOException e) {
        }
    }

    static void houtui() {

        try {
            byte[] CAR_BACK = new byte[] { (byte) 0x55, (byte) 0xAA,
                    (byte) 0x03, 100, (byte) 190, (byte) 0, (byte) 293 % 256,
                    (byte) 0xBB };
            outStream1.write(CAR_BACK);

        } catch (IOException e) {
        }
    }

    static void zuozhuan() {

        try {
            byte[] CAR_LEFT = new byte[] { (byte) 0x55, (byte) 0xAA,
                    (byte) 0x04, 80, (byte) 0x00, (byte) 0x00, (byte) 84 % 255,
                    (byte) 0xBB };

            outStream1.write(CAR_LEFT);

        } catch (IOException e) {
        }
    }

    static void youzhuan() {

        try {
            byte[] CAR_RIGHT = new byte[] { (byte) 0x55, (byte) 0xAA,
                    (byte) 0x05, 80, (byte) 0x00, (byte) 0x00, (byte) 85 % 255,
                    (byte) 0xBB };
            outStream1.write(CAR_RIGHT);

        } catch (IOException e) {
        }
    }

    static void xunji() {
        try {
            byte[] Ca_Q = new byte[] { (byte) 0x55, (byte) 0xAA, (byte) 0x91,
                    (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 145 % 256,
                    (byte) 0xBB };

            outStream1.write(Ca_Q);
        } catch (IOException e) {
        }
    }

    static void taiqi() {

        String message;
        byte[] msgBuffer;

        message = "0";

        msgBuffer = message.getBytes();

        try {
            outStream1.write(msgBuffer);

        } catch (IOException e) {
        }
    }

    static void onResume() {
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(address);

        try {

            btSocket1 = device.createRfcommSocketToServiceRecord(MY_UUID);

        } catch (IOException e) {

            // DisplayToast("套接字创建失败！");
        }
        // DisplayToast("成功连接智能小车！可以开始操控了~~~");
        mBluetoothAdapter.cancelDiscovery();

        try {

            btSocket1.connect();
            // DisplayToast("连接成功建立，数据连接打开！");
            outStream1 = btSocket1.getOutputStream();

        } catch (IOException e) {

            try {
                btSocket1.close();

            } catch (IOException e2) {

                // DisplayToast("连接没有建立，无法关闭套接字！");
            }
        }
    }
}
