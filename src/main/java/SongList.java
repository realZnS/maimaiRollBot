import java.util.ArrayList;

public class SongList {
    int data;
    ArrayList<Song> songList;

    public SongList(int data,ArrayList<Song> songList){
        super();
        this.data=data;
        this.songList=songList;
    }

    @Override
    public String toString() {
        return "SongList{" +
                "data=" + data +
                ", songList=" + songList +
                '}';
    }
}
