package cn.bestsign.ultimate.delta.api.domain.contract.create;

public class SignFlowConstants {
    public static enum UserType {
        PERSON(1),
        ENTERPRISE(2);

        private Integer type;

        private UserType(Integer b) {
            this.type = b;
        }

        public static SignFlowConstants.UserType valueOf(byte type) {
            SignFlowConstants.UserType[] var1 = values();
            int var2 = var1.length;

            for(int var3 = 0; var3 < var2; ++var3) {
                SignFlowConstants.UserType userType = var1[var3];
                if (userType.type == type) {
                    return userType;
                }
            }

            throw new IllegalArgumentException("signStatus枚举类型错误");
        }

        public byte getByte() {
            return this.type.byteValue();
        }

    }

    public static enum ReceiverType {
        SENDER(1),
        SIGNER(2),
        CC_USER(3),
        APPROVER(4),
        VIEWER(5),
        DISTRIBUTOR(6),
        UNDEFINER(7);

        private Integer type;

        private ReceiverType(Integer type) {
            this.type = type;
        }

        public static String getDescription(int b) {
            switch (b) {
                case 1:
                    return "发送";
                case 2:
                    return "签署";
                case 3:
                    return "抄送";
                case 4:
                    return "审批人";
                case 5:
                    return "查阅者";
                case 6:
                    return "分配者";
                case 7:
                    return "未指定";
                default:
                    return "未知";
            }
        }
    }

    public static enum SignLabelType {
        SIGNATURE(1),
        DATE(2),
        SEAL(3),
        RIDING_SEAL(4),
        RIDING_SIGNATURE(5),
        RIDING_PART(6),
        GROUP_SIGN(7),
        BESTSIGN_SIGNATURE(8),
        QR_CODE(9);

        private Byte type;

        private SignLabelType(Integer type) {
            this.type = type.byteValue();
        }

        public byte getByte() {
            return this.type;
        }

        public static SignFlowConstants.SignLabelType valueOf(byte type) {
            SignFlowConstants.SignLabelType[] var1 = values();
            int var2 = var1.length;

            for(int var3 = 0; var3 < var2; ++var3) {
                SignFlowConstants.SignLabelType signLabelType = var1[var3];
                if (signLabelType.type == type) {
                    return signLabelType;
                }
            }

            throw new IllegalArgumentException("SignLabelType枚举类型错误");
        }
    }
}
