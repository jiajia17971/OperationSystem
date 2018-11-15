package com.hgsoft.zengzhiyingyong.util;

public  class InitData {


        public static String pointCode_boc = "4201020200301050003";
        public static String pointCode_icbc = "4201020200101050060";
        public static String pointCode_abc = "4201020200401000047";
        public static String pointCode_psbc = "4201020200601030003";
        public static String pointCode_ccb = "4201020200201025008";
        public static String pointCode_rcb = "4201020203001060001";

        public final static InitData INSTANCE = new InitData();
        private InitData(){};
        public static InitData getInstance(){
            return INSTANCE;
        }

        public  String getPointCode_boc() {
            return pointCode_boc;
        }

        public  void setPointCode_boc(String pointCode_boc) {
            InitData.pointCode_boc = pointCode_boc;
        }

        public  String getPointCode_icbc() {
            return pointCode_icbc;
        }

        public static void setPointCode_icbc(String pointCode_icbc) {
            InitData.pointCode_icbc = pointCode_icbc;
        }

        public  String getPointCode_abc() {
            return pointCode_abc;
        }

        public  void setPointCode_abc(String pointCode_abc) {
            InitData.pointCode_abc = pointCode_abc;
        }

        public  String getPointCode_psbc() {
            return pointCode_psbc;
        }

        public  void setPointCode_psbc(String pointCode_psbc) {
            InitData.pointCode_psbc = pointCode_psbc;
        }

        public  String getPointCode_ccb() {
            return pointCode_ccb;
        }

        public  void setPointCode_ccb(String pointCode_ccb) {
            InitData.pointCode_ccb = pointCode_ccb;
        }
}
