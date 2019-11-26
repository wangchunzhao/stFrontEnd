package cn.bestsign.ultimate.delta.api.domain.template.create;

import java.util.List;

/**
 * @author whthomas
 * @date 2018/6/27
 */
public class SendContractsFromTemplateVO {

    /**
     * 模板编号
     */
    private Long templateId;

    /**
     * 发送合同的默认配置
     */
    private DefaultConfig defaultConfig;

    /**
     * 角色
     */
    private List<Role> roles;

    /**
     * 待填写的用户信息
     */
    private List<PlaceHolder> placeHolders;

    public Long getTemplateId() {
        return templateId;
    }

    public void setTemplateId(Long templateId) {
        this.templateId = templateId;
    }

    public DefaultConfig getDefaultConfig() {
        return defaultConfig;
    }

    public void setDefaultConfig(DefaultConfig defaultConfig) {
        this.defaultConfig = defaultConfig;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public List<PlaceHolder> getPlaceHolders() {
        return placeHolders;
    }

    public void setPlaceHolders(List<PlaceHolder> placeHolders) {
        this.placeHolders = placeHolders;
    }
}
