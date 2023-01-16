public class Outcast {
    // wordnet object variable
    private WordNet wordNet;

    // constructor takes a WordNet object
    public Outcast(WordNet wordnet) {
        if (wordnet == null) {
            throw new IllegalArgumentException();
        }
        this.wordNet = wordnet;

    }

    // given an array of WordNet nouns, return an outcast
    public String outcast(String[] nouns) {
        int minimum = Integer.MIN_VALUE;
        String out = "";


        for (int i = 0; i < nouns.length; i++) {
            int distance = 0;
            for (int j = 0; j < nouns.length; j++) {
                distance += this.wordNet.distance(nouns[i], nouns[j]);
            }
            if (distance > minimum) {
                minimum = distance;
                out = nouns[i];
            }

        }
        return out;
    }

    // test client (see below)
    public static void main(String[] args) {
        // WordNet wordnet = new WordNet(args[0], args[1]);
        // Outcast outcast = new Outcast(wordnet);
        // for (int t = 2; t < args.length; t++) {
        //     In in = new In(args[t]);
        //     String[] nouns = in.readAllStrings();
        //     StdOut.println(args[t] + ": " + outcast.outcast(nouns));
        // }

    }

}
