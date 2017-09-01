package lyapkoandy13.gsonger;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Andrew on 01.09.2017.
 */

public class SongAdapter extends ArrayAdapter {
    private ArrayList<Song> songs;

    public SongAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull ArrayList<Song> songs) {
        super(context, resource, songs);
        this.songs = songs;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View songView = convertView;

        if (songView == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            songView = inflater.inflate(R.layout.song_item, null);
        }

        Song tSong = songs.get(position);

        if ( tSong != null ){
            TextView songName = (TextView) songView.findViewById(R.id.song_item_song_name);
            TextView songArtist = (TextView) songView.findViewById(R.id.song_item_artist);
            TextView songAuthor = (TextView) songView.findViewById(R.id.song_item_author);

            songName.setText(songs.get(position).getName());
            songArtist.setText(songs.get(position).getArtist());
            songAuthor.setText(songs.get(position).getAuthor());
        }


        return songView;
    }
}
