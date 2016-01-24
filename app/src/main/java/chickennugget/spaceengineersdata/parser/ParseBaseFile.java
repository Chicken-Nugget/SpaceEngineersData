package chickennugget.spaceengineersdata.parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class ParseBaseFile {
    public static ArrayList<String> base_file = new ArrayList<>();
    public static ArrayList<String> block_ids = new ArrayList<>();
    public static Set<String> hs = new HashSet<>();
    public static String basetxt = "base_file.txt";
    public static String idstxt = "block_ids.txt";

    public static void main(String[] args) throws IOException {
        BufferedReader br;
        File f;
        FileWriter fw;
        String line;
        try {
            br = new BufferedReader(new FileReader(basetxt));
            while ((line = br.readLine()) != null)
                base_file.add(line);
            br.close();
            br = new BufferedReader(new FileReader(idstxt));
            while ((line = br.readLine()) != null)
                block_ids.add(line);
            br.close();
            hs.addAll(block_ids);
            block_ids.clear();
            block_ids.addAll(hs);
            for (String bi : block_ids) {
                f = new File("f_" + bi + ".xml");
                fw = new FileWriter(f, true);
                for (String bf : base_file)
                    fw.write(bf.replaceAll("INSERTID", bi) + "\n");
                fw.flush();
                fw.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
