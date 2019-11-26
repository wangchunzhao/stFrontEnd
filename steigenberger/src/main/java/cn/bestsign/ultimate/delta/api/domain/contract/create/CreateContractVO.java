package cn.bestsign.ultimate.delta.api.domain.contract.create;

import java.util.Date;
import java.util.List;

public class CreateContractVO {
    /**
     * 合同标题
     */
    private String  contractTitle;

    /**
     * 合同描述
     */
    private String  contractDescription;

    /**
     * 是否顺序签
     */
    private Boolean isSignOrdered;

    /**
     * 签名截止日期
     */
    private Date signDeadline;

    /**
     * 合同的文档
     */
    private List<CreateDocumentVO> documents;

    /**
     * 合同的参与人
     */
    private List<CreateReceiverVO> receivers;

    public String getContractTitle() {
        return contractTitle;
    }

    public void setContractTitle(String contractTitle) {
        this.contractTitle = contractTitle;
    }

    public String getContractDescription() {
        return contractDescription;
    }

    public void setContractDescription(String contractDescription) {
        this.contractDescription = contractDescription;
    }

    public Boolean getSignOrdered() {
        return isSignOrdered;
    }

    public void setSignOrdered(Boolean signOrdered) {
        isSignOrdered = signOrdered;
    }

    public Date getSignDeadline() {
        return signDeadline;
    }

    public void setSignDeadline(Date signDeadline) {
        this.signDeadline = signDeadline;
    }

    public List<CreateDocumentVO> getDocuments() {
        return documents;
    }

    public void setDocuments(List<CreateDocumentVO> documents) {
        this.documents = documents;
    }

    public List<CreateReceiverVO> getReceivers() {
        return receivers;
    }

    public void setReceivers(List<CreateReceiverVO> receivers) {
        this.receivers = receivers;
    }
}
