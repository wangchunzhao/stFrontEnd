package cn.bestsign.ultimate.delta.api.domain.template.create;

/**
 * @author whthomas
 * @date 2018/6/27
 */
public class Role {

    private Long roleId;

    private RoleType roleType;

    private String userAccount;

    private String userName;

    private String enterpriseName;

    private Boolean forceHandWrite;

    private Boolean requireIdentityAssurance;

    private Boolean faceVerify;

    private String privateLetter;

    private Integer routeOrder;

    private String notification;

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public RoleType getRoleType() {
        return roleType;
    }

    public void setRoleType(RoleType roleType) {
        this.roleType = roleType;
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

    public Boolean getForceHandWrite() {
        return forceHandWrite;
    }

    public void setForceHandWrite(Boolean forceHandWrite) {
        this.forceHandWrite = forceHandWrite;
    }

    public Boolean getRequireIdentityAssurance() {
        return requireIdentityAssurance;
    }

    public void setRequireIdentityAssurance(Boolean requireIdentityAssurance) {
        this.requireIdentityAssurance = requireIdentityAssurance;
    }

    public Boolean getFaceVerify() {
        return faceVerify;
    }

    public void setFaceVerify(Boolean faceVerify) {
        this.faceVerify = faceVerify;
    }

    public String getPrivateLetter() {
        return privateLetter;
    }

    public void setPrivateLetter(String privateLetter) {
        this.privateLetter = privateLetter;
    }

    public Integer getRouteOrder() {
        return routeOrder;
    }

    public void setRouteOrder(Integer routeOrder) {
        this.routeOrder = routeOrder;
    }

    public String getNotification() {
        return notification;
    }

    public void setNotification(String notification) {
        this.notification = notification;
    }
}
