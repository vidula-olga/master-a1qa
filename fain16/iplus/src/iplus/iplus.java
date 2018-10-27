package iplus;

/**
 * Created by New User on 26.10.2018.
 */
public class iplus {


    public static int substring(String where, String what) {

        int i = where.indexOf(what);
        int count = 0;
        while (i>=0){
            count++;
            i = where.indexOf(what, i + 1);
        }
        return count;
    }

    public static void main(String[] args){
        String where = "оля молодец молодец молодец";
        String what = "молодец";
        System.out.println("количество совпадений: " + substring(where,what));
    }



}
