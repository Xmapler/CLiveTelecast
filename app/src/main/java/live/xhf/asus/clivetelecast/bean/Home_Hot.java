package live.xhf.asus.clivetelecast.bean;

import java.util.List;

/**
 * Created by asus on 2016/10/9.
 */
public class Home_Hot {

    public Content content;

    public class Content{
        public List<Banner> banner;

            public class Banner{
                public String img;
                public String url;

                public String getUrl() {
                    return url;
                }

                public void setUrl(String url) {
                    this.url = url;
                }

                public String getImg() {
                    return img;
                }

                public void setImg(String img) {
                    this.img = img;
                }

                @Override
                public String toString() {
                    return "Banner{" +
                            "img='" + img + '\'' +
                            ", url='" + url + '\'' +
                            '}';
                }
            }

        public List<LList> list;

            public class LList{
                public int uid;
                public String name;
                public String smallheadimg;
                public String bigheadimg;
                public String place;
                public int online;
                public String livename;
                public String video;

                    public List<Server> servers;
                    public class Server{
                        public String host;
                        public int port;
                    }

                public int getUid() {
                    return uid;
                }

                public void setUid(int uid) {
                    this.uid = uid;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getSmallheadimg() {
                    return smallheadimg;
                }

                public void setSmallheadimg(String smallheadimg) {
                    this.smallheadimg = smallheadimg;
                }

                public String getBigheadimg() {
                    return bigheadimg;
                }

                public void setBigheadimg(String bigheadimg) {
                    this.bigheadimg = bigheadimg;
                }

                public String getPlace() {
                    return place;
                }

                public void setPlace(String place) {
                    this.place = place;
                }

                public String getLivename() {
                    return livename;
                }

                public void setLivename(String livename) {
                    this.livename = livename;
                }

                public int getOnline() {
                    return online;
                }

                public void setOnline(int online) {
                    this.online = online;
                }

                public String getVideo() {
                    return video;
                }

                public void setVideo(String video) {
                    this.video = video;
                }

                @Override
                public String toString() {
                    return "LList{" +
                            "uid='" + uid + '\'' +
                            ", name='" + name + '\'' +
                            ", smallheadimg='" + smallheadimg + '\'' +
                            ", bigheadimg='" + bigheadimg + '\'' +
                            ", place='" + place + '\'' +
                            ", online='" + online + '\'' +
                            ", livename='" + livename + '\'' +
                            ", video='" + video + '\'' +
                            '}';
                }
            }
    }
}
