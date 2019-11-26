package com.qhc.steigenberger.domain;

import io.swagger.annotations.*;
import java.sql.Timestamp;
//import javax.persistence.*;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;

//@Entity
//@Table(name = "qhc_contract_signsys")
@ApiModel(description = "签约系统合同")
public class ContractSignSys {
//	@Id
//	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	@Size(max = 40)
	@ApiModelProperty("电子签约中合同Id")
//	@Column(name = "sign_contractid", length = 40)
	private String signContractId;
	@Size(max = 200)
	@ApiModelProperty("文档Hash值")
//	@Column(name = "file_hashcode", length = 200)
	private String fileHashCode;

	public void setId(long id) {
		this.id = id;
	}

	@Size(max = 2)
	@ApiModelProperty("是否删除标识")
//	@Column(name = "is_delete", length = 2)
	private String isDelete;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@ApiModelProperty("创建日期")
//	@Column(name = "createDate")
	private Timestamp createDate;
//	@Transient
	private Boolean curHave;

	public void setSignContractId(String signContractId) {
		this.signContractId = signContractId;
	}

	public void setFileHashCode(String fileHashCode) {
		this.fileHashCode = fileHashCode;
	}

	public void setIsDelete(String isDelete) {
		this.isDelete = isDelete;
	}

	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}

	public void setCurHave(Boolean curHave) {
		this.curHave = curHave;
	}

	public String toString() {
		return "ContractSignSys(id=" + getId() + ", signContractId=" + getSignContractId() + ", fileHashCode="
				+ getFileHashCode() + ", isDelete=" + getIsDelete() + ", createDate=" + getCreateDate() + ", curHave="
				+ getCurHave() + ")";
	}

	public long getId() {
		return this.id;
	}

	public String getSignContractId() {
		return this.signContractId;
	}

	public String getFileHashCode() {
		return this.fileHashCode;
	}

	public String getIsDelete() {
		return this.isDelete;
	}

	public Timestamp getCreateDate() {
		return this.createDate;
	}

	public Boolean getCurHave() {
		return this.curHave;
	}
}
