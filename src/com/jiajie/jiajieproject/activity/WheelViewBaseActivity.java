/**
 * 
 */
package com.jiajie.jiajieproject.activity;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import android.content.res.AssetManager;

import com.mrwujay.cascade.model.CityModel;
import com.mrwujay.cascade.model.DistrictModel;
import com.mrwujay.cascade.model.ProvinceModel;
import com.mrwujay.cascade.service.XmlParserHandler;

/**   
 * 项目名称：NewProject   
 * 类名称：WheelViewBaseActivity   
 * 类描述：   
 * 创建人：王蕾
 * 创建时间：2015-10-10 上午11:25:57   
 * 修改备注：    
 */
public class WheelViewBaseActivity extends BaseActivity {
protected String[] mProvinceDatas;
	
	protected Map<String, String[]> mCitisDatasMap = new HashMap<String, String[]>();
	
	protected Map<String, String[]> mDistrictDatasMap = new HashMap<String, String[]>();

	
	protected Map<String, String> mZipcodeDatasMap = new HashMap<String, String>();

	
	protected String mCurrentProviceName;

	protected String mCurrentCityName;
	
	protected String mCurrentDistrictName = "";

	
	protected String mCurrentZipCode = "";

	

	protected void initProvinceDatas() {
		List<ProvinceModel> provinceList = null;
		AssetManager asset = getAssets();
		try {
			InputStream input = asset.open("province_data.xml");

			SAXParserFactory spf = SAXParserFactory.newInstance();

			SAXParser parser = spf.newSAXParser();
			XmlParserHandler handler = new XmlParserHandler();
			parser.parse(input, handler);
			input.close();

			provinceList = handler.getDataList();

			if (provinceList != null && !provinceList.isEmpty()) {
				mCurrentProviceName = provinceList.get(0).getName();
				List<CityModel> cityList = provinceList.get(0).getCityList();
				if (cityList != null && !cityList.isEmpty()) {
					mCurrentCityName = cityList.get(0).getName();
					List<DistrictModel> districtList = cityList.get(0)
							.getDistrictList();
					mCurrentDistrictName = districtList.get(0).getName();
					mCurrentZipCode = districtList.get(0).getZipcode();
				}
			}

			mProvinceDatas = new String[provinceList.size()];
			for (int i = 0; i < provinceList.size(); i++) {

				mProvinceDatas[i] = provinceList.get(i).getName();
				List<CityModel> cityList = provinceList.get(i).getCityList();
				String[] cityNames = new String[cityList.size()];
				for (int j = 0; j < cityList.size(); j++) {

					cityNames[j] = cityList.get(j).getName();
					List<DistrictModel> districtList = cityList.get(j)
							.getDistrictList();
					String[] distrinctNameArray = new String[districtList
							.size()];
					DistrictModel[] distrinctArray = new DistrictModel[districtList
							.size()];
					for (int k = 0; k < districtList.size(); k++) {

						DistrictModel districtModel = new DistrictModel(
								districtList.get(k).getName(), districtList
										.get(k).getZipcode());

						mZipcodeDatasMap.put(districtList.get(k).getName(),
								districtList.get(k).getZipcode());
						distrinctArray[k] = districtModel;
						distrinctNameArray[k] = districtModel.getName();
					}

					mDistrictDatasMap.put(cityNames[j], distrinctNameArray);
				}

				mCitisDatasMap.put(provinceList.get(i).getName(), cityNames);
			}
		} catch (Throwable e) {
			e.printStackTrace();
		} finally {

		}
	}
}
