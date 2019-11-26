package cn.bestsign.ultimate.delta.api.domain.contract.sign;

import java.util.List;

public class SignContractVO {
    private String sealName;
    private List<Long> contractIds;

    public String getSealName() {
        return sealName;
    }

    public void setSealName(String sealName) {
        this.sealName = sealName;
    }

    public List<Long> getContractIds() {
        return contractIds;
    }

    public void setContractIds(List<Long> contractIds) {
        this.contractIds = contractIds;
    }
}
