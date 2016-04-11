package com.xdf.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HttpPostUtil {
	
	public String getMD5Str(String str){
		MessageDigest messageDigest = null;

		try {
			messageDigest = MessageDigest.getInstance("MD5");

			messageDigest.reset();

			messageDigest.update(str.getBytes("UTF-8"));
		} catch (NoSuchAlgorithmException e) {
			System.out.println("NoSuchAlgorithmException caught!");
			System.exit(-1);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		byte[] byteArray = messageDigest.digest();

		StringBuffer md5StrBuff = new StringBuffer();

		for (int i = 0; i < byteArray.length; i++) {
			if (Integer.toHexString(0xFF & byteArray[i]).length() == 1){
				md5StrBuff.append("0").append(Integer.toHexString(0xFF & byteArray[i]));
			}else{
				md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
			}
		}

		return md5StrBuff.toString();
	}
	
	public String doPost(String url,String param) throws IOException{
		String result = "";
		URL netUrl = new URL(url);
		HttpURLConnection connection = (HttpURLConnection)netUrl.openConnection();
		connection.setDoInput(true);
		connection.setDoOutput(true);
		connection.setUseCaches(false);
		connection.setInstanceFollowRedirects(true);
		connection.setRequestMethod("POST");
		connection.setRequestProperty("Accept", "application/json");
		connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		connection.connect();
		OutputStreamWriter outputStreamWriter = new OutputStreamWriter(connection.getOutputStream(),"UTF-8");
		outputStreamWriter.write(param);
		outputStreamWriter.flush();
		outputStreamWriter.close();
		System.out.println(connection.getResponseCode());
		InputStream is = connection.getInputStream();
		InputStreamReader inputStreamReader = new InputStreamReader(is,"utf-8");
		BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
		String tempLine = "";
		while((tempLine = bufferedReader.readLine()) != null){
			result += tempLine;
		}
		is.close();
		connection.disconnect();
		/*int length = (int) connection.getContentLength();// 获取长度
		InputStream is = connection.getInputStream();
		if (length != -1) {
			byte[] data = new byte[length];
			byte[] temp = new byte[512];
			int readLen = 0;
			int destPos = 0;
			while ((readLen = is.read(temp)) > 0) {
				System.arraycopy(temp, 0, data, destPos, readLen);
				destPos += readLen;
			}
			result = new String(data, "UTF-8"); // utf-8编码
			System.out.println(result);
			System.out.println("我的data是" + data);
		}*/
		return result;
	}
}
