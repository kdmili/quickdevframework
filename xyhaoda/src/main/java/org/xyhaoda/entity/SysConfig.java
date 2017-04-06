package org.xyhaoda.entity;

import java.util.List;

import javax.persistence.Convert;
import javax.persistence.Entity;

import org.lm.quick.componet.entity.ArrayProprertyConvert;
import org.lm.quick.entity.BaseEntity;
import org.lm.quick.ui.annotation.UIField;
import org.lm.quick.ui.annotation.UIField.FType;

@Entity
public class SysConfig extends BaseEntity {
	private String keyword;
	private String meta;
	@UIField(ftype = FType.Img)
	private String icon;
	@UIField(ftype=FType.Img)
	@Convert(converter=ArrayProprertyConvert.class)
	private List<String> bannelImgs;
	private String siteName;
	@UIField(ftype = FType.Img)
	private String logo;
	private String links;

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getMeta() {
		return meta;
	}

	public void setMeta(String meta) {
		this.meta = meta;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	 
	public String getSiteName() {
		return siteName;
	}

	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getLinks() {
		return links;
	}

	public void setLinks(String links) {
		this.links = links;
	}

	
	public List<String> getBannelImgs() {
		return bannelImgs;
	}
	public void setBannelImgs(List<String> bannelImgs) {
		this.bannelImgs = bannelImgs;
	}
	
}
