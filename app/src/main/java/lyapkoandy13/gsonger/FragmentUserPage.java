package lyapkoandy13.gsonger;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentUserPage.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class FragmentUserPage extends android.app.Fragment {

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private DatabaseReference mSongsRef = FirebaseDatabase.getInstance().getReference().child("songs");
    private ArrayList<Song> arrSongs = new ArrayList<>();
    private HashMap<String, Song> mapSongs = new HashMap<>();
    private OnFragmentInteractionListener mListener;

    public FragmentUserPage() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_page, container, false);
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final SongAdapter songAdapter = new SongAdapter(getActivity(), R.layout.song_item, arrSongs);

        ListView lvSearch = (ListView) getActivity().findViewById(R.id.lv_user_songs);
        lvSearch.setAdapter(songAdapter);

        mSongsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                arrSongs.clear();
                mapSongs.clear();
                for(DataSnapshot songSnapshot: dataSnapshot.getChildren()){
                    if(songSnapshot.child("author").getValue(String.class).equals(mAuth.getCurrentUser().getEmail())){
                        Song tSong = songSnapshot.getValue(Song.class);
                        tSong.setId(songSnapshot.getKey());
                        mapSongs.put(songSnapshot.getKey(), tSong);
                        arrSongs.add(tSong);
                    }
                }
                songAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        lvSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String songId = arrSongs.get(position).getId();
                String songText = arrSongs.get(position).getText();
                String songAuthor = arrSongs.get(position).getAuthor();
                String songArtist = arrSongs.get(position).getArtist();
                String songName = songAdapter.getItem(position).getName();

                Bundle bundle = new Bundle();
                bundle.putString("songId", songId);
                bundle.putString("songText", songText);
                bundle.putString("songAuthor", songAuthor);
                bundle.putString("songArtist", songArtist);
                bundle.putString("songName", songName);

                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                FragmentSong fragmentSong = new FragmentSong();
                fragmentSong.setArguments(bundle);

                fragmentTransaction.replace(R.id.container, fragmentSong);
                getActivity().setTitle("Song");
                fragmentTransaction.commit();
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
