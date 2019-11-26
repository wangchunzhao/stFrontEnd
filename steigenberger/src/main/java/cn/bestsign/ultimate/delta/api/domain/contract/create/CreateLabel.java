package cn.bestsign.ultimate.delta.api.domain.contract.create;

public class CreateLabel {
    private Integer documentOrder;

    /**
     * 签名位置x
     */
    private Float x ;

    /**
     * 签名位置y
     */
    private Float y ;

    /**
     * 宽度
     */
    private Float width;

    /**
     * 高度
     */
    private Float height;

    /**
     * 签署的页数，这个需要独立列出来
     */
    private Integer pageNumber;

    /**
     * 字段类型 1-签名;2-日期;3-签章
     */
    private SignFlowConstants.SignLabelType  type = SignFlowConstants.SignLabelType.SEAL;

    public Integer getDocumentOrder() {
        return documentOrder;
    }

    public void setDocumentOrder(Integer documentOrder) {
        this.documentOrder = documentOrder;
    }

    public Float getX() {
        return x;
    }

    public void setX(Float x) {
        this.x = x;
    }

    public Float getY() {
        return y;
    }

    public void setY(Float y) {
        this.y = y;
    }

    public Float getWidth() {
        return width;
    }

    public void setWidth(Float width) {
        this.width = width;
    }

    public Float getHeight() {
        return height;
    }

    public void setHeight(Float height) {
        this.height = height;
    }

    public Integer getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    public SignFlowConstants.SignLabelType getType() {
        return type;
    }

    public void setType(SignFlowConstants.SignLabelType type) {
        this.type = type;
    }
}
