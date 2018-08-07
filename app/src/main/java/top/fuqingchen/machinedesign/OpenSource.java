package top.fuqingchen.machinedesign;

/**
 * @author Fu_Qingchen
 */
public class OpenSource {
    private String mname;
    private String mdetal;

    OpenSource(String name, String detal) {
        mname = name;
        mdetal = detal;
    }

    public String getMname() {
        return mname;
    }

    public String getDetal() {
        return mdetal;
    }
}
