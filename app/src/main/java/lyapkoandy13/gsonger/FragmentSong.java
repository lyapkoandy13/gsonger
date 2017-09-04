package lyapkoandy13.gsonger;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentSong.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class FragmentSong extends android.app.Fragment {

    private String songText;
    private String songAuthor;
    private String songArtist;
    private String songId;
    private String songName;

    private OnFragmentInteractionListener mListener;

    public FragmentSong() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            songText = getArguments().getString("songText");
            songAuthor = getArguments().getString("songAuthor");
            songId = getArguments().getString("songId");
            songArtist = getArguments().getString("songArtist");
            songName = getArguments().getString("songName");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_song, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView tvName = (TextView) getActivity().findViewById(R.id.song_name);
        TextView tvAuthor = (TextView) getActivity().findViewById(R.id.song_author);
        TextView tvArtist = (TextView) getActivity().findViewById(R.id.song_artist);

        tvArtist.setText(this.songArtist);
        tvAuthor.setText(this.songAuthor);
        tvName.setText(this.songName);

        if(getArguments() != null) {
            SongParser songParser = new SongParser(this.songText);
            ArrayList<HashMap<String, String>> arrChordsAndPhrases = songParser.parse();
            if ( !arrChordsAndPhrases.isEmpty() ) {
                LinearLayout song_container = (LinearLayout) view.findViewById(R.id.song_container);
                for(HashMap<String,String> line:arrChordsAndPhrases){
                    LinearLayout line_container = new LinearLayout(getActivity());

                    line_container.setOrientation(LinearLayout.HORIZONTAL);
                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    line_container.setLayoutParams(layoutParams);

                    // dp to pixel conversion
                    float scale = getResources().getDisplayMetrics().density;
                    int dpAsPixels = (int) (15*scale + 0.5f);

                    line_container.setPadding(dpAsPixels,dpAsPixels,dpAsPixels,dpAsPixels);

                    for(Map.Entry<String, String> chordAndPhrase : line.entrySet()){
                        LinearLayout phrase_container = new LinearLayout(getActivity());

                        phrase_container.setOrientation(LinearLayout.VERTICAL);
                        LinearLayout.LayoutParams layoutParams1 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        phrase_container.setLayoutParams(layoutParams1);

                        TextView tvChord = new TextView(getActivity());
                        layoutParams1.width = layoutParams1.MATCH_PARENT;
                        layoutParams1.height =  layoutParams1.WRAP_CONTENT;
                        tvChord.setLayoutParams(layoutParams1);
                        tvChord.setText(chordAndPhrase.getKey());

                        TextView tvPhrase = new TextView(getActivity());
                        tvPhrase.setLayoutParams(layoutParams1);
                        tvPhrase.setText(chordAndPhrase.getValue());
                        tvPhrase.setTextColor(Color.BLACK);

                        phrase_container.addView(tvChord);
                        phrase_container.addView(tvPhrase);

                        line_container.addView(phrase_container);
                    }
                    song_container.addView(line_container);
                }
            }
        }
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
