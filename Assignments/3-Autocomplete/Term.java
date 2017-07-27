/******************************************************************************
 *  Name:    Linhchi Nguyen
 *  NetID:   lbn
 *  Precept: P03
 *
 *  Partner Name:    Murray Lynch
 *  Partner NetID:   mdlynch
 *  Partner Precept: P04
 * 
 *  Description:  Creates an object composed of a query (String) and 
 *  its corresponding weight (long). Includes comparator methods for instances
 *  of the class that facilitate sorting term elements in an array by weight
 *  and by prefix.
 ******************************************************************************/
import java.util.Comparator;

public class Term implements Comparable<Term> {
    
    private final String q;
    private final long w;
    
    // Initializes a term with the given query string and weight.
    public Term(String query, long weight) {
        // corner cases
        if (query == null) throw new NullPointerException();
        if (weight < 0) throw new IllegalArgumentException();
        
        q = query;
        w = weight;
    }
    
    // Compares the two terms in descending order by weight.
    public static Comparator<Term> byReverseWeightOrder() {
        return new ReverseWeightOrder(); 
    }
    
    private static class ReverseWeightOrder implements Comparator<Term> {
        public int compare(Term t1, Term t2) {
            if (t1.w > t2.w) return -1;
            if (t1.w < t2.w) return 1;
            return 0;
        }
    }
    
    // Compares the two terms in lexicographic order but 
    // using only the first r characters of each query.
    public static Comparator<Term> byPrefixOrder(int r) {
        if (r < 0) throw new IllegalArgumentException();
        return new PrefixOrder(r);   
    }
    
    private static class PrefixOrder implements Comparator<Term> {
        private int r;
        private PrefixOrder(int r) {
            this.r = r;
        }
        
        public int compare(Term t1, Term t2) {
            String s1, s2;
            
            // If prefix longer than query, compare query as is
            // Check both t1 and t2 to make compare method's arguments
            // interchangable
            if (r >= (t1.q.length())) 
                s1 = t1.q;
            else s1 = t1.q.substring(0, r);
            
            if (r >= (t2.q.length())) 
                s2 = t2.q;
            else s2 = t2.q.substring(0, r);
            
            //System.out.println("compared: " + s1.compareTo(s2));
            
            return (s1.compareTo(s2));
            
        }
    }

    // Compares the two terms in lexicographic order by query.
    public int compareTo(Term that) {
        return this.q.compareTo(that.q);
    }
    
    // Returns a string representation of this term in the following format:
    // the weight, followed by a tab, followed by the query.
    public String toString() {
        String s = w + "\t" + q;
        return s;
    }
    
}