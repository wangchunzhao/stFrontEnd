package cn.bestsign.ultimate.delta.api.domain.contract.create;

import java.util.List;

public class CreateReceiverVO {
    /**
     * 用户帐号
     */
    private String userAccount;

    /**
     * 用户类型
     */
    private SignFlowConstants.UserType userType ;

    /**
     * 用户名称
     */
    private String userName;

    /**
     * 所属企业的名称
     */
    private String enterpriseName;

    /**
     * 参与人类型 签署人(`SIGNER`) 抄送人(`CARBON_COPY`)
     */
    private SignFlowConstants.ReceiverType receiverType ;

    /**
     * 私信
     */
    private String privateLetter;

    /**
     * 是否需要实名
     */
    private Boolean requireIdentityAssurance = false;

//        /**
//         * 校验的配置
//         */
//        var verifyConfigVerifyConfig? ;

    /**
     * 是否手绘签名
     */
    private Boolean forceHandWrite =false;

    /**
     * 通知方式
     */
    private String notification ;

    /**
     * 是否自动签署
     */
    private Boolean signImmediately =false;

    /**
     * 签名图片文件的名称
     */
    private String signSignatureFileName ;

    /**
     * 签名印章文件的名称
     */
    private String signSealFileName;

    /**
     * 参与的顺序，默认为上一个参与者顺序+1; 抄送人暂时都被定义为最后一位
     */
    private Integer routeOrder;

    /**
     * 要签署的标签
     */
    private List<CreateLabel> labels;

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    public SignFlowConstants.UserType getUserType() {
        return userType;
    }

    public void setUserType(SignFlowConstants.UserType userType) {
        this.userType = userType;
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

    public SignFlowConstants.ReceiverType getReceiverType() {
        return receiverType;
    }

    public void setReceiverType(SignFlowConstants.ReceiverType receiverType) {
        this.receiverType = receiverType;
    }

    public String getPrivateLetter() {
        return privateLetter;
    }

    public void setPrivateLetter(String privateLetter) {
        this.privateLetter = privateLetter;
    }

    public Boolean getRequireIdentityAssurance() {
        return requireIdentityAssurance;
    }

    public void setRequireIdentityAssurance(Boolean requireIdentityAssurance) {
        this.requireIdentityAssurance = requireIdentityAssurance;
    }

    public Boolean getForceHandWrite() {
        return forceHandWrite;
    }

    public void setForceHandWrite(Boolean forceHandWrite) {
        this.forceHandWrite = forceHandWrite;
    }

    public String getNotification() {
        return notification;
    }

    public void setNotification(String notification) {
        this.notification = notification;
    }

    public Boolean getSignImmediately() {
        return signImmediately;
    }

    public void setSignImmediately(Boolean signImmediately) {
        this.signImmediately = signImmediately;
    }

    public String getSignSignatureFileName() {
        return signSignatureFileName;
    }

    public void setSignSignatureFileName(String signSignatureFileName) {
        this.signSignatureFileName = signSignatureFileName;
    }

    public String getSignSealFileName() {
        return signSealFileName;
    }

    public void setSignSealFileName(String signSealFileName) {
        this.signSealFileName = signSealFileName;
    }

    public Integer getRouteOrder() {
        return routeOrder;
    }

    public void setRouteOrder(Integer routeOrder) {
        this.routeOrder = routeOrder;
    }

    public List<CreateLabel> getLabels() {
        return labels;
    }

    public void setLabels(List<CreateLabel> labels) {
        this.labels = labels;
    }
}
