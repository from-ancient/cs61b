package gh2;
import edu.princeton.cs.algs4.StdAudio;
import edu.princeton.cs.algs4.StdDraw;

/**
 * A client that uses the synthesizer package to replicate a plucked guitar string sound
 */
public class GuitarHero {
    public static final double CONCERT= 440.0;

    public static void main(String[] args) {
        /* create two guitar strings, for concert A and C */
        String keyboard = "q2we4r5ty7u8i9op-[=zxdcfvgbnjmk,.;/' ";
        GuitarString[] stringArray = new GuitarString[keyboard.length()];

        for (int i = 0; i < keyboard.length(); i++) {
            stringArray[i] = new GuitarString(CONCERT * Math.pow(2, (i - 24) / 12.0));
        }

        while (true) {

            /* check if the user has typed a key; if so, process it */
            if (StdDraw.hasNextKeyTyped()) {
                int index;
                char key = StdDraw.nextKeyTyped();
                if ((index = keyboard.indexOf(key)) != -1){
                    stringArray[index].pluck();
                }
            }

            /* compute the superposition of samples */
            double sample = 0.0;
            for (int i = 0; i < stringArray.length; i++) {
                sample += stringArray[i].sample();
            }
            /* play the sample on standard audio */
            StdAudio.play(sample);

            /* advance the simulation of each guitar string by one step */
            for (int i = 0; i < stringArray.length; i++) {
                stringArray[i].tic();
            }
        }
    }
}

