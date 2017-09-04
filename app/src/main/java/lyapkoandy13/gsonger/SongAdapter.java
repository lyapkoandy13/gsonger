package lyapkoandy13.gsonger;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Andrew on 01.09.2017.
 */

public class SongAdapter extends ArrayAdapter implements Filterable {
    private ArrayList<Song> songs;
    private ArrayList<Song> origSongs;

    public SongAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull ArrayList<Song> songs) {
        super(context, resource, songs);
        this.songs = songs;
        this.origSongs = songs;
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

    @Override
    public int getCount() {
        return songs.size();
    }

    @Nullable
    @Override
    public Song getItem(int position) {
        return songs.get(position);
    }

    @Override
    public Filter getFilter() {
        return new Filter() {

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                final FilterResults oReturn = new FilterResults();
                final ArrayList<Song> results = new ArrayList<Song>();
                if (origSongs == null)
                    origSongs = songs;
                if (constraint != null) {
                    if (origSongs != null && origSongs.size() > 0) {
                        for (final Song song : origSongs) {
                            if (song.getName().toLowerCase()
                                    .contains(constraint.toString()))
                                results.add(song);
                        }
                    }
                    oReturn.values = results;
                }
                return oReturn;
            }

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint,
                                          FilterResults results) {
                songs = (ArrayList<Song>) results.values;
                SongAdapter.this.notifyDataSetChanged();
            }
        };
    }
}
