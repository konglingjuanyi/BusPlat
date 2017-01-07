import lombok.Data;
import org.kohsuke.args4j.Option;

import java.io.File;
@Data
public class Business {

    @Option(name="-name",usage="Sets a name")
    public String name;

//    @Option(name="-file",usage="Sets a file if that is present")
//    public File file;



    public void run() {
        System.out.println("Business-Logic");
        System.out.println("- name: " + name);
//        System.out.println("- file: " + ((file!=null)
//                                      ? file.getAbsolutePath()
//                                      : "null"));
    }

}