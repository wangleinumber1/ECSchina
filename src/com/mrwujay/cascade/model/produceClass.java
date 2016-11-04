/**
 * 
 */
package com.mrwujay.cascade.model;

import java.io.Serializable;


/**
 * 项目名称：NewProject 类名称：produceClass 类描述： 创建人：王蕾 创建时间：2015-11-5 下午6:14:06 修改备注：
 */
public class produceClass implements Serializable{

	public String id;
	public String item_id;
	public String sku;
	public String name;
	public String image;
	public String price;
	public String qty;
	public String total_price;
	public String productName;
	public String product_name;
	public String productId;
	public String product_id;
	public String address_id;
	public String title;
	public String url;
	public String status;
	public String order_code;
	public String order_price;
	public String order_qty;
	public String qty_ordered;
	public String express;
	public String order_date;
	public String customer_name;
	public String customer_email;
	public String description;
	public String short_description;
	public String pn;
	public boolean isChoosed;
	@Override
	public String toString() {
		return "produceClass [id=" + id + ", item_id=" + item_id + ", sku="
				+ sku + ", name=" + name + ", image=" + image + ", price="
				+ price + ", qty=" + qty + ", total_price=" + total_price
				+ ", productName=" + productName + ", product_name="
				+ product_name + ", productId=" + productId + ", product_id="
				+ product_id + ", address_id=" + address_id + ", title="
				+ title + ", url=" + url + ", status=" + status
				+ ", order_code=" + order_code + ", order_price=" + order_price
				+ ", order_qty=" + order_qty + ", qty_ordered=" + qty_ordered
				+ ", express=" + express + ", order_date=" + order_date
				+ ", customer_name=" + customer_name + ", customer_email="
				+ customer_email + ", description=" + description
				+ ", short_description=" + short_description + ", pn=" + pn
				+ ", isChoosed=" + isChoosed + "]";
	}

}
