package cn.bestsign.ultimate.delta.api.domain.template.create;

import java.util.Date;

/**
 * @author whthomas
 * @date 2018/6/27
 */
public class DefaultConfig {

    private String contractTitle;

    private Integer expireDays;

    private Boolean signOrdered;

    private Date contractLifeEnd;

    public String getContractTitle() {
        return contractTitle;
    }

    public void setContractTitle(String contractTitle) {
        this.contractTitle = contractTitle;
    }

    public Integer getExpireDays() {
        return expireDays;
    }

    public void setExpireDays(Integer expireDays) {
        this.expireDays = expireDays;
    }

    public Boolean getSignOrdered() {
        return signOrdered;
    }

    public void setSignOrdered(Boolean signOrdered) {
        this.signOrdered = signOrdered;
    }

    public Date getContractLifeEnd() {
        return contractLifeEnd;
    }

    public void setContractLifeEnd(Date contractLifeEnd) {
        this.contractLifeEnd = contractLifeEnd;
    }
}
