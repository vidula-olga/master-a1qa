package master;



/**
 * Hello world!
 */
public class App {
    public static int main(String where, String what) {
        where = "оля молодец молодец молодец";
        what = "молодец";
        int i = where.indexOf(what);
        int count = 0;
        while (i>=0){
            count++;
            i = where.indexOf(what, i + 1);
        }
        return count;
    }
}
