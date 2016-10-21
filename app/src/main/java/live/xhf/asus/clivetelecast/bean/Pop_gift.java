package live.xhf.asus.clivetelecast.bean;

import java.util.List;

/**
 * Created by asus on 2016/10/20.
 */
public class Pop_gift {
    public PGift content;

    public static class PGift{
        public List<JUF> jufan;

        public static class JUF{
            public List<PList> list;

            public static class PList{
                public int exp;
                public String unit;
                public int baseGoodId;
                public String desc;
                public String name;
                public String gif_url1;
                public int goodId;
                public String url;

                public int getExp() {
                    return exp;
                }

                public void setExp(int exp) {
                    this.exp = exp;
                }

                public String getUnit() {
                    return unit;
                }

                public void setUnit(String unit) {
                    this.unit = unit;
                }

                public int getBaseGoodId() {
                    return baseGoodId;
                }

                public void setBaseGoodId(int baseGoodId) {
                    this.baseGoodId = baseGoodId;
                }

                public String getDesc() {
                    return desc;
                }

                public void setDesc(String desc) {
                    this.desc = desc;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getGif_url1() {
                    return gif_url1;
                }

                public void setGif_url1(String gif_url1) {
                    this.gif_url1 = gif_url1;
                }

                public int getGoodId() {
                    return goodId;
                }

                public void setGoodId(int goodId) {
                    this.goodId = goodId;
                }

                public String getUrl() {
                    return url;
                }

                public void setUrl(String url) {
                    this.url = url;
                }

                @Override
                public String toString() {
                    return "PList{" +
                            "exp=" + exp +
                            ", unit='" + unit + '\'' +
                            ", baseGoodId=" + baseGoodId +
                            ", desc='" + desc + '\'' +
                            ", name='" + name + '\'' +
                            ", gif_url1='" + gif_url1 + '\'' +
                            ", goodId=" + goodId +
                            ", url='" + url + '\'' +
                            '}';
                }
            }
        }

        public List<QX> qxiu;

        public class QX{

        }
    }
}
