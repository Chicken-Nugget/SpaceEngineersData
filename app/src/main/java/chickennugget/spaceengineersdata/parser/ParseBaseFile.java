import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class ParseBaseFile {
    public static ArrayList<String> base_file_combined = new ArrayList<>();
    public static ArrayList<String> base_file_large = new ArrayList<>();
    public static ArrayList<String> base_file_small = new ArrayList<>();
    public static ArrayList<String> base_button = new ArrayList<>();
    public static ArrayList<String> block_ids = new ArrayList<>();
    public static Set<String> hs = new HashSet<>();
    public static String basetxtc = "base_file_combined.txt";
    public static String basetxtl = "base_file_large.txt";
    public static String basetxts = "base_file_small.txt";
    public static String basetxtb = "base_button.txt";
    public static String idstxt = "block_ids.txt";

    public static void main(String[] args) throws IOException {
        BufferedReader br;
        File f;
        FileWriter fw;
        String line;
        try {
            // Load base_file_combined into ArrayList
            br = new BufferedReader(new FileReader(basetxtc));
            while ((line = br.readLine()) != null)
                base_file_combined.add(line);
            br.close();
            // Load base_file_large into ArrayList
            br = new BufferedReader(new FileReader(basetxtl));
            while ((line = br.readLine()) != null)
                base_file_large.add(line);
            br.close();
            // Load base_file_small into ArrayList
            br = new BufferedReader(new FileReader(basetxts));
            while ((line = br.readLine()) != null)
                base_file_small.add(line);
            br.close();
            // Load base_button into ArrayList
            br = new BufferedReader(new FileReader(basetxtb));
            while ((line = br.readLine()) != null)
                base_button.add(line);
            br.close();
            // Load block ids into ArrayList
            br = new BufferedReader(new FileReader(idstxt));
            while ((line = br.readLine()) != null)
                block_ids.add(line);
            br.close();
            // Load block ids into HashSet and back to ArrayList to remove duplicates
            hs.addAll(block_ids);
            block_ids.clear();
            block_ids.addAll(hs);
            // For all block ids...
            for (String bi : block_ids) {
                // Generate formatted file for combined view
                f = new File("f_" + bi + "_c.xml");
                fw = new FileWriter(f, true);
                for (String bf : base_file_combined)
                    fw.write(bf.replaceAll("INSERTID", bi) + "\n");
                fw.flush();
                fw.close();
                // Generate formatted file for large view
                f = new File("f_" + bi + "_l.xml");
                fw = new FileWriter(f, true);
                for (String bf : base_file_large)
                    fw.write(bf.replaceAll("INSERTID", bi) + "\n");
                fw.flush();
                fw.close();
                // Generate formatted file for small view
                f = new File("f_" + bi + "_s.xml");
                fw = new FileWriter(f, true);
                for (String bf : base_file_small)
                    fw.write(bf.replaceAll("INSERTID", bi) + "\n");
                fw.flush();
                fw.close();
                // Generate formatted file for buttons
                f = new File("generated_buttons.txt");
                fw = new FileWriter(f, true);
                for (String bf : base_button)
                    fw.write(bf.replaceAll("INSERTID", bi) + "\n");
                fw.flush();
                fw.close();
                // Generate button id array
                f = new File("generated_button_array.txt");
                fw = new FileWriter(f, true);
                fw.write("R.id.b_" + bi + "_l,\n");
                fw.write("R.id.b_" + bi + "_c,\n");
                fw.write("R.id.b_" + bi + "_s,\n");
                fw.flush();
                fw.close();
                // Generate fragment id array
                f = new File("generated_fragment_array.txt");
                fw = new FileWriter(f, true);
                fw.write("R.layout.f_" + bi + "_l,\n");
                fw.write("R.layout.f_" + bi + "_c,\n");
                fw.write("R.layout.f_" + bi + "_s,\n");
                fw.flush();
                fw.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
