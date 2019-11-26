package cn.bestsign.ultimate.delta.api.domain.template.create;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author whthomas
 * @date 2018/6/27
 */
public class PlaceHolder {

    private String userAccount;

    private String userName;

    private String enterpriseName;

    private String contractTitle;

    private Long contractLifeEnd;

    private String consumerDefinedUserId;

    private String notification;

    private List<LabelVO> textLabels;

    public void addLabel(LabelVO labelVO) {

        if (Objects.isNull(textLabels)) {
            textLabels = new ArrayList();
        }

        textLabels.add(labelVO);
    }

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEnterpriseName() {
        return enterpriseName;
    }

    public void setEnterpriseName(String enterpriseName) {
        this.enterpriseName = enterpriseName;
    }

    public String getContractTitle() {
        return contractTitle;
    }

    public void setContractTitle(String contractTitle) {
        this.contractTitle = contractTitle;
    }

    public Long getContractLifeEnd() {
        return contractLifeEnd;
    }

    public void setContractLifeEnd(Long contractLifeEnd) {
        this.contractLifeEnd = contractLifeEnd;
    }

    public String getConsumerDefinedUserId() {
        return consumerDefinedUserId;
    }

    public void setConsumerDefinedUserId(String consumerDefinedUserId) {
        this.consumerDefinedUserId = consumerDefinedUserId;
    }

    public String getNotification() {
        return notification;
    }

    public void setNotification(String notification) {
        this.notification = notification;
    }

    public List<LabelVO> getTextLabels() {
        return textLabels;
    }

    public void setTextLabels(List<LabelVO> textLabels) {
        this.textLabels = textLabels;
    }
}
