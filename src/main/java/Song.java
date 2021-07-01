import java.util.Map;

public class Song implements Comparable {
    String title,type,category,cover,artist,version;
    int bpm,sort;
    Map<String,String> level;

    @Override
    public String toString() {
        return
                "title='" + title + '\'' +
                "\ntype='" + type + '\'' +
                "\ncategory='" + category + '\'' +
                "\nartist='" + artist + '\'' +
                "\nversion='" + version + '\'' +
                "\nbpm=" + bpm +
                "\nlevel=" + level
                ;
    }

    public String getCover(){
        return "https://github.com/realzns/maimaidx-roll/blob/master/static/img/cover/" + cover + ".jpg?raw=true'";
    }

    public String getTitle() {
        return title;
    }

    public int compareTo(Object o) {
        Song s = (Song) o;
        return this.sort-s.sort;
    }
//https://github.com/eiko-g/maimaidx-roll/blob/master/static/img/cover/0000b23e3a6bff99da5b7d8c7b56f737.jpg?raw=true
}
