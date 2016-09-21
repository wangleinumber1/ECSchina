package com.jiajie.jiajieproject.model;

/**
 * @ClassName DeviceInfo
 * @Description
 */
public class DeviceInfo{

	/**
	 * @Fields 屏幕横向分辨率
	 */
	private int screenWidth;

	/**
	 * @Fields 屏幕纵向分辨率
	 */
	private int screenHeight;

	/**
	 * @Fields 设备型号（比如说是哪个厂家的某个机型）
	 */
	private String deviceModel;

	/**
	 * @Fields 当前安装的软件版本
	 */
	private String softVersion;

	/**
	 * @Fields 当前系统版本
	 */
	private String systemVersion;

	/**
	 * @Fields deviceId 设备唯一编号
	 */
	private String deviceID;
	
	private static DeviceInfo deviceInfo;
	
	/*
	 * appVersionCode
	 */
	private int appVersionCode;
	
	public int getAppVersionCode() {
		return appVersionCode;
	}

	public void setAppVersionCode(int appVersionCode) {
		this.appVersionCode = appVersionCode;
	}

	public DeviceInfo() {
		
	}
	
	/** 
	 * 单例DeviceInfo对象
	 * @return 唯一的deviceInfo对象 
	 * @author huangke@yoka.com 
	 */
	public synchronized static DeviceInfo getInstance() {
		if (deviceInfo == null) {
			deviceInfo = new DeviceInfo();
		}
		return deviceInfo;
	}

	/**
	 * @return the screenWidth
	 */
	public int getScreenWidth() {
		return screenWidth;
	}

	/**
	 * @param screenWidth
	 *            the screenWidth to set
	 */
	public void setScreenWidth(int screenWidth) {
		this.screenWidth = screenWidth;
	}

	/**
	 * @return the screenHeight
	 */
	public int getScreenHeight() {
		return screenHeight;
	}

	/**
	 * @param screenHeight
	 *            the screenHeight to set
	 */
	public void setScreenHeight(int screenHeight) {
		this.screenHeight = screenHeight;
	}

	/**
	 * @return the deviceModel
	 */
	public String getDeviceModel() {
		return deviceModel;
	}

	/**
	 * @param deviceModel
	 *            the deviceModel to set
	 */
	public void setDeviceModel(String deviceModel) {
		this.deviceModel = deviceModel;
	}

	/**
	 * @return the softVersion
	 */
	public String getSoftVersion() {
		return softVersion;
	}

	/**
	 * @param softVersion
	 *            the softVersion to set
	 */
	public void setSoftVersion(String softVersion) {
		this.softVersion = softVersion;
	}

	/**
	 * @return the systemVersion
	 */
	public String getSystemVersion() {
		return systemVersion;
	}

	/**
	 * @param systemVersion
	 *            the systemVersion to set
	 */
	public void setSystemVersion(String systemVersion) {
		this.systemVersion = systemVersion;
	}

	/**
	 * @return the deviceID
	 */
	public String getDeviceID() {
		return deviceID;
	}

	/**
	 * @param deviceID
	 *            the deviceID to set
	 */
	public void setDeviceID(String deviceID) {
		this.deviceID = deviceID;
	}
}
