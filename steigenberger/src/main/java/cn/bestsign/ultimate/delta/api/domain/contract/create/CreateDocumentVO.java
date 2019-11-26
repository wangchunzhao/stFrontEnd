package cn.bestsign.ultimate.delta.api.domain.contract.create;

public class CreateDocumentVO {
    /**
     * 文档的顺序
     */
    private int order ;
    /**
     * 文件的内容，BASE64编码
     */
    private String content;
    /**
     * 文件名称
     */
    private String  fileName;

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
