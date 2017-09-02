package lyapkoandy13.gsonger;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Andrew on 02.09.2017.
 */

public class SongParser {
    private String songText;

    public SongParser(String songText) {
        this.songText = songText;
    }

    public SongParser() {
    }

    public void setSongText(String songText) {
        this.songText = songText;
    }

    public String getSongText() {
        return songText;
    }

    public ArrayList<HashMap<String,String>> parse(){
        if(this.songText != null){
            ArrayList<HashMap<String,String>> lineChordsAndText = new ArrayList<>();
            HashMap<String,String> chordsAndText = new HashMap<>();
            String lines[] = this.songText.split("\\n");
            for(String line: lines) {

                int firstIndex = line.indexOf("[");
                int secondIndex = line.indexOf("]");
                // if theres some text or whitespace before first chord in line
                if (firstIndex != -1 && firstIndex != 0){
                    String phrase = line.substring(0, firstIndex);
                    chordsAndText.put(" ", phrase);
                    line = line.substring(firstIndex);
                }

                // parsing line
                firstIndex = line.indexOf("[");
                secondIndex = line.indexOf("]");
                // if there's some "[" and "]"
                if (firstIndex != -1 && secondIndex != -1){
                    while(line.length() != 0) {
                        String chord = line.substring(firstIndex + 1, secondIndex);
                        line = line.substring(secondIndex+1);
                        firstIndex = line.indexOf("[");
                        secondIndex = line.indexOf("]");
                        // if there's still "["
                        if (firstIndex != -1){
                            String phrase = line.substring(0,firstIndex);
                            chordsAndText.put(chord, phrase);
                            line = line.substring(firstIndex);
                            firstIndex = line.indexOf("[");
                            secondIndex = line.indexOf("]");
                        } else {
                            String phrase = line.substring(0);
                            chordsAndText.put(chord, phrase);
                            line = "";
                        }
                    }
                } else {
                    String phrase = line.substring(0);
                    chordsAndText.put(" ", phrase);
                    line = "";
                }
                lineChordsAndText.add(chordsAndText);

            }

            return lineChordsAndText;
        }

        return null;
    }

}
