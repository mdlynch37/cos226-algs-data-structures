/******************************************************************************
 *  Name:    Linhchi Nguyen
 *  NetID:   lbn
 *  Precept: P03
 *
 *  Partner Name:    Murray Lynch
 *  Partner NetID:   mdlynch
 *  Partner Precept: P04
 * 
 *  Description:  Sorts an array of terms lexicographically, then with
 *  allMatches(), returns a list of terms whose queries begin with a given
 *  prefix in descending order of their weight.
 * 
 *  Dependencies: BinarySearchDeluxe.java, Term.java
 ******************************************************************************/
import edu.princeton.cs.algs4.MergeX;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import java.util.Comparator;
import edu.princeton.cs.algs4.In;

public class Autocomplete {
    
    private final Term[] a;
    
    // Initializes the data structure from the given array of terms.
    public Autocomplete(Term[] terms) {
        if (terms == null) throw new NullPointerException();
        for (int i = 0; i < terms.length; i++) {
            if (terms[i] == null) throw new NullPointerException();
        }
        
        a = new Term[terms.length];
        
        // defensive copy
        for (int j = 0; j < terms.length; j++) {
            a[j] = terms[j];
        }
        
        MergeX.sort(a);
    }
    
    // Returns all terms that start with the given prefix, 
    // in descending order of weight.
    public Term[] allMatches(String prefix) {
        if (prefix == null) throw new NullPointerException();
        
        Term term = new Term(prefix, 0); // with arbitrary weight of 0
        
        Comparator<Term> prefixComparator = term.byPrefixOrder(prefix.length());
        int first = BinarySearchDeluxe.firstIndexOf(a, term, prefixComparator);
        // when no match is found, returns null array
        if (first == -1) { // before lastIndexOf to save unnecessary search
           Term[] b = new Term[0];
           return b;
        }
        int last = BinarySearchDeluxe.lastIndexOf(a, term, prefixComparator);

        // creates new array of terms found with prefix to be sorted by weight
        Term[] b = new Term[last-first+1];
        for (int i = 0; i < (last-first+1); i++)
            b[i] = a[first + i];
        Comparator<Term> weightComparator = term.byReverseWeightOrder();
        MergeX.sort(b, weightComparator);
        return b;
    }
    
    // Returns the number of terms that start with the given prefix.
    public int numberOfMatches(String prefix) {
        if (prefix == null) throw new NullPointerException();
        
        Term term = new Term(prefix, 0);
        Comparator<Term> prefixComparator = term.byPrefixOrder(prefix.length());
        int first = BinarySearchDeluxe.firstIndexOf(a, term, prefixComparator);
        // when no match is found, returns 0
        if (first == -1) // before lastIndexOf to save unnecessary search
           return 0;
        int last = BinarySearchDeluxe.lastIndexOf(a, term, prefixComparator);
        return (last-first + 1);
    }
    public static void main(String[] args) {
        
        // read in the terms from a file
        String filename = args[0];
        In in = new In(filename);
        int N = in.readInt();
        Term[] terms = new Term[N];
        for (int i = 0; i < N; i++) {
            long weight = in.readLong();           // read the next weight
            in.readChar();                         // scan past the tab
            String query = in.readLine();          // read the next query
            terms[i] = new Term(query, weight);    // construct the term
        }
        
        // read in queries from standard input and print out the top k matching terms
        int k = Integer.parseInt(args[1]);
        Autocomplete autocomplete = new Autocomplete(terms);
        while (StdIn.hasNextLine()) {
            String prefix = StdIn.readLine();
            Term[] results = autocomplete.allMatches(prefix);
            for (int i = 0; i < Math.min(k, results.length); i++)
                StdOut.println(results[i]);
        }
    }    
}