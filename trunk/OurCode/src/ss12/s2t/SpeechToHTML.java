package ss12.s2t;

import edu.cmu.sphinx.frontend.util.Microphone;
import edu.cmu.sphinx.recognizer.Recognizer;
import edu.cmu.sphinx.result.Result;
import edu.cmu.sphinx.util.props.ConfigurationManager;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;


/**
 * A simple HelloDigits demo showing a simple speech application built using Sphinx-4. This application uses the
 * Sphinx-4 endpointer, which automatically segments incoming audio into utterances and silences.
 */
public class SpeechToHTML {

    public static void main(String[] args) throws MalformedURLException {
        URL url;
        if (args.length > 0) {
            url = new File(args[0]).toURI().toURL();
        } else {
            url = SpeechToHTML.class.getResource("SpeechToHTML.config.xml");
        }
        
        SickPad pad = new SickPad();
        pad.setVisible(true);

        ConfigurationManager cm = new ConfigurationManager(url);

        // allocate the recognizer
        Recognizer recognizer = (Recognizer) cm.lookup("recognizer");
        recognizer.allocate();

        // start the microphone
        Microphone microphone = (Microphone) cm.lookup("microphone");
        microphone.startRecording();

        System.out.println("Say any digit(s): e.g. \"two oh oh four\" , or \"three six five\".");

        // loop the recognition until the programm exits.
        while (true) {
            System.out.println("Start speaking. Press Ctrl-C to quit.\n");

            Result result = recognizer.recognize();

            if (result != null) {
                String resultText = result.getBestResultNoFiller();
                System.out.println("You said: " + resultText + "\n");
                pad.sound2Text(resultText);
            } else {
                System.out.println("I can't hear what you said.\n");
            }
        }
    }
}
