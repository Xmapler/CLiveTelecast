package live.xhf.asus.clivetelecast.bean;

/**
 * Created by asus on 2016/10/19.
 */
public class Student {
    public int id;
    public String midheadimg;
    public String name;
    public String place;
    public String video;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMidheadimg() {
        return midheadimg;
    }

    public void setMidheadimg(String midheadimg) {
        this.midheadimg = midheadimg;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
        return "Student{" +
                "id=" + id +
                ", midheadimg='" + midheadimg + '\'' +
                ", name='" + name + '\'' +
                ", place='" + place + '\'' +
                ", video='" + video + '\'' +
                '}';
    }
}
