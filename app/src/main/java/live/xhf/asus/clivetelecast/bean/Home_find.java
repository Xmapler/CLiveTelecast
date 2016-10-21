package live.xhf.asus.clivetelecast.bean;

import java.util.List;

/**
 * Created by asus on 2016/10/12.
 */
public class Home_find {

    public FindBean content;

    public class FindBean{
        public List<FBean> list;

        public class FBean{
            public int uid;
            public String bigheadimg;
            public String smallheadimg;
            public String midheadimg;
            public String livename;
            public String name;
            public String place;
            public int online;
            public String video;

            public int getUid() {
                return uid;
            }

            public void setUid(int uid) {
                this.uid = uid;
            }

            public String getMidheadimg() {
                return midheadimg;
            }

            public void setMidheadimg(String midheadimg) {
                this.midheadimg = midheadimg;
            }

            public String getSmallheadimg() {
                return smallheadimg;
            }
            public void setSmallheadimg(String smallheadimg) {
                this.smallheadimg = smallheadimg;
            }
            public String getLivename() {
                return livename;
            }

            public void setLivename(String livename) {
                this.livename = livename;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getBigheadimg() {
                return bigheadimg;
            }

            public void setBigheadimg(String bigheadimg) {
                this.bigheadimg = bigheadimg;
            }

            public int getOnline() {
                return online;
            }

            public void setOnline(int online) {
                this.online = online;
            }

            public String getPlace() {
                return place;
            }

            public void setPlace(String place) {
                this.place = place;
            }

            public String getVideo() {
                return video;
            }

            public void setVideo(String video) {
                this.video = video;
            }

            @Override
            public String toString() {
                return "FBean{" +
                        "bigheadimg='" + bigheadimg + '\'' +
                        ", livename='" + livename + '\'' +
                        ", name='" + name + '\'' +
                        ", place='" + place + '\'' +
                        ", online=" + online +
                        ", video='" + video + '\'' +
                        '}';
            }
        }

    }
}
