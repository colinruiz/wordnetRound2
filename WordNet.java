import edu.princeton.cs.algs4.In;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

public class WordNet {
    // hashmap to keep track of the nouns
    private HashMap<String, Queue<Integer>> nouns = new HashMap<>();
    // hashmap to keep track of the synsets
    private HashMap<Integer, String> synsets = new HashMap<>();
    // digraph that represents the wordnet
    private Digraph digraph;
    // private ArrayList<String> nouns = new ArrayList<>();
    // shortestcommonAncestor object variable
    private ShortestCommonAncestor shortestCommonAncestor;

    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) {
        if (synsets == null || hypernyms == null) {
            throw new IllegalArgumentException();
        }

        In syn = new In(synsets);

        int indexSyn;

        // reading synsets
        while (!syn.isEmpty()) {
            String[] line = syn.readLine().split(",");
            indexSyn = Integer.parseInt(line[0]);
            this.synsets.put(indexSyn, line[1]);
            String[] noun = line[1].split(" ");
            for (String n : noun) {
                if (!this.nouns.containsKey(n)) {
                    Queue<Integer> temp = new LinkedList<>();
                    temp.add(indexSyn);
                    this.nouns.put(n, temp);
                }
                else {
                    this.nouns.get(n).add(indexSyn);
                }

            }
            // this.synsets.put(indexSyn, this.nouns);

        }
        syn.close();

        this.digraph = new Digraph(this.synsets.size());
        // System.out.println(this.synsets.size());
        In hyp = new In(hypernyms);
        // reading hypernyms
        while (!hyp.isEmpty()) {
            String[] line = hyp.readLine().split(",");
            int synsetNumber = Integer.parseInt(line[0]);
            for (int i = 1; i < line.length; i++) {
                this.digraph.addEdge(synsetNumber, Integer.parseInt(line[i]));
            }
        }
        hyp.close();
        // checks if the digraph is rooted
        this.shortestCommonAncestor = new ShortestCommonAncestor(this.digraph);


    }

    // the set of all WordNet nouns
    public Iterable<String> nouns() {
        // System.out.println(this.nouns.size());
        return this.nouns.keySet();
    }

    // is the word a WordNet noun?
    public boolean isNoun(String word) {
        if (word == null) {
            throw new IllegalArgumentException();
        }
        return this.nouns.containsKey(word);
    }

    // a synset (second field of synsets.txt) that is a shortest common ancestor
    // of noun1 and noun2 (defined below)
    public String sca(String noun1, String noun2) {
        if (noun1 == null || noun2 == null) {
            throw new IllegalArgumentException();
        }
        if (!isNoun(noun1) || !isNoun(noun2)) {
            throw new IllegalArgumentException();
        }
        Iterable<Integer> subsetA = this.nouns.get(noun1);
        Iterable<Integer> subsetB = this.nouns.get(noun2);
        int ancestor = this.shortestCommonAncestor.ancestorSubset(subsetA, subsetB);
        if (ancestor == -1) {
            throw new IllegalArgumentException();
        }

        return synsets.get(ancestor);

    }

    // distance between noun1 and noun2 (defined below)
    public int distance(String noun1, String noun2) {
        if (noun1 == null || noun2 == null) {
            throw new IllegalArgumentException();
        }
        if (!isNoun(noun1) || !isNoun(noun2)) {
            throw new IllegalArgumentException();
        }
        Iterable<Integer> subsetA = this.nouns.get(noun1);
        Iterable<Integer> subsetB = this.nouns.get(noun2);
        int dist = this.shortestCommonAncestor.lengthSubset(subsetA, subsetB);
        if (dist == -1) {
            throw new IllegalArgumentException();
        }

        return dist;

    }

    // unit testing (required)
    public static void main(String[] args) {
        String synsets = args[0];
        String hypernyms = args[1];
        WordNet wordNet = new WordNet(synsets, hypernyms);
        Iterable<String> nouns = wordNet.nouns();
        for (String noun1 : nouns)
            for (String noun2 : nouns) {
                String sca = wordNet.sca(noun1, noun2);
                int length = wordNet.distance(noun1, noun2);
                System.out.printf("%s, %s, sca: %s, distance: %d\n",
                                  noun1, noun2, sca, length);
            }
    }

}
